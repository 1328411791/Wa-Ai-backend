package org.talang.wabackend.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.talang.sdk.SdWebui;
import org.talang.wabackend.sd.MultiSdWebUiConnect;

import java.util.List;

@Slf4j
@Configuration
public class SdWebUIConfig {

    // 从配置文件sdwebui.sd[0].url 中读取url数组
    @Value("${sdwebui.url}")
    private List<String> urls;


    @Bean
    public MultiSdWebUiConnect createMultiSdWebui() {
        MultiSdWebUiConnect multiSdWebUiConnect = new MultiSdWebUiConnect();
        for (String url : urls) {
            SdWebui sdWebui = SdWebui.create(url);
            multiSdWebUiConnect.addSdWebui(sdWebui);
            log.info("SdWebui created url:{}", url);
        }
        return multiSdWebUiConnect;
    }


    /*
    @Bean
    public SdWebui createSdWebui() {
        SdWebui sdWebui = SdWebui.create(urls.get(0));
        log.info("SdWebui created url:{}", urls.get(0));
        return sdWebui;
    }
     */
}
