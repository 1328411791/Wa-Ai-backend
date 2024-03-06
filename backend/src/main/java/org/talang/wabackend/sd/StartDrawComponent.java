package org.talang.wabackend.sd;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.wabackend.sd.utiliy.PrintingQueueComponent;
import org.talang.wabackend.sd.utiliy.TaskMessage;
import org.talang.wabackend.sd.utiliy.TaskTypeEnum;
import org.talang.wabackend.service.TaskService;

@Slf4j
@Component
public class StartDrawComponent {

    @Resource
    private DrawImageComponent drawImageComponent;

    @Resource
    private TaskService taskService;

    @Resource
    private PrintingQueueComponent printingQueueComponent;

    public String startTxt2ImageRequest(int userId, Txt2ImageOptions options) {
        String taskId = taskService.setCreateStatus(userId, options);
        log.info("startTxt2ImageRequest taskId:{}", taskId);
        // 异步执行
        //drawImageComponent.text2Image(taskId, userId, options);

        // 发送到消息队列
        printingQueueComponent.sendTask(new TaskMessage(taskId, TaskTypeEnum.TXT2IMAGE));

        return taskId;
    }


    public String startExtraImageRequest(int userId, ExtraImageOptions extraImageOptions) {
        String taskId = taskService.setCreateStatus(userId, extraImageOptions);
        log.info("startExtraImageRequest taskId:{}", taskId);
        // 异步执行
        // drawImageComponent.extraImage(taskId, userId, extraImageOptions);

        printingQueueComponent.sendTask(new TaskMessage(taskId, TaskTypeEnum.EXTRA_IMAGE));

        return taskId;
    }
}
