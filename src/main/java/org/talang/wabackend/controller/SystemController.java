package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;

@RestController
@RequestMapping("/system")
@Tag(name = "System", description = "系统相关接口")
public class SystemController {

    @Operation(summary = "系统测试")
    @GetMapping("/systemTest")
    public Result systemTest() {
        return Result.success("System Test");
    }
}
