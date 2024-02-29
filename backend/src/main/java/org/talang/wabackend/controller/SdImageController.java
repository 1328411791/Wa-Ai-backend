package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;
import org.talang.wabackend.service.SdImageService;

@Tag(name = "Sd图片接口", description = "Sd图片接口")
@RestController
@RequestMapping("/sdImage")
public class SdImageController {

    @Autowired
    private SdImageService sdImageService;

    @Operation(summary = "获取Sd图片信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable String id) {
        SdImageVo image = sdImageService.getImageById(id);
        return Result.success(image);
    }
}
