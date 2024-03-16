package org.talang.wabackend.exception;

public enum BusinessErrorCode {

    NOT_FOUND("未找到"),
    WEBSOKET_FAILED_CONNECT("websoket连接失败"),
    IMAGE_NOT_FOUND("不存在Sd图片"),
    MODEL_NOT_FOUND("不存在Sd模型"),
    UnknownErr("系统繁忙");


    private final String message;

    BusinessErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
