package org.talang.wabackend.aop;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.talang.wabackend.aop.annotation.RateLimiter;
import org.talang.wabackend.exception.BusinessErrorCode;
import org.talang.wabackend.exception.BusinessException;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class RedisRateLimiterAspect {

    private static final String LIMIT_KEY_PREFIX = "limit:";
    @Resource
    private Redisson redisson;

    @Pointcut("@annotation(org.talang.wabackend.aop.annotation.RateLimiter)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String ip = requestAttributes.getRequest().getRemoteAddr();
        log.info("进入限流切面,ip:{}", ip);
        // 获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        RateLimiter annotation = method.getAnnotation(RateLimiter.class);


        String key = annotation.key();

        String redisKey = LIMIT_KEY_PREFIX + key;
        RRateLimiter rateLimiter = redisson.getRateLimiter(redisKey);
        // 初始化

        rateLimiter.trySetRate(annotation.rateType(), annotation.count(), annotation.period(), annotation.rateTimeUnit());

        // 尝试获取锁
        boolean tryAcquire = rateLimiter.tryAcquire(annotation.permits(), annotation.timeout(), TimeUnit.SECONDS);
        if (!tryAcquire) {
            log.error("获取锁失败,IP:{},key:{}", ip, key);
            throw new BusinessException(BusinessErrorCode.RATE_LIMITER_ERROR, "获取锁失败,请稍后再试");

        }
        return joinPoint.proceed();
    }


}
