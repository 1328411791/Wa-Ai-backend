package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.post.PostDto;
import org.talang.wabackend.service.PostFavoriteSevice;
import org.talang.wabackend.service.PostLikesService;
import org.talang.wabackend.service.PostService;

import java.util.List;

@Tag(name = "帖子", description = "帖子相关接口")
@RestController()
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostFavoriteSevice postFavoriteSevice;

    @Autowired
    private PostLikesService postLikesService;

    @Operation(summary = "创建帖子", description = "任务相关接口")
    @PostMapping("/create")
    public Result createPost(@RequestBody PostDto postDto) {
        return Result.success(postService.createPost(postDto));
    }

    @Operation(summary = "删除帖子", description = "任务相关接口")
    @PostMapping("/delete")
    public Result deletePost(@RequestBody List<Integer> postingIdList) {
        postService.deletePost(postingIdList);
        return Result.success("删除成功");
    }

    @Operation(summary = "通过用户id搜索帖子", description = "参数为空时查找登录用户的帖子")
    @GetMapping("/getPost/userId")
    public Result getPostLite(@RequestParam(required = false) Integer userId) {
        if (userId == null) {
            userId = StpUtil.getLoginIdAsInt();
        }
        return Result.success(postService.getPostLite(userId));
    }

    @Operation(summary = "通过帖子id搜索帖子", description = "任务相关接口")
    @GetMapping("/getPost/{postId}")
    public Result getPostingFull(@PathVariable Integer postId) {
        return Result.success(postService.getPostFull(postId));
    }

    @Operation(summary = "收藏帖子", description = "任务相关接口")
    @GetMapping("/Favorite/{postId}")
    public Result PostFavorite(@PathVariable Integer postId) {
        return Result.success(postFavoriteSevice.PostFavorite(postId));
    }

    @Operation(summary = "点赞帖子", description = "任务相关接口")
    @GetMapping("/Likes/{postId}")
    public Result PostLikes(@PathVariable Integer postId) {
        return Result.success(postLikesService.PostLikes(postId));
    }
}
