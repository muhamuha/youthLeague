package com.wzxc.zzdpush.vo;

import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 hhl_zzd_push_template
 * 
 * @author muhamuha
 * @date 2021-02-02
 */
@Data
public class HhlZzdPushTemplate {

    /** 主键 */
    private Integer id;

    /** 模板名称 */
    private String templateName;

    /** 状态 */
    private Integer status;

    /** 父id */
    private Integer parentId;

    /** 内容 */
    private String content;

    private String contentTemplate;

    /** 创建日期 */
    private Date createTime;

    /** 排序 */
    private Integer sort;

    /** 是否是指标 */
    private Integer isKpi;
}
