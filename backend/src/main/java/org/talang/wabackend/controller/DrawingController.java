package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.sd.StartDrawComponent;

@Tag(name = "画图相关接口", description = "画图API")
@RestController
@RequestMapping("/drawImage")
public class DrawingController {

    @Resource
    private StartDrawComponent startDrawComponent;

    @Operation(description = "文字转图片")
    @PostMapping("/txt2Image")
    public Result txt2Image(@RequestBody Txt2ImageOptions options) {
        int userId = StpUtil.getLoginIdAsInt();
        String taskId = startDrawComponent.startTxt2ImageRequest(userId, options);
        return Result.success(taskId);
    }

    @Operation(description = "超分图片接口")
    @PostMapping("/extraImage")
    public Result extraImage(@RequestBody ExtraImageOptions extraImageOptions) {
        int userId = StpUtil.getLoginIdAsInt();
        String taskId = startDrawComponent.startExtraImageRequest(userId,extraImageOptions);
        return Result.success(taskId);
    }
}
