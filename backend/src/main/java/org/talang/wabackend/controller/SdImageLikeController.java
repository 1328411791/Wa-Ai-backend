package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talang.wabackend.common.LikeResult;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.util.SdImageLikeComponent;

@Tag(name = "Sd图片点赞接口", description = "Sd图片点赞接口")
@RestController
@RequestMapping("/sdImage/like")
public class SdImageLikeController {

    @Autowired
    private SdImageLikeComponent sdImageLikeComponent;

    @Operation(summary = "点赞")
    @PostMapping("/liked")
    public Result like(@RequestParam String sdImageId) {
        Integer userId = StpUtil.getLoginIdAsInt();
        boolean flag = sdImageLikeComponent.like(sdImageId, userId);
        Long likeCount = sdImageLikeComponent.getLikeCount(sdImageId);
        LikeResult likeResult = new LikeResult();
        likeResult.setLikeCount(likeCount);
        likeResult.setIsLike(flag);
        return Result.success(likeResult);
    }

    @Operation(summary = "检测是否点赞")
    @GetMapping("/isLiked")
    public Result isLiked(@RequestParam String sdImageId) {
        Integer userId = StpUtil.getLoginIdAsInt();
        boolean flag = sdImageLikeComponent.isLiked(sdImageId, userId);
        return flag?Result.success("已点赞"):Result.success("未点赞");
    }
}
