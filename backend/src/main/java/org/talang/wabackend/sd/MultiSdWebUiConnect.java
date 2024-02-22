package org.talang.wabackend.sd;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.talang.sdk.SdWebui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Data
@Component
public class MultiSdWebUiConnect {

    List<SdWebui> sdWebuiList = new ArrayList<>();

    BlockingQueue<SdWebui> availableSdWebui = new LinkedBlockingQueue<>();

    public void addSdWebui(SdWebui sdWebui) {
        sdWebuiList.add(sdWebui);
        availableSdWebui.add(sdWebui);
    }

    public SdWebui getAvailableSdWebui() {
        try {
            return availableSdWebui.take();
        } catch (Exception e) {
            log.error("获取sdwebui失败", e);
        }
        return null;
    }

    public void returnSdWebui(SdWebui sdWebui) {
        availableSdWebui.add(sdWebui);
    }
}

