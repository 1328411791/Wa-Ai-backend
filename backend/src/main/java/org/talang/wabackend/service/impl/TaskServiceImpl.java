package org.talang.wabackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.mapper.TaskMapper;
import org.talang.wabackend.model.generator.Task;
import org.talang.wabackend.model.vo.task.ShowTaskVo;
import org.talang.wabackend.service.StaticImageService;
import org.talang.wabackend.service.TaskService;
import org.talang.wabackend.service.UserService;

import java.util.List;
import java.util.UUID;

/**
 * @author lihan
 * @description 针对表【sd_task】的数据库操作Service实现
 * @createDate 2024-02-23 02:09:45
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
        implements TaskService {

    @Autowired
    private StaticImageService staticImageService;

    @Autowired
    private UserService userService;


    @Override
    public String setCreateStatus(Integer userId
            , Txt2ImageOptions txt2ImageOptions) {
        Task task = new Task();
        String id = UUID.randomUUID().toString();
        task.setId(id);
        task.setStatus(0);
        task.setUserId(userId);
        task.setTxt2imageOptions(JSONUtil.toJsonStr(txt2ImageOptions));
        task.setPriority(0);
        task.setType("txt2image");
        this.save(task);
        return id;
    }

    @Override
    public String setCreateStatus(Integer userId, ExtraImageOptions extraImageOptions) {
        Task task = new Task();
        String id = UUID.randomUUID().toString();
        task.setId(id);
        task.setUserId(userId);
        task.setStatus(0);
        task.setExtraimageOptions(JSONUtil.toJsonStr(extraImageOptions));
        task.setPriority(0);
        task.setType("extraimage");
        this.save(task);

        return id;
    }

    @Override
    public void setStartDrawStatus(String taskId) {
        Task task = getById(taskId);
        task.setStatus(1);
        updateById(task);
    }

    @Override
    public void setFinishDrawStatus(String taskId, String imageId) {
        Task task = getById(taskId);
        task.setStatus(2);
        task.setImageId(imageId);
        updateById(task);
    }

    @Override
    public String startDrawRequest(int userId, Txt2ImageOptions options) {
        String taskId = this.setCreateStatus(userId, options);
        return taskId;
    }

    @Override
    public String startDrawRequest(int userId, Txt2ImageOptions txt2ImageOptions, ExtraImageOptions extraImageOptions) {
        return null;
    }

    @Override
    public Result getTaskByUser(int userID, Integer page, Integer pageSize) {
        Page<Task> taskPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Task> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Task::getUserId, userID);
        taskPage.addOrder(OrderItem.desc("update_time"));
        taskPage = page(taskPage, lambdaQueryWrapper);

        List<Task> tasks = taskPage.getRecords();

        List<ShowTaskVo> taskVos = tasks.stream().map(task -> {
            ShowTaskVo showTaskVo = BeanUtil.toBean(task, ShowTaskVo.class);
            String nickName = userService.getUserNickNameById(task.getUserId());
            showTaskVo.setNickName(nickName);
            return showTaskVo;
        }).toList();

        return Result.success(taskVos, taskPage.getTotal());
    }

    @Override
    public Result deleteTask(int userId, String taskId) {
        Task task = getById(taskId);
        if (task.getUserId().equals(userId)) {
            removeById(taskId);
            return Result.success("删除成功");
        }
        return Result.fail("无权限删除任务");
    }

    @Override
    public void setFailStatus(String taskId) {
        Task task = getById(taskId);
        task.setStatus(3);
        updateById(task);
    }
}




