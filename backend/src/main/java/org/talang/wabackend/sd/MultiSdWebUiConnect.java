package org.talang.wabackend.sd;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.talang.sdk.SdWebui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Data
public class MultiSdWebUiConnect {

    List<SdWebui> sdWebuiList = new ArrayList<>();

    BlockingQueue<SdWebui> availableSdWebui = new LinkedBlockingQueue<>();

    public void addSdWebui(SdWebui sdWebui) {
        sdWebuiList.add(sdWebui);
        availableSdWebui.add(sdWebui);
    }

    public SdWebui getAvailableSdWebui() {
        SdWebui take = null;
        try {
            log.info("waiting for get Available sdwebui...");
            take = availableSdWebui.take();
            log.info("get Available sdwebui success");
        } catch (Exception e) {
            log.error("获取sdwebui失败", e);
        }
        return take;
    }

    public void returnSdWebui(SdWebui sdWebui) {
        availableSdWebui.add(sdWebui);
    }
}

