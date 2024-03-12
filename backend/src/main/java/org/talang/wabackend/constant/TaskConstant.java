package org.talang.wabackend.constant;

public class TaskConstant {
    private TaskConstant() {}

    /**
     * 任务状态：创建
     */
    public static int TASK_STATUS_CREATED = 0;

    /**
     * 任务状态：生成中
     */
    public static int TASK_STATUS_RUNNING = 1;

    /**
     * 任务状态：完成
     */
    public static int TASK_STATUS_FINISHED = 2;

    /**
     * 任务状态：失败
     */
    public static int TASK_STATUS_FAILED = 3;

    /**
     * 任务状态：无法获取
     */
    public static int TASK_STATUS_NOTFOUND = 9;



}
