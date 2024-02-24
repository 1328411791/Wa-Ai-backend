package org.talang.wabackend.sd;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.wabackend.service.TaskService;

@Slf4j
@Component
public class StartDrawComponent {

    @Resource
    private DrawImageComponent drawImageComponent;

    @Resource
    private TaskService taskService;

    public String startTxt2ImageRequest(int userId, Txt2ImageOptions options) {
        String taskId = taskService.setCreateStatus(userId, options);
        log.info("startTxt2ImageRequest taskId:{}", taskId);
        // 异步执行
        drawImageComponent.text2Image(taskId, options);

        return taskId;
    }


    public String startExtraImageRequest(int userId, Txt2ImageOptions txt2ImageOptions, ExtraImageOptions extraImageOptions) {
        String taskId = taskService.setCreateStatus(userId, txt2ImageOptions, extraImageOptions);
        log.info("startExtraImageRequest taskId:{}", taskId);
        // 异步执行
        drawImageComponent.extraImage(taskId, txt2ImageOptions, extraImageOptions);

        return taskId;
    }
}
