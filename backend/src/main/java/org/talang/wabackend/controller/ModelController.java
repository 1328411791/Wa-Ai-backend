package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.vo.model.HomePageModelVo;
import org.talang.wabackend.service.ModelService;

import java.util.List;

@Tag(name = "模型", description = "The model API")
@RestController
@RequestMapping("/model")
public class ModelController {

    @Resource
    private ModelService modelService;

    @Operation(description = "根据模型类别查询")
    @GetMapping("/getByCategory")
    public Result getByCategory(@RequestParam String category
            , @RequestParam Integer page, @RequestParam Integer pageSize) {

        List<HomePageModelVo> models = modelService.getByCategory(category, page, pageSize);
        return Result.success(models);
    }

}
