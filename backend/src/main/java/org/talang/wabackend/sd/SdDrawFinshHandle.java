package org.talang.wabackend.sd;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.talang.wabackend.model.generator.Task;
import org.talang.wabackend.websocket.model.SdDrawFinishResult;
import org.talang.wabackend.service.TaskService;
import org.talang.wabackend.websocket.SdTaskWebSocket;

@Slf4j
@Component
public class SdDrawFinshHandle {

    @Resource
    private SdTaskWebSocket sdTaskWebSocket;

    @Resource
    private TaskService taskService;

    public void drawFinishHandle(String taskId) {
        log.info("[drawFinishHandle] 画图完成处理");
        Task task = taskService.getById(taskId);
        Integer userId = task.getUserId();

        SdDrawFinishResult ret = new SdDrawFinishResult();
        ret.setUserId(userId);
        ret.setTaskId(taskId);
        String message = JSONUtil.toJsonStr(ret);
        sdTaskWebSocket.sendOneMessage(userId, message);
    }
}
