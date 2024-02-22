package org.talang.wabackend.sd;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class FileSaveAspect {


    // 切入点ImageComponent.saveImage 方法
    @Pointcut("execution(* org.talang.wabackend.sd.ImageComponent.saveImage(..))")
    public void saveImage() {
    }

    // 对于所有的saveImage方法，都会进行切面进行日记记录
    @After("saveImage()")
    public void afterSaveImage(JoinPoint joinPoint) {
        log.info("afterSaveImage");
    }
}
