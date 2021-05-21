package com.wzxc.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 * 
 * @author ruoyi
 */
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String keyword;

    /** 创建者 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String createBy;

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }
}
