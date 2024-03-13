package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.mail.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;
import org.talang.wabackend.service.SdImageService;

import java.util.Date;

@Tag(name = "Sd图片接口", description = "Sd图片接口")
@RestController
@RequestMapping("/sdImage")
public class SdImageController {

    private final SdImageService sdImageService;

    public SdImageController(SdImageService sdImageService) {
        this.sdImageService = sdImageService;
    }

    @Operation(summary = "获取Sd图片信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable String id) {
        Integer userId = 0;
        if (StpUtil.isLogin()){
            userId = StpUtil.getLoginIdAsInt();
        }
        SdImageVo image = sdImageService.getImageById(userId,id);
        return Result.success(image);
    }

    @Operation(summary = "获取我的所有SD图片列表")
    @GetMapping("/getMyAllList")
    public Result getMyAllList(@RequestParam(defaultValue = "0") String startTimeStamp,
                               @RequestParam(defaultValue = "0") String endTimeStamp,
                               @RequestParam(defaultValue = "true") boolean myGenerate,
                               @RequestParam(defaultValue = "true") boolean myUpload,
                               Integer page, Integer pageSize) {

        return sdImageService.getMyAllList(startTimeStamp, endTimeStamp,
                myGenerate, myUpload,
                page, pageSize);
    }

    @PostMapping("/upload")
    public Result upLoadSdImageByUser(MultipartFile img) {
        return sdImageService.upLoadSdImageByUser(img);
    }
}
