package org.talang.wabackend.exception;

public class BusinessException extends RuntimeException {

    private final ErrorName errorName;

    public BusinessException(ErrorName errorName) {
        super(errorName.getMessage());
        this.errorName = errorName;
    }

    public BusinessException(ErrorName errorName, String message) {
        super(errorName.getMessage() + message);
        this.errorName = errorName;
    }

}
