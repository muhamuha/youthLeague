package com.wzxc.common.exception;

public class InsertBatchException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public InsertBatchException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public InsertBatchException(String message)
    {
        super(message);
    }

    public InsertBatchException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
