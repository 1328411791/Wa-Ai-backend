package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.post.PostAddDto;
import org.talang.wabackend.model.dto.post.PostGetByMIDDto;
import org.talang.wabackend.model.dto.post.PostGetByUIDDto;
import org.talang.wabackend.service.PostFavoriteSevice;
import org.talang.wabackend.service.PostService;
import org.talang.wabackend.util.PostLikeComponent;

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
    private PostLikeComponent postLikeComponent;

    @Operation(summary = "创建帖子")
    @PostMapping("/create")
    public Result createPost(@RequestBody PostAddDto postAddDto) {
        return Result.success(postService.createPost(postAddDto));
    }

    @Operation(summary = "删除帖子")
    @PostMapping("/delete")
    public Result deletePost(@RequestBody List<Integer> postingIdList) {
        postService.deletePost(postingIdList);
        return Result.success("删除成功");
    }

    @Operation(summary = "搜索创建的帖子", description = "userId参数为空时查找登录用户的帖子")
    @PostMapping("/getPostLite/create")
    public Result getPostLiteByCreate(@RequestBody PostGetByUIDDto postGetByUIDDto) {
        return Result.success(postService.getPostLiteByCreate(postGetByUIDDto));
    }

    @Operation(summary = "搜索收藏的的帖子", description = "userId参数为空时查找登录用户的帖子")
    @PostMapping("/getPostLite/favours")
    public Result getPostLiteByFavours(@RequestBody PostGetByUIDDto postGetByUIDDto) {
        return Result.success(postService.getPostLiteByFavours(postGetByUIDDto));
    }

    @Operation(summary = "搜索模型的的帖子")
    @PostMapping("/getPostLite/model")
    public Result getPostLiteByMID(@RequestBody PostGetByMIDDto postGetByMIDDto) {
        return Result.success(postService.getPostLiteByModel(postGetByMIDDto));
    }

    @Operation(summary = "通过帖子id搜索帖子")
    @GetMapping("/getPost/{postId}")
    public Result getPostingFull(@PathVariable Integer postId) {
        return Result.success(postService.getPostFull(postId));
    }

    @Operation(summary = "收藏帖子")
    @GetMapping("/Favorite/{postId}")
    public Result PostFavorite(@PathVariable Integer postId) {
        return Result.success(postFavoriteSevice.PostFavorite(postId));
    }

    @Operation(summary = "点赞帖子")
    @GetMapping("/Likes/{postId}")
    public Result PostLikes(@PathVariable Integer postId) {
        return Result.success(postLikeComponent.like(postId));
    }
}
