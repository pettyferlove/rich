package com.github.rich.common.core.constants;

/**
 * @author Petty
 */
public interface RabbitMqQueueConstant {

    /**
     * 服务状态变更消息队列
     */
    String SERVICE_STATUS_CHANGE_QUEUE = "service_status_change_queue";

    /**
     * 服务状态变更消息交换机
     */
    String SERVICE_STATUS_CHANGE_EXCHANGE = "service_status_change_exchange";

    /**
     * 短信消息队列
     */
    String SERVICE_SMS_QUEUE = "service_sms_queue";
}
