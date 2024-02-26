package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.service.UserService;

@Tag(name = "用户", description = "用户API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "更新用户信息")
    @PutMapping("/putUserInformation")
    public Result putUserInformation(@RequestBody String putUserInformationDto) {
        int userId = StpUtil.getLoginIdAsInt();
        boolean flag = userService.putUserInformation(userId, putUserInformationDto);
        return flag ? Result.success("更新成功") : Result.fail("更新失败");
    }
}
