package com.wzxc.common.exception;

public class ParamInException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public ParamInException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public ParamInException(String message)
    {
        super(message);
    }

    public ParamInException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
