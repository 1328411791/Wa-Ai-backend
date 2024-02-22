package org.talang.wabackend;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.wabackend.service.ImageTagService;

@Slf4j
@SpringBootTest
class WaBackendApplicationTests {

    @Resource
    private ImageTagService tagService;

    @Test
    void contextLoads() {
        log.info(String.valueOf(tagService.getById(2)));
    }

}
