package org.talang.wabackend.sd.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.sdk.models.results.Txt2ImgResult;
import org.talang.wabackend.sd.DrawImageComponent;
import org.talang.wabackend.sd.ImageComponent;

import java.util.Base64;

@Component
public class DrawImageComponentImpl implements DrawImageComponent {

    @Autowired
    private SdWebui sdWebui;

    // 如果sayWay = local 注入LocalFileSaveComponentImpl, qiniu 注入QiniuSaveImageComponentImpl
    @Value("${sdwebui.image.save-way}")
    private String saveWay;

    @Resource
    private ImageComponent imageComponent;


    @Override
    public String text2Image(Txt2ImageOptions options) {

        Txt2ImgResult txt2ImgResult = sdWebui.txt2Img(options);

        byte[] decode = Base64.getDecoder().decode(txt2ImgResult.getImages().get(0));

        String imagePath = imageComponent.saveImage(decode);

        return switch (saveWay) {

            case "local" -> "file://" + imagePath;

            case "qiniu" -> "http://qiniu.com/" + imagePath;

            default -> throw new RuntimeException("不支持的保存方式");
        };

    }
}
