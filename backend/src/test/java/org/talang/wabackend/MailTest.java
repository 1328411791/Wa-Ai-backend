package org.talang.wabackend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailTest {

    @Autowired
    private MailSender mailSender;

    @Test
    public void testSendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1328411791@qq.com");
        message.setTo("1328411791@qq.com");
        message.setSubject("Spring Boot Mail");
        message.setText("This is a test mail");
        mailSender.send(message);
    }


}
