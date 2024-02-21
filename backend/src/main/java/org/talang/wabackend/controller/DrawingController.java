package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.sd.DrawImageComponent;

@Tag(name = "Printing", description = "画图API")
@RestController
@RequestMapping("/drawImage")
public class DrawingController {

    @Resource
    private DrawImageComponent drawImageComponent;

    @PostMapping("/txt2Image")
    public Result txt2Image(@RequestBody Txt2ImageOptions options) {
        String path = drawImageComponent.text2Image(options);
        return Result.success(path);
    }
}
