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
import org.talang.sdk.SdWebui;
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

    @RabbitListener(queues = "PrintingTaskQueue",autoStartup = "true")
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
            }
        }catch (Exception e){
            log.error("Error: ", e);
            taskService.setFailStatus(taskMessage.getTaskId());
        }finally {
            multiSdWebUiConnect.returnSdWebui(sdWebui);
        }
    }
}
