package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.generator.ImageTag;
import org.talang.wabackend.service.ImageTagService;

import java.util.List;

@Tag(description = "sdk", name = "sd标签管理")
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private ImageTagService tagService;

    @Operation(description = "获取标签")
    @GetMapping("/getTag")
    public Result getTag(@RequestParam String search,
                         @RequestParam Integer page,
                         @RequestParam Integer pageSize) {
//        List<ImageTag> sdTag = tagService.getTagOrderByAsce(page, pageSize);
        List<ImageTag> sdTag = tagService.getTagOrderBySearch(search, page, pageSize);

        return Result.success(sdTag);
    }

}