package org.talang.wabackend.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talang.wabackend.sd.ImageComponent;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private ImageComponent imageComponent;

    // 图片返回接口
    @GetMapping(value = "/id/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImageById(@PathVariable String id) {
        log.info("获取图片id:{}", id);

        return ResponseEntity.ok(imageComponent.getImageById(id));
    }

    @PostMapping(value = "/save", produces = MediaType.TEXT_PLAIN_VALUE)
    public String saveImage(@RequestParam MultipartFile image) throws IOException {
        log.info("保存图片");
        if (image.isEmpty()) {
            throw new RuntimeException("图片为空");
        }

        if (image.getName().matches(".*\\.(jpg|png|jpeg|gif|bmp)")) {
            throw new RuntimeException("图片格式不正确");
        }

        return imageComponent.saveImage(image.getBytes());
    }

}
