package org.talang.wabackend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.service.SdImageUserFavourService;

@RestController
@RequestMapping("/sdImageFavour")
public class SdImageUserFavourController {

    @Resource
    private SdImageUserFavourService sdImageUserFavourService;

    @PostMapping("/set")
    public Result setFavour(String sdImageId) {
        return sdImageUserFavourService.setFavour(sdImageId);
    }

    @GetMapping("/get")
    public Result getFavour(String sdImageId) {
        return sdImageUserFavourService.getFavour(sdImageId);
    }
}