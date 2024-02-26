package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/putUserInformation")
    public Result putUserInformation(@RequestBody String putUserInformationDto) {
        int userId = StpUtil.getLoginIdAsInt();
        boolean flag = userService.putUserInformation(userId, putUserInformationDto);
        return flag ? Result.success("更新成功") : Result.fail("更新失败");
    }
}
