package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.vo.sdImage.SdImageVo;
import org.talang.wabackend.service.SdImageService;

import java.util.Date;

@Tag(name = "Sd图片接口", description = "Sd图片接口")
@RestController
@RequestMapping("/sdImage")
public class SdImageController {

    @Autowired
    private SdImageService sdImageService;

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
    public Result getMyAllList(@DefaultValue("0") Date startTimeStamp,
                               @DefaultValue("0") Date endTimeStamp,
                               @DefaultValue("true") boolean myGenerate,
                               @DefaultValue("true") boolean myUpload,
                               Integer page, Integer pageSize) {
        return sdImageService.getMyAllList(startTimeStamp, endTimeStamp,
                myGenerate, myUpload,
                page, pageSize);
    }
}
