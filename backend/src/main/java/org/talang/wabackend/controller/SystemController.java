package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.results.SdModel;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.sd.MultiSdWebUiConnect;
import org.talang.wabackend.websocket.SdTaskWebSocket;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system")
@Tag(name = "System", description = "系统相关接口")
public class SystemController {

    @Autowired
    private MultiSdWebUiConnect multiSdWebUiConnect;
    @Resource
    private SdTaskWebSocket sdTaskWebSocket;


    @Operation(summary = "系统测试")
    @GetMapping("/systemTest")
    public Result systemTest() {
        return Result.success("System Test");
    }

    @Operation(summary = "WebUI Model信息")
    @GetMapping("/SdModelInfo")
    public Result WebUIInfo() {
        List<SdWebui> webuiInfo = multiSdWebUiConnect.getWebUIList();
        List<List<SdModel>> sdModels = new ArrayList<>();
        for (SdWebui sdWebui : webuiInfo) {
            sdModels.add(sdWebui.getSdModels());
        }
        return Result.success(sdModels);
    }

    @Operation(summary = "权限测试")
    @GetMapping("/authTest")
    public Result authTest() {
        List<String> list = StpUtil.getRoleList();
        return Result.success(list);
    }

    @Operation(summary = "Websock 群发消息")
    @PostMapping("/sendAll")
    public String sendAllMessage(String message) {
        sdTaskWebSocket.sendAllMessage(message);
        return "success";
    }

    @Operation(summary = "Websock 对指定用户发送消息")
    @PostMapping("/sendOne")
    public String sendOneMessage(String message, Integer userId) {
        sdTaskWebSocket.sendOneMessage(userId,message);
        return "success";
    }

    @Operation(summary = "Websock 页面")
    @GetMapping("/webSocket")
    public ModelAndView socket() {
        ModelAndView mav=new ModelAndView("/webSocket");
        mav.addObject("userId", 1);
        return mav;
    }

}
