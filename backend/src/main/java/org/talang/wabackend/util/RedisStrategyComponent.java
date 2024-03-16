package org.talang.wabackend.util;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Component
public class RedisStrategyComponent {

    @Resource
    private RedisCache redisCache;

    /**
     * 查询缓存，如果缓存不存在则查询数据库，并将查询结果放入缓存
     *
     * @param prefix   缓存前缀
     * @param id       主键
     * @param type     返回类型
     * @param fallback 查询数据库的方法
     * @param <ID>     主键类型
     * @param <R>      返回类型
     * @return 查询结果
     */
    public <ID, R> R queryWithPassThrough(final String prefix, ID id
            , Class<R> type, Function<ID, R> fallback, Long time, TimeUnit timeUnit) {
        String redisKey = prefix + id;

        R result = redisCache.getCacheObject(redisKey);

        if (result != null) {
            // 缓存命中
            return result;
        }

        // 缓存未命中

        // 查询数据库
        result = fallback.apply(id);

        if (result != null) {
            // 将查询结果放入缓存,防止缓存雪崩
            long randomTime = RandomUtil.randomLong(60, 300);
            long relayTime = TimeUnit.SECONDS.convert(time, timeUnit) + randomTime;
            redisCache.setCacheObject(redisKey, result, relayTime, TimeUnit.SECONDS);
        }

        return result;
    }

}
