package org.talang.wabackend.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.wabackend.mapper.TaskMapper;
import org.talang.wabackend.model.generator.Task;
import org.talang.wabackend.sd.SdThreadPool;
import org.talang.wabackend.service.TaskService;

import java.util.UUID;

/**
 * @author lihan
 * @description 针对表【sd_task】的数据库操作Service实现
 * @createDate 2024-02-23 02:09:45
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
        implements TaskService {

    @Resource
    private SdThreadPool sdThreadPool;


    @Override
    public String setCreateStatus(Integer userId
            , Txt2ImageOptions txt2ImageOptions) {
        Task task = new Task();
        String id = UUID.randomUUID().toString();
        task.setId(id);
        task.setStatus(0);
        task.setTxt2image_options(JSONUtil.toJsonStr(txt2ImageOptions));
        task.setPriority(0);
        task.setAuthor_id(userId);
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
        task.setStatus(0);
        task.setTxt2image_options(JSONUtil.toJsonStr(txt2ImageOptions));
        task.setExtraimage_options(JSONUtil.toJsonStr(extraImageOptions));
        task.setPriority(0);
        task.setAuthor_id(userId);
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
        task.setImage_id(imageId);
        updateById(task);
    }

    @Override
    public String startDrawRequest(int userId, Txt2ImageOptions options) {
        String taskId = this.setCreateStatus(userId, options);
        sdThreadPool.addTask(taskId);
        return taskId;
    }

    @Override
    public String startDrawRequest(int userId, Txt2ImageOptions txt2ImageOptions, ExtraImageOptions extraImageOptions) {
        return null;
    }
}




