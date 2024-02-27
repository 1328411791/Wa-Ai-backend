package org.talang.wabackend.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.vo.task.ShowTaskVo;
import org.talang.wabackend.service.TaskService;

import java.util.List;

@Tag(name = "Task", description = "任务相关接口")
@RestController()
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "获取个人任务", description = "任务相关接口")
    @GetMapping("/getTaskByUser")
    public Result getTaskByUser(@RequestParam Integer page,
                                @RequestParam Integer pageSize) {
        int userID = StpUtil.getLoginIdAsInt();
        List<ShowTaskVo> tasks = taskService.getTaskByUser(userID, page, pageSize);

        return Result.success(tasks);
    }
}
