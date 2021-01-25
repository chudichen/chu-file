package com.chudichen.chufile.exception;

/**
 * 存储策略未初始化异常
 *
 * @author chudichen
 * @date 2021-01-25
 */
public class StorageStrategyUninitializedException extends RuntimeException {

    private static final long serialVersionUID = 9017851703135745918L;

    public StorageStrategyUninitializedException() {
    }

    public StorageStrategyUninitializedException(String message) {
        super(message);
    }

    public StorageStrategyUninitializedException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageStrategyUninitializedException(Throwable cause) {
        super(cause);
    }

    public StorageStrategyUninitializedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
