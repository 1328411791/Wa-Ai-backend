package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.user.ForgetPasswordDto;
import org.talang.wabackend.model.dto.user.LoginDto;
import org.talang.wabackend.model.dto.user.RegisterDto;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.service.UserService;

@RestController
@RequestMapping("/account")
@Tag(name = "账户管理", description = "账户管理相关接口")
public class AccountController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        if (StrUtil.isEmpty(loginDto.getUserName()) && StrUtil.isEmpty(loginDto.getEmail())) {
            return Result.fail("用户名或邮箱不能同时为空");
        }

        if (StrUtil.isEmpty(loginDto.getPassword())) {
            return Result.fail("密码不能为空");
        }

        if (StrUtil.isNotEmpty(loginDto.getUserName()) && StrUtil.isNotEmpty(loginDto.getEmail())) {
            return Result.fail("用户名和邮箱不能同时存在");
        }

        Integer userId;
        if (StrUtil.isNotEmpty(loginDto.getUserName())) {
            userId = userService.loginByUserName(loginDto.getUserName(), loginDto.getPassword());
        } else {
            userId = userService.loginByEmail(loginDto.getEmail(), loginDto.getPassword());
        }
        if (userId == null) {
            return Result.fail("用户名或密码错误");
        }

        StpUtil.login(userId, loginDto.isRememberMe());

        return Result.success("登录成功");
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success("登出成功");
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public Result info() {
        Integer userId = StpUtil.getLoginIdAsInt();
        return Result.success(userService.getMe(userId));
    }

    @Operation(summary = "id反查username")
    @GetMapping("/getUsernameById")
    public Result username(
            @RequestParam Integer userId
    ) {
        return Result.success(userService.getUsernameById(userId));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result register(@RequestBody RegisterDto registerDto) {
        if (StrUtil.isEmpty(registerDto.getUserName())) {
            return Result.fail("用户名不能为空");
        }

        if (StrUtil.isEmpty(registerDto.getEmail())) {
            return Result.fail("邮箱不能为空");
        }

        if (StrUtil.isEmpty(registerDto.getPassword())) {
            return Result.fail("密码不能为空");
        }

        if (!registerDto.getPassword().equals(registerDto.getRePassword())) {
            return Result.fail("两次密码不一致");
        }

        if (userService.getByUserName(registerDto.getUserName()) != null) {
            return Result.fail("用户名已存在");
        }

        userService.register(registerDto);

        return Result.success("注册成功");
    }

    @PostMapping("/forgetPassword")
    @Operation(summary = "忘记密码")
    public Result forgetPassword(@RequestBody ForgetPasswordDto forgetPasswordDto) {
        if (StrUtil.isEmpty(forgetPasswordDto.getUserName())) {
            return Result.fail("用户名不能为空");
        }
        if (StrUtil.isEmpty(forgetPasswordDto.getEmail())) {
            return Result.fail("邮箱不能为空");
        }

        User user = userService.forgetPassword(forgetPasswordDto);

        return Result.success(user.getPassword());
    }

    @GetMapping("/isLogin")
    @Operation(summary = "是否登录")
    public Result isLogin() {
        return Result.success(StpUtil.isLogin());
    }
}