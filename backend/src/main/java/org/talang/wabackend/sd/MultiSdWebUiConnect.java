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

    public static List<SdWebui> sdWebuiList = new ArrayList<>();

    private static BlockingQueue<SdWebui> availableSdWebui = new LinkedBlockingQueue<>();

    public void addSdWebui(SdWebui sdWebui) {
        sdWebuiList.add(sdWebui);
        availableSdWebui.add(sdWebui);
    }

    public SdWebui getAvailableSdWebui() {
        SdWebui take = null;
        try {
            log.info("waiting for get Available sdwebui...");
            log.info("show availableSdWebui size:{}", availableSdWebui.size());
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

    public List<SdWebui> getWebUIList() {
        return sdWebuiList;
    }
}

