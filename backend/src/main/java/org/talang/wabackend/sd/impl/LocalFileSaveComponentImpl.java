package org.talang.wabackend.sd.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.talang.wabackend.sd.ImageComponent;
import org.talang.wabackend.service.StaticImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@ConditionalOnProperty(name = "sdwebui.image.save-way", havingValue = "local")
public class LocalFileSaveComponentImpl implements ImageComponent {

    @Value("${sdwebui.image.save-path}")
    private String IMAGE_SAVE_PATH = null;

    // 协议前缀
    private static final String FILE_PROTOCOL = "file://";

    @Resource
    private StaticImageService staticImageService;

    @Override
    public String saveImage(byte[] image) {

        String fileName = System.currentTimeMillis() + ".png";

        return saveImage(image, fileName);
    }

    @Override
    public String saveImage(byte[] image, String fileName) {
        Path filePath = Paths.get(IMAGE_SAVE_PATH, fileName);

        try {
            Files.write(filePath, image);
        } catch (IOException e) {
            log.error("保存图片失败", e);
            throw new RuntimeException("保存图片失败");
        }

        String imageId = staticImageService.saveImage(fileName, FILE_PROTOCOL + fileName);

        return imageId;
    }

    @Override
    public byte[] getImageById(String id) {

        File file = new File(IMAGE_SAVE_PATH, id);

        // 读入文件
        byte[] image;
        try {
            image = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            log.error("读取图片失败", e);
            throw new RuntimeException("读取图片失败");
        }
        return image;
    }
}
