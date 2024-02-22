package org.talang.wabackend.sd;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
@Component
public class SdThreadPool {


    ThreadPoolExecutor executor = null;
    private BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();

    public void init(List<SdWebui> webuiList) {
        log.info("初始化线程池");
        int size = webuiList.size();
        executor = new ThreadPoolExecutor(size,
                size, 200, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public void addTask(String task) {
        taskQueue.add(task);
        executor.execute(new SdTaskThread());
    }

    public String getTask() throws InterruptedException {
        return taskQueue.take();
    }


}
