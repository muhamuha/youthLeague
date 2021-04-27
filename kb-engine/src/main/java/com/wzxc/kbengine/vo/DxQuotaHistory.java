package com.wzxc.kbengine.vo;

import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 dx_quota_history
 * 
 * @author huanghl
 * @date 2021-04-27
 */
@Data
public class DxQuotaHistory
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 指标名称 */
    private String quotaName;

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

    /** 创建人 */
    private String creator;

    /** 指标描述 */
    private String quotaDesc;

    /** $column.columnComment */
    private Long quotaId;

    private Date createTime;

}
