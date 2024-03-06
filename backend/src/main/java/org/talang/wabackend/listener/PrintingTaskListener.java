package org.talang.wabackend.listener;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.talang.wabackend.model.generator.Task;
import org.talang.wabackend.sd.DrawImageComponent;
import org.talang.wabackend.sd.utiliy.TaskMessage;
import org.talang.wabackend.service.TaskService;

@Slf4j
@Component
public class PrintingTaskListener {

    @Resource
    private TaskService taskService;

    @Resource
    private DrawImageComponent drawImageComponent;

    @RabbitListener(queues = "PrintingTaskQueue",autoStartup = "true")
    public void onMessage(String message) {
        log.info("PrintingTaskListener: " + message);
        TaskMessage taskMessage = JSONUtil.toBean(message, TaskMessage.class);
        Task task = taskService.getById(taskMessage.getTaskId());
        switch (taskMessage.getTaskType()) {
            case TXT2IMAGE:
                drawImageComponent.text2Image(taskMessage.getTaskId(), task.getUserId(), task.getTxt2imageOptions());
                break;
            case EXTRA_IMAGE:
                drawImageComponent.extraImage(taskMessage.getTaskId(), task.getUserId(), task.getExtraimageOptions());
                break;
            default:
                log.error("Unknown task type: " + taskMessage.getTaskType());
        }

    }
}
