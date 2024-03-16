package org.talang.sdk.exceptions;

public class SdBusinessException extends RuntimeException {
    public SdBusinessException(Exception e) {
        super(e);
    }

    public SdBusinessException(String e) {
        super(e);
    }
}
