package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.model.ModelFavoriteDto;
import org.talang.wabackend.service.ModelFavoriteService;

@Tag(name = "模型收藏", description = "模型收藏相关接口")
@RestController
@RequestMapping("/sdModel/favorites")
public class ModelFavoriteController {

    @Autowired
    ModelFavoriteService modelFavoriteService;

    @Operation(summary = "对sd模型添加收藏")
    @GetMapping("/add/{modelId}")
    public Result addModelFavorite(@PathVariable Integer modelId) {
        return modelFavoriteService.addOrCancelFavoriteModel(modelId) ? Result.success("收藏成功") : Result.success("取消收藏成功");
    }

    @Operation(summary = "查询收藏的模型", description = "userId为空时，为查询自己收藏的模型")
    @PostMapping("/getMyFavoriteModel")
    public Result getModelFavorite(@RequestBody ModelFavoriteDto modelFavoriteDto) {
        return Result.success(modelFavoriteService.getMyFavoriteModel(modelFavoriteDto));
    }
}
