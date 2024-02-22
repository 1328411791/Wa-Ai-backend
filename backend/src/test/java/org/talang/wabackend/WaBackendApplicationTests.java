package org.talang.wabackend;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.wabackend.service.TagService;

@Slf4j
@SpringBootTest
class WaBackendApplicationTests {

    @Resource
    private TagService tagService;

    @Test
    void contextLoads() {
        log.info(String.valueOf(tagService.getById(1)));
    }

}
