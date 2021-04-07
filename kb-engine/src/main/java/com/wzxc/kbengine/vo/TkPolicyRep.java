package com.wzxc.kbengine.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 【请填写功能名称】对象 tk_policy_rep
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Data
public class TkPolicyRep
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系统主键 */
    @ApiModelProperty(value = "系统主键")
    private Long id;

    /** 任务表主键 */
    @ApiModelProperty(value = "任务表主键")
    @NotNull(message = "任务主键必填")
    private Long tkBaseInfoId;

    /** 政策表主键 */
    @ApiModelProperty(value = "政策表主键")
    @NotNull(message = "政策表主键必填")
    private Long policyRepId;

    /** 制定单位名称 */
    @ApiModelProperty(value = "制定单位名称")
    private String unitName;

    /** 制定单位编码 */
    @ApiModelProperty(value = "制定单位编码")
    private String unitCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人名称")
    private String creator;

    @ApiModelProperty(value = "修改人名称")
    private String updator;

    @ApiModelProperty(value = "备注")
    private String remark;

}
