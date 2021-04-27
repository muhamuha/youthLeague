package com.wzxc.kbengine.vo;

import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 dx_quota_current
 * 
 * @author huanghl
 * @date 2021-04-27
 */
@Data
public class DxQuotaCurrent
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 指标名称 */
    private String quotaName;

    /** 指标定义 */
    private String quotaDesc;

    /** 指标单位 */
    private String valueUnit;

    /** 区县编码 */
    private Long nodeId;

    /** 指标值 */
    private String quotaValue;

    /** 同比 */
    private String quotaYoy;

    /** 环比 */
    private String quotaMom;

    /** 备注 */
    private String remarks;

    private Date createTime;

    private Date updateTime;

    /** 创建人 */
    private String creator;

    /** 修改人 */
    private String updator;

    /** 是否有效
0：有效
1：无效 */
    private Integer isValid;

}
