package org.talang.wabackend.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(BusinessErrorCode businessErrorCode) {
        super(businessErrorCode.getMessage());
    }

    public BusinessException(BusinessErrorCode businessErrorCode, String message) {
        super(businessErrorCode.getMessage() + message);
    }

}
