package org.talang.wabackend.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.talang.sdk.SdWebui;

@Slf4j
@Configuration
public class SdWebUIConfig {

    @Value("${sdwebui.url}")
    private String url;

    @Bean
    public SdWebui createSdWebui() {
        SdWebui sdWebui = SdWebui.create(url);
        log.info("SdWebui created");
        return sdWebui;
    }
}
