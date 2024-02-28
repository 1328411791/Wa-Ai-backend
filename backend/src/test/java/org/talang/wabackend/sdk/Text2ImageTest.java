package org.talang.wabackend.sdk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.sd.MultiSdWebUiConnect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootTest
public class Text2ImageTest {

    @Autowired
    private MultiSdWebUiConnect sdWebui;

    @Test
    public void text2ImageTest() throws IOException {
        Txt2ImgResult txt2ImgResult = sdWebui.getAvailableSdWebui().txt2Img(Txt2ImageOptions.builder()
                .prompt("1dog")
                .samplerName("DPM++ 2M Karras")
                .steps(20)
                .cfgScale(7)
                .seed(32749528)
                .build());

        // 获取当前工作目录路径
        String currentDirectory = System.getProperty("user.dir");
        // 构建新文件的路径
        Path filePath = Paths.get(currentDirectory, "txt2img-dog.png");

        Files.write(filePath, Base64.getDecoder().decode(txt2ImgResult.getImages().get(0)));
    }
}
