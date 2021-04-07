package com.wzxc.kbengine.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 【请填写功能名称】对象 label_group_rep
 * 
 * @author huanghl
 * @date 2021-03-12
 */
@Data
public class LabelGroupRep
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系统主键 */
    @ApiModelProperty(value = "系统主键")
    private Long id;

    /** 标签所属类 */
    @ApiModelProperty(value = "标签所属类")
    @NotNull(message = "缺少标签所属类")
    private String labelBaseInfoRepClass;

    /** 分组表主键 */
    @ApiModelProperty(value = "分组表主键")
    @NotNull(message = "缺少分组主键")
    private Long groupBaseInfoRepId;

    /** 创建人名称 */
    @ApiModelProperty(value = "创建人名称")
    @NotNull(message = "缺少创建人名称")
    private String creator;

    /** 修改人名称 */
    @ApiModelProperty(value = "修改人名称")
    @NotNull(message = "缺少修改人名称")
    private String updator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分组名称")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String groupName;

    @ApiModelProperty(value = "标签名称")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String labelName;

    @ApiModelProperty(value = "标签id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer labelBaseInfoRepId;

}
