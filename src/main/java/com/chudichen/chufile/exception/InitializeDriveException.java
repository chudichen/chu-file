package com.chudichen.chufile.exception;

/**
 * 对象存储初始化异常
 *
 * @author chudichen
 * @date 2021-01-25
 */
public class InitializeDriveException extends RuntimeException {

    private static final long serialVersionUID = -7994403829188975641L;

    public InitializeDriveException() {
    }

    public InitializeDriveException(String message) {
        super(message);
    }

    public InitializeDriveException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializeDriveException(Throwable cause) {
        super(cause);
    }

    public InitializeDriveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
