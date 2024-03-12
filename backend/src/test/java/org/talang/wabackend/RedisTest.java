package org.talang.wabackend;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.wabackend.util.SdImageLikeComponent;

import java.util.Set;

@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private SdImageLikeComponent sdImageLikeComponent;

   // @Test
    @Ignore
    void test() {
        Set<String> allImageLikeCount = sdImageLikeComponent.getAllImageLikeCount();
        log.info("test");
    }
}
