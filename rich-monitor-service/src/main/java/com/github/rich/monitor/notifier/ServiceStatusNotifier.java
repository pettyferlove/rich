package com.github.rich.monitor.notifier;

import com.github.rich.common.core.constant.MqQueueConstant;
import com.github.rich.common.core.model.message.ServiceStatusChangeMessage;
import com.github.rich.common.core.service.IMessageSender;
import com.github.rich.monitor.config.MailRemindConfig;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Petty
 */
@Slf4j
public class ServiceStatusNotifier extends AbstractStatusChangeNotifier {

    private final static String DOWN_MESSAGE = "服务生命检查失败";
    private final static String OFFLINE_MESSAGE = "服务离线";
    private final static String UNKNOWN_MESSAGE = "未知错误";
    private final static String DEFAULT_MESSAGE = "缺省信息";

    private final MailRemindConfig mailRemindConfig;

    private final IMessageSender messageSender;

    private final ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(10, r -> new Thread(r, "ServiceStatusNotifierThread"));

    private static Map<InstanceId, Future> futureMap = new HashMap<>(10);

    public ServiceStatusNotifier(InstanceRepository instanceRepository, MailRemindConfig mailRemindConfig, IMessageSender messageSender) {
        super(instanceRepository);
        this.mailRemindConfig = mailRemindConfig;
        this.messageSender = messageSender;
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent instanceEvent, Instance instance) {
        return Mono.fromRunnable(() -> {
            Future future;
            switch (instance.getStatusInfo().getStatus()) {
                case StatusInfo.STATUS_DOWN:
                    processStatus(instance, DOWN_MESSAGE);
                    break;
                case StatusInfo.STATUS_UP:
                    future = futureMap.get(instance.getId());
                    if (future != null) {
                        future.cancel(true);
                    }
                    break;
                case StatusInfo.STATUS_OFFLINE:
                    processStatus(instance, OFFLINE_MESSAGE);
                    break;
                case StatusInfo.STATUS_UNKNOWN:
                    processStatus(instance, UNKNOWN_MESSAGE);
                    break;
                default:
                    processStatus(instance, DEFAULT_MESSAGE);
                    break;
            }
        });
    }

    private void processStatus(Instance instance, String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Future future = scheduled.schedule(() -> {
            log.error("Service {} (instance->{}) is {}", instance.getRegistration().getName(), instance.getId(), instance.getStatusInfo().getStatus());
            ServiceStatusChangeMessage serviceStatusChangeMessage = new ServiceStatusChangeMessage();
            serviceStatusChangeMessage.setServiceId(instance.getId().getValue());
            serviceStatusChangeMessage.setMessage(message);
            serviceStatusChangeMessage.setException((String) instance.getStatusInfo().getDetails().get("exception"));
            serviceStatusChangeMessage.setExceptionMessage((String) instance.getStatusInfo().getDetails().get("message"));
            serviceStatusChangeMessage.setStatus(instance.getStatusInfo().getStatus());
            serviceStatusChangeMessage.setTime(simpleDateFormat.format(new Date(System.currentTimeMillis())));
            serviceStatusChangeMessage.setVersion(instance.getVersion());
            serviceStatusChangeMessage.setInfo(instance.getInfo().getValues());
            serviceStatusChangeMessage.setFrom(mailRemindConfig.getFrom());
            serviceStatusChangeMessage.setTo(mailRemindConfig.getTo());
            serviceStatusChangeMessage.setSubject("服务离线警告");
            messageSender.send(MqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE, serviceStatusChangeMessage);
        }, mailRemindConfig.getInterval(), TimeUnit.MINUTES);
        futureMap.put(instance.getId(), future);
    }
}