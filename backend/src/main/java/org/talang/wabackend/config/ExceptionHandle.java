package org.talang.wabackend.config;


import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.talang.wabackend.common.Result;

@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(NotLoginException.class)
    public Result handleNotLoginException(NotLoginException e) {
        log.error(e.toString(), e);
        return Result.fail("未登录，请先登录");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.toString(), e);
        return Result.fail(e.getMessage());
    }

}
