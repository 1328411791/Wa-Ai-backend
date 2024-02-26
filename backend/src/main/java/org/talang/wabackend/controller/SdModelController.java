package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.generator.Model;
import org.talang.wabackend.model.vo.model.HomePageModelVo;
import org.talang.wabackend.model.vo.model.SelectSdModelVo;
import org.talang.wabackend.service.ModelService;

import java.util.List;

@Tag(name = "模型", description = "The model API")
@RestController
@RequestMapping("/model")
public class SdModelController {

    @Resource
    private ModelService modelService;

    @Operation(summary = "类别查询（弃用）", description = "根据模型类别查询")
    @GetMapping("/getByCategory")
    public Result getByCategory(@RequestParam String category
            , @RequestParam Integer page, @RequestParam Integer pageSize) {

        List<HomePageModelVo> models = modelService.getByCategory(category, page, pageSize);
        return Result.success(models);
    }

    @Operation(summary = "获取模型列表", description = "获取模型列表")
    @GetMapping("/getSdModelsList")
    public Result getSdModel4Text(@RequestParam(defaultValue = "") String searchQuery,
                                  @RequestParam String type,
                                  @RequestParam(defaultValue = "0") Long startTimestamp,
                                  @RequestParam(defaultValue = "0") Long endTimestamp,
                                  @RequestParam Integer page,
                                  @RequestParam Integer pageSize) {

        SelectSdModelVo sdModel = modelService.selectModelOrder(
                searchQuery,
                type,
                startTimestamp,
                endTimestamp,
                page,
                pageSize);

        return Result.success(sdModel);
    }

    @Operation(summary = "获取模型详情", description = "获取模型详情")
    @GetMapping("/getSdModelById")
    public Result getSdModelById(@RequestParam Integer id) {
        Model model = modelService.getById(id);
        return Result.success(model);
    }

}
