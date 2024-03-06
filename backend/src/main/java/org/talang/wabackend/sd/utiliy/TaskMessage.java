package org.talang.wabackend.sd.utiliy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TaskMessage {

    private String taskId;

    private TaskTypeEnum taskType;

    private Integer priority;

    private Long timestamp;

    public TaskMessage(String taskId, TaskTypeEnum taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.priority = 5;
        this.timestamp = System.currentTimeMillis();
    }

    public TaskMessage(String taskId,Integer priority, TaskTypeEnum taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.priority = priority;
        this.timestamp = System.currentTimeMillis();
    }
}
