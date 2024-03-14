package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.service.SdImageUserFavourService;

@Tag(name = "Sd图片收藏接口", description = "Sd图片收藏接口")
@RestController
@RequestMapping("/sdImageFavour")
public class SdImageUserFavourController {

    @Resource
    private SdImageUserFavourService sdImageUserFavourService;

    @Operation(description = "收藏/取消收藏")
    @PostMapping("/set")
    public Result setFavour(String sdImageId) {
        return sdImageUserFavourService.setFavour(sdImageId);
    }

    @Operation(description = "获取当前SD图片是否收藏")
    @GetMapping("/get")
    public Result getFavour(String sdImageId) {
        return sdImageUserFavourService.getFavour(sdImageId);
    }
}