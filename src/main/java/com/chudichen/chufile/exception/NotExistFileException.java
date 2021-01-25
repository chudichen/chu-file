package com.chudichen.chufile.exception;

/**
 * @author chudichen
 * @date 2021-01-25
 */
public class NotExistFileException extends RuntimeException {

    public NotExistFileException() {
        super();
    }

    public NotExistFileException(String message) {
        super(message);
    }

    public NotExistFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistFileException(Throwable cause) {
        super(cause);
    }

    protected NotExistFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
