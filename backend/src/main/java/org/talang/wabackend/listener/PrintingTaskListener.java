package org.talang.wabackend.listener;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;
import org.talang.wabackend.config.PrintingTaskQueueConfig;
import org.talang.wabackend.exception.TaskFailException;
import org.talang.wabackend.model.generator.Task;
import org.talang.wabackend.sd.DrawImageComponent;
import org.talang.wabackend.sd.MultiSdWebUiConnect;
import org.talang.wabackend.sd.utiliy.TaskMessage;
import org.talang.wabackend.service.TaskService;

@Slf4j
@Component
public class PrintingTaskListener {

    @Resource
    private TaskService taskService;

    @Resource
    private DrawImageComponent drawImageComponent;

    @Resource
    private MultiSdWebUiConnect multiSdWebUiConnect;

    @RabbitListener(queues = PrintingTaskQueueConfig.PRINTING_QUEUE_NAME
            ,autoStartup = "true",ackMode = "AUTO")
    public void onMessage(String message) {
        SdWebui sdWebui = multiSdWebUiConnect.getAvailableSdWebui();
        TaskMessage taskMessage = JSONUtil.toBean(message, TaskMessage.class);
        try {
            log.info("PrintingTaskListener: " + message);

            Task task = taskService.getById(taskMessage.getTaskId());

            switch (taskMessage.getTaskType()) {
                case TXT2IMAGE:
                    drawImageComponent.text2Image(taskMessage.getTaskId(), task.getUserId(), task.getTxt2imageOptions(),sdWebui);
                    break;
                case EXTRA_IMAGE:
                    drawImageComponent.extraImage(taskMessage.getTaskId(), task.getUserId(), task.getExtraimageOptions(),sdWebui);
                    break;
                default:
                    log.error("Unknown task type: " + taskMessage.getTaskType());
                    throw new TaskFailException(taskMessage.getTaskId(),
                            "Unknown task type: " + taskMessage.getTaskType());
            }
        }catch (RuntimeException e){
            log.error("Error: ", e);
            // taskService.setFailStatus(taskMessage.getTaskId());
            throw new AmqpRejectAndDontRequeueException(e);
        }finally {
            multiSdWebUiConnect.returnSdWebui(sdWebui);
        }
    }


    @RabbitListener(queues = PrintingTaskQueueConfig.PRINTING_DEAD_QUEUE_NAME
            ,autoStartup = "true")
    public void onDeadMessage(String message) {
        log.info("PrintingDeadTaskListener: " + message);
        TaskMessage taskMessage = JSONUtil.toBean(message, TaskMessage.class);
        taskService.setFailStatus(taskMessage.getTaskId());
    }
}
