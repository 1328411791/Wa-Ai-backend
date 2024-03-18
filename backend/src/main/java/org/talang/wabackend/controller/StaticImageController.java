package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.service.StaticImageService;

import java.util.HashMap;

@Tag(name = "静态图片接口", description = "静态图片接口")
@RestController
@RequestMapping("/staticImage")
public class StaticImageController {

    @Resource
    private StaticImageService staticImageService;
    @GetMapping("/url")
    Result getUrlByStaticImageId(String id) {
        String url = staticImageService.getStaticImagePathById(id);
        HashMap<String, String> result = new HashMap<>();
        result.put("url", url);
        return Result.success(result);
    }
}
