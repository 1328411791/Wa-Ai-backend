package org.talang.wabackend.exception;

public class TaskFailException extends RuntimeException {

    public final String taskId;

    public TaskFailException(String taskId, String message) {
        super(message);
        this.taskId = taskId;
    }
}
