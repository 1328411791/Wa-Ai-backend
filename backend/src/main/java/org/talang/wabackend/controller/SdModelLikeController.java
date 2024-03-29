package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.LikeResult;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.util.SdModelLikeComponent;

@Tag(name = "Sd模型点赞接口", description = "Sd模型点赞接口")
@RestController
@RequestMapping("/sdModel/like")
public class SdModelLikeController {

    @Autowired
    private SdModelLikeComponent sdModelLikeComponent;

    @Operation(summary = "点赞")
    @PostMapping("/liked")
    public Result like(@RequestParam Integer sdmodelId) {
        Integer userId = StpUtil.getLoginIdAsInt();
        boolean flag = sdModelLikeComponent.like(sdmodelId, userId);
        Long count = sdModelLikeComponent.getLikeCount(String.valueOf(sdmodelId));
        LikeResult likeResult = new LikeResult();
        likeResult.setLikeCount(count);
        likeResult.setIsLike(flag);
        return Result.success(likeResult);
    }

    @Operation(summary = "检测是否点赞")
    @PostMapping("/isLiked")
    public Result isLiked(@RequestParam Integer sdmodelId) {
        Integer userId = StpUtil.getLoginIdAsInt();
        boolean flag = sdModelLikeComponent.isLiked(sdmodelId, userId);
        return flag?Result.success("已点赞"):Result.success("未点赞");
    }
}
