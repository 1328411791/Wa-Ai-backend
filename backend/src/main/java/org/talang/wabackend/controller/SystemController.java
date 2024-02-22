package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.SystemInfo;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.sd.MultiSdWebUiConnect;

import java.util.List;

@RestController
@RequestMapping("/system")
@Tag(name = "System", description = "系统相关接口")
public class SystemController {

    @Autowired
    private MultiSdWebUiConnect multiSdWebUiConnect;

    @Operation(summary = "系统测试")
    @GetMapping("/systemTest")
    public Result systemTest() {
        return Result.success("System Test");
    }

    @Operation(summary = "WebUI信息")
    @GetMapping("/webuiInfo")
    public Result WebUIInfo() throws InterruptedException {
        List<SdWebui> webuiInfo = multiSdWebUiConnect.getSdWebuiList();
        List<SystemInfo> sdSystemInfo = null;
        for (SdWebui sdWebui : webuiInfo) {
            sdSystemInfo.add(sdWebui.systemInfo());
        }
        return Result.success(sdSystemInfo);
    }
}
