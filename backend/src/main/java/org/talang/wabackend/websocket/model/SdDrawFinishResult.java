package org.talang.wabackend.websocket.model;

import lombok.Data;

@Data
public class SdDrawFinishResult {

    private Integer userId;

    private String taskId;

    private Long timestamp;

    public SdDrawFinishResult() {
        this.timestamp = System.currentTimeMillis();
    }
}
