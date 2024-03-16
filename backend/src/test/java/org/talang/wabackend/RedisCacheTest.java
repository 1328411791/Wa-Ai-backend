package org.talang.wabackend;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.service.UserService;
import org.talang.wabackend.util.RedisStrategyComponent;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisCacheTest {

    @Resource
    private RedisStrategyComponent redisStrategyComponent;

    @Resource
    private UserService userService;

    @Test
    public void userQueryTest() throws InterruptedException {
        User id = userService.getById(5);
        log.info("id:{}", id);

        User user = redisStrategyComponent.queryWithPassThrough("user:",
                5, userService::getById, 60L, TimeUnit.MINUTES);
        log.info("user:{}", user);
    }

}
