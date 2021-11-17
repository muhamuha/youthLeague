package com.wzxc.common.core.domain;

import com.wzxc.common.utils.RequestIdUtils;
import com.wzxc.common.utils.StringUtils;

import java.util.HashMap;

public class BusiResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /** 是否成功 */
    public static final String IS_SUCCESS = "success";

    /** 状态码 */
    public static final String CODE_TAG = "errorCode";

    /** 返回内容 */
    public static final String MSG_TAG = "errorMsg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /** 请求编码 */
    public static final String REQUEST_ID = "request_id";

    /**
     * 状态类型
     */
    public enum Type
    {
        /** 成功 */
        SUCCESS(13000),
        /** 警告 */
        WARN(13301),
        /** 未授权 */
        UNAUTH(13401),
        /** 错误 */
        ERROR(13500);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    /**
     * 初始化一个新创建的 KbengineResult 对象，使其表示一个空消息。
     */
    public BusiResult() { }

    /**
     * 初始化一个新创建的 KbengineResult 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     */
    public BusiResult(boolean isSuccess, BusiResult.Type type, String msg)
    {
        super.put(IS_SUCCESS, isSuccess);
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        super.put(REQUEST_ID, RequestIdUtils.getRequestId());
    }

    /**
     * 初始化一个新创建的 KbengineResult 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     * @param data 数据对象
     */
    public BusiResult(boolean isSuccess, BusiResult.Type type, String msg, Object data)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public BusiResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static BusiResult success()
    {
        return BusiResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static BusiResult success(Object data)
    {
        return BusiResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static BusiResult success(String msg)
    {
        return BusiResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static BusiResult success(String msg, Object data)
    {
        return new BusiResult(true, BusiResult.Type.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static BusiResult warn(String msg)
    {
        return BusiResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static BusiResult warn(String msg, Object data)
    {
        return new BusiResult(false, BusiResult.Type.WARN, msg, data);
    }

    /**
     * 返回未授权消息
     *
     * @param msg 返回内容
     * @return 授权消息
     */
    public static BusiResult unAuth()
    {
        return BusiResult.unAuth("未登录，请在网站上登录");
    }

    /**
     * 返回未授权消息
     *
     * @param msg 返回内容
     * @return 授权消息
     */
    public static BusiResult unAuth(String msg)
    {
        return new BusiResult(false, BusiResult.Type.UNAUTH, msg);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static BusiResult error()
    {
        return BusiResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static BusiResult error(String msg)
    {
        return BusiResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static BusiResult error(String msg, Object data)
    {
        return new BusiResult(false, BusiResult.Type.ERROR, msg, data);
    }
}
