package com.wzxc.common.utils;

import com.wzxc.common.utils.uuid.IdUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求id相关的工具类
 */
public class RequestIdUtils {

    private static ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<>();
    private static final String REQUEST_ID_KEY = "requestId";

    public static String createRequestId(HttpServletRequest request) {
        String requestId = null;
        String parameterRequestId = request.getParameter(REQUEST_ID_KEY);
        if (parameterRequestId == null || StringUtils.isEmpty(parameterRequestId)) {
            requestId = IdUtils.simpleUUID();
        } else {
            requestId = parameterRequestId;
        }
        setRequestId(requestId);
        return requestId;
    }

    public static String getRequestId(){
        return requestIdThreadLocal.get();
    }

    private static void setRequestId(String requestId){
        requestIdThreadLocal.set(requestId);
    }
}
