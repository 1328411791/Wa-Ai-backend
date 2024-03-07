package org.talang.wabackend.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MailComponent {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    public static final String REGISTER_MAIL_PREFIX = "mail:register:";

    public static final String MAIL_PREFIX = "mail:";


    private void sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }


    public boolean sendRegisterMail(String to) {
        log.info("MailComponent.sendRegisterMail: " + to);

        String key = REGISTER_MAIL_PREFIX + to;


        String oldCode = stringRedisTemplate.opsForValue().get(key);
        String code = null;
        if (StrUtil.isEmpty(oldCode)){
            // 随机生成6位验证码
            code = RandomUtil.randomNumbers(6);
            stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        }else{
            // 5分钟内重复发送验证码
            code = oldCode;
        }
        // 设置该邮箱验证码已发送
        String mailKey = MAIL_PREFIX + to;
        String mailValue = stringRedisTemplate.opsForValue().get(mailKey);
        if (StrUtil.isNotBlank(mailValue)){
            // 1分钟内已经发送验证码
            return false;
        }
        // 设置该邮箱验证码已发送
        stringRedisTemplate.opsForValue().set(mailKey, "1", 1, TimeUnit.MINUTES);

        // 5分钟内重复发送验证码
        stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        sendMail(to, "注册验证码","您的验证码为" + code + "，请在5分钟内完成注册");

        return true;
    }
}
