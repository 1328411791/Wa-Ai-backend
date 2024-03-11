package org.talang.wabackend.sd.utiliy;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talang.wabackend.config.PrintingTaskQueueConfig;

@Slf4j
@Component
public class PrintingQueueComponent {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendTask(TaskMessage message) {
        log.info("Sending message: " + message.toString());
        MessagePostProcessor messagePostProcessor = message1 -> {
            message1.getMessageProperties().setPriority(message.getPriority());
            return message1;
        };

        amqpTemplate.convertAndSend(PrintingTaskQueueConfig.PRINTING_EXCHANGE_NAME
                ,"" , JSONUtil.toJsonStr(message),messagePostProcessor);
    }


}
