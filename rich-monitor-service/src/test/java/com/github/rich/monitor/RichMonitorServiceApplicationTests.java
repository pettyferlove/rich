package com.github.rich.monitor;

import com.github.rich.common.core.constants.RabbitMqQueueConstant;
import com.github.rich.common.core.dto.Message;
import com.github.rich.common.core.service.IMessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RichMonitorServiceApplicationTests {

    @Autowired
    private IMessageSender messageSender;

    @Test
    public void contextLoads() {
        messageSender.send(RabbitMqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE,new Message("1","1","1","1"));
    }

}
