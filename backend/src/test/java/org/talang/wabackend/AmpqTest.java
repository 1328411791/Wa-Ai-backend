package org.talang.wabackend;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AmpqTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void ampqTest() {
        log.info("ampqTest");
        amqpTemplate.convertSendAndReceive("test", "test");
    }
}
