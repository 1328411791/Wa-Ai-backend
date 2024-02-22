package org.talang.wabackend.sd;

import lombok.Getter;
import org.talang.sdk.SdWebui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiSdWebUiConnect {

    @Getter
    List<SdWebui> sdWebuiList = new ArrayList<>();

    BlockingQueue<SdWebui> availableSdWebui = new LinkedBlockingQueue<>();

    public MultiSdWebUiConnect() {

    }

    public MultiSdWebUiConnect(List<SdWebui> sdWebuiList) {
        this.sdWebuiList = sdWebuiList;
        availableSdWebui.addAll(sdWebuiList);
    }

    public void addSdWebui(SdWebui sdWebui) {
        sdWebuiList.add(sdWebui);
        availableSdWebui.add(sdWebui);
    }

    public SdWebui getAvailableSdWebui() throws InterruptedException {
        return availableSdWebui.take();
    }

    public void returnSdWebui(SdWebui sdWebui) {
        availableSdWebui.add(sdWebui);
    }
}

