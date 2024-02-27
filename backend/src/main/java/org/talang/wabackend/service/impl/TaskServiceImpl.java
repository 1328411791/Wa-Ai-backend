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
import org.talang.wabackend.mapper.TaskMapper;
import org.talang.wabackend.model.generator.Task;
import org.talang.wabackend.model.vo.task.ShowTaskVo;
import org.talang.wabackend.service.StaticImageService;
import org.talang.wabackend.service.TaskService;

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
    public String setCreateStatus(Integer userId
            , Txt2ImageOptions txt2ImageOptions, ExtraImageOptions extraImageOptions) {
        Task task = new Task();
        String id = UUID.randomUUID().toString();
        task.setId(id);
        task.setUserId(userId);
        task.setStatus(0);
        task.setTxt2imageOptions(JSONUtil.toJsonStr(txt2ImageOptions));
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
    public void setFinishDrawStatus(String taskId, String imageId, String imageParams) {
        Task task = getById(taskId);
        task.setStatus(2);
        task.setImageId(imageId);
        task.setTxt2imageOptions(imageParams);
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
    public List<ShowTaskVo> getTaskByUser(int userID, Integer page, Integer pageSize) {
        Page<Task> taskPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Task> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Task::getUserId, userID);
        taskPage = page(taskPage, lambdaQueryWrapper);

        List<Task> tasks = taskPage.addOrder(new OrderItem().setColumn("update_time")).getRecords();

        List<ShowTaskVo> taskVos = tasks.stream().map(task -> {
            ShowTaskVo showTaskVo = BeanUtil.toBean(task, ShowTaskVo.class);
            String saticImagePath = staticImageService.getSaticImagePathById(task.getImageId());
            showTaskVo.setImageURL(saticImagePath);
            return showTaskVo;
        }).toList();

        return taskVos;
    }
}




