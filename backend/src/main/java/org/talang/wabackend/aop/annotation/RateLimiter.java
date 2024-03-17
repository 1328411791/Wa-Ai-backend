package org.talang.wabackend.aop.annotation;

import jakarta.validation.constraints.NotNull;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 */

@Target(ElementType.METHOD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流的key
     */
    @NotNull
    String key();

    /**
     * 限流的类型
     */
    RateType rateType() default RateType.OVERALL;

    /**
     * 限流的时间
     */
    int count() default 1;

    /**
     * 限流的次数
     */
    int period() default 1;

    /**
     * 限流的单位
     */
    RateIntervalUnit rateTimeUnit() default RateIntervalUnit.SECONDS;

    /**
     * 许可证获取数量
     */
    int permits() default 1;

    /**
     * 许可证获取超时时间
     */
    long timeout() default 3;

    /**
     * 许可证获取超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;


}
