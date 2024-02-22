package org.talang.wabackend.sd;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;

@Component
public class SdTaskThread implements Runnable {

    @Resource
    private MultiSdWebUiConnect multiSdWebUiConnect;

    @Resource
    private SdThreadPool sdThreadPool;

    @Resource
    private DrawImageComponent drawImageComponent;

    @Override
    public void run() {
        try {
            SdWebui freeConnect = multiSdWebUiConnect.getAvailableSdWebui();
            String task = sdThreadPool.getTask();

            drawImageComponent.threadDraw(freeConnect, task);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
