package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.sd.ImageComponent;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/image")
@Tag(name = "Image", description = "图片API")
public class ImageController {

    @Resource
    private ImageComponent imageComponent;

    // 图片返回接口
    @GetMapping(value = "/id/{imagePath}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImageById(@PathVariable String imagePath) {
        log.info("获取图片id:{}", imagePath);

        // local image path regex "file://.*" or "123.png"
        if (imagePath.matches("https://.*")) {
            // set 302 redirect to path
            return ResponseEntity.status(302)
                    .header("Location", imagePath)
                    .build();
        } else if (imagePath.matches("file://.*")) {
            return ResponseEntity.ok(imageComponent.getImageById(imagePath.substring(7)));
        }

        return ResponseEntity.ok(imageComponent.getImageById(imagePath));
    }

    @PostMapping(value = "/save", produces = MediaType.TEXT_PLAIN_VALUE)
    public Result saveImage(@RequestParam MultipartFile image) throws IOException {
        log.info("保存图片");
        if (image.isEmpty()) {
            throw new RuntimeException("图片为空");
        }

        if (image.getName().matches(".*\\.(jpg|png|jpeg|gif|bmp)")) {
            throw new RuntimeException("图片格式不正确");
        }

        return Result.success(imageComponent.saveImage(image.getBytes(),
                image.getOriginalFilename()));
    }

}