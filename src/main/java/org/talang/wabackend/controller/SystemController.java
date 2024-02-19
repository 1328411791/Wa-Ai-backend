package org.talang.wabackend.controller;

import io.github.robothy.sdwebui.sdk.SdWebui;
import io.github.robothy.sdwebui.sdk.models.options.Txt2ImageOptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
@RestController
@RequestMapping("/system")
@Tag(name = "System", description = "系统相关接口")
public class SystemController {

    @Autowired
    private SdWebui sdWebui;

    @Operation(summary = "系统测试")
    @GetMapping("/systemTest")
    public Result systemTest() {
        return Result.success("System Test");
    }

    @Operation(summary = "WebUI信息")
    @GetMapping("/webuiInfo")
    public Result WebUIInfo() {
        return Result.success(sdWebui.systemInfo());
    }
}
