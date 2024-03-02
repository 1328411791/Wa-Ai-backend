package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.generator.Task;

/**
 * @author lihan
 * @description 针对表【sd_task】的数据库操作Service
 * @createDate 2024-02-23 02:09:45
 */
public interface TaskService extends IService<Task> {

    String setCreateStatus(Integer userId, Txt2ImageOptions txt2ImageOptions);

    public String setCreateStatus(Integer userId
            , ExtraImageOptions extraImageOptions);

    void setStartDrawStatus(String taskId);

    void setFinishDrawStatus(String taskId, String imageId, String imageParams);

    String startDrawRequest(int userId, Txt2ImageOptions options);

    String startDrawRequest(int userId, Txt2ImageOptions txt2ImageOptions, ExtraImageOptions extraImageOptions);

    Result getTaskByUser(int userID, Integer page, Integer pageSize);

    Result deleteTask(int userId, String taskId);
}
