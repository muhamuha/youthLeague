package com.wzxc.kbengine.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 【请填写功能名称】对象 StatisticRptRep
 * 
 * @author mengs
 * @date 2021-04-13
 */
@Data
public class StatisticRptRep {
    private static final long serialVersionUID = 1L;

    /** 系统主键 */
    @ApiModelProperty(value = "系统主键")
    private Long id;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    /** 牵头单位名称 */
    @ApiModelProperty(value = "牵头单位名称")
    private String orgName;

    /** 任务路径 */
    @ApiModelProperty(value = "任务路径")
    private String taskPathName;

    /** 任务层级 */
    @ApiModelProperty(value = "任务层级")
    private String taskLevel;

    /** 任务指标数 */
    @ApiModelProperty(value = "任务指标数")
    private Integer quotaCount;

    /** 改革清单数 */
    @ApiModelProperty(value = "改革清单数")
    private Integer reformCount;

    /** 工作计划数 */
    @ApiModelProperty(value = "工作计划数")
    private Integer planCount;

    /** 政策体系数 */
    @ApiModelProperty(value = "政策体系数")
    private Integer policyCount;

    /** 业务维护员 */
    @ApiModelProperty(value = "业务维护员")
    private String fullName;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /** 业务维护员编号 */
    @ApiModelProperty(value = "业务维护员编号")
    private String taskMaintainerId;

    /** 任务路径 */
    @ApiModelProperty(value = "任务路径")
    private String taskIdPath;

    /** 牵头单位编码 */
    @ApiModelProperty(value = "牵头单位编码")
    private String orgCode;

    /** 领域 */
    @ApiModelProperty(value = "领域")
    private String taskSystem;

    /** 任务来源 */
    @ApiModelProperty(value = "任务来源")
    private String taskSource;
}
