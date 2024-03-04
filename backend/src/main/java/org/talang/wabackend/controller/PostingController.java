package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.posting.CreatePostingDto;
import org.talang.wabackend.service.PostingService;

import java.util.List;

@Tag(name = "帖子", description = "帖子相关接口")
@RestController()
@RequestMapping("/posting")
public class PostingController {

    @Autowired
    private PostingService postingService;

    @Operation(summary = "创建帖子", description = "任务相关接口")
    @PostMapping("/create")
    public Result createPosting(@RequestBody CreatePostingDto createPostingDto) {
        postingService.createPosting(createPostingDto);
        return Result.success();
    }

    @Operation(summary = "删除帖子", description = "任务相关接口")
    @PostMapping("/delete")
    public Result deletePosting(@RequestBody List<Integer> posting) {
        postingService.deletePosting(posting);
        return Result.success();
    }

    @Operation(summary = "搜索帖子通过用户id", description = "参数为空时查找登录用户的帖子")
    @GetMapping("/getPosting/userId")
    public Result getPostingByuser(@RequestParam(required = false) Integer userId) {
        if (userId == null) {
            userId = StpUtil.getLoginIdAsInt();
        }
        return Result.success(postingService.getPostingByuser(userId));
    }

    @Operation(summary = "搜索帖子通过帖子id", description = "任务相关接口")
    @GetMapping("/getPosting/posting/{id}")
    public Result getPostingByposting(@PathVariable Integer id) {
        return Result.success(postingService.getPostingById(id));
    }

}
