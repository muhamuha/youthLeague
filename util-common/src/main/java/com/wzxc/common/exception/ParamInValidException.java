package com.wzxc.common.exception;

public class ParamInValidException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public ParamInValidException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public ParamInValidException(String message)
    {
        super(message);
    }

    public ParamInValidException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
