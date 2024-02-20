package org.talang.wabackend.config;

import io.github.robothy.sdwebui.sdk.SdWebui;
import io.github.robothy.sdwebui.sdk.models.options.Txt2ImageOptions;
import io.github.robothy.sdwebui.sdk.models.results.Txt2ImgResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

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
