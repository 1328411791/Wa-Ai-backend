package org.talang.wabackend.sd.impl;

import com.qiniu.storage.model.DefaultPutRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.talang.wabackend.sd.ImageComponent;
import org.talang.wabackend.service.SdImageService;
import org.talang.wabackend.service.StaticImageService;
import org.talang.wabackend.util.QiniuStorageUtiliy;

@Slf4j
@Component()
@ConditionalOnProperty(name = "sdwebui.image.save-way", havingValue = "qiniu")
public class QiniuStorgeComponentImpl implements ImageComponent {

    @Autowired
    private QiniuStorageUtiliy qiniuStorageUtiliy;

    @Value("${sdwebui.image.qiniu.domain}")
    private String domain;

    @Value("${sdwebui.image.save-path}")
    private String savePath;

    @Autowired
    private StaticImageService staticImageService;

    @Autowired
    private SdImageService sdImageService;

    @Override
    public String saveImage(byte[] image, Integer userId) {

        String fileName = System.currentTimeMillis() + ".png";

        return saveImage(image, userId, fileName);
    }

    @Override
    public String saveImage(byte[] image, Integer userId, String fileName) {
        String key = savePath + "/" + fileName;

        log.info("开始上传图片，图片名称：{} key:{}", fileName, key);
        DefaultPutRet uploadRet = qiniuStorageUtiliy.uploadImage(image, key);

        String filePath = domain + "/" + uploadRet.key;

        String imageId = staticImageService.saveImage(fileName, filePath, uploadRet.hash, userId);

        log.info("图片上传成功，图片ID：{} url:{}", imageId, filePath);

        return imageId;
    }

    @Override
    public byte[] getImageById(String id) {
        throw new RuntimeException("不支持的操作");
    }
}
