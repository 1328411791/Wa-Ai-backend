package org.talang.wabackend.sdk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.sdk.models.results.Txt2ImgResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootTest
public class Text2ImageTest {

    @Autowired
    private SdWebui sdWebui;

    // 测试时候跳过
    @Test
    public void text2ImageTest() throws IOException {
        Txt2ImgResult txt2ImgResult = sdWebui.txt2Img(Txt2ImageOptions.builder()
                .prompt("1dog")
                .samplerName("DPM++ 2M Karras")
                .steps(20)
                .cfgScale(7)
                .seed(32749528)
                .build());

        Path step1Path = Paths.get("C:\\Users\\Administrator\\Desktop\\step1.png");
        Files.write(step1Path, Base64.getDecoder().decode(txt2ImgResult.getImages().get(0)));
    }
}