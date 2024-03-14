package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.user.PutUserInformationDto;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.model.vo.user.UserVo;
import org.talang.wabackend.service.UserService;

import java.io.IOException;

@Tag(name = "用户", description = "用户API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "更新用户信息")
    @PutMapping("/putUserInformation")
    public Result putUserInformation(@RequestBody PutUserInformationDto putUserInformationDto) {
        int userId = StpUtil.getLoginIdAsInt();
        boolean flag = userService.putUserInformation(userId, putUserInformationDto);
        return flag ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @Operation(description = "获取用户信息")
    @PutMapping("/{id}")
    public Result getUserInformation(@PathVariable Integer id) {
        User user = userService.getById(id);
        UserVo userVo = BeanUtil.toBean(user, UserVo.class);
        return Result.success(userVo);
    }

    @Operation(description = "更新用户头像")
    @PostMapping("/uploadAvatar")
    public Result updateAvatar(MultipartFile img) throws IOException {
        return userService.updateAvatar(img);
    }
}
