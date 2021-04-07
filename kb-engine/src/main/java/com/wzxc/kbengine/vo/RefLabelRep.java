package com.wzxc.kbengine.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 【请填写功能名称】对象 policy_label_rep
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Data
public class RefLabelRep
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系统主键 */
    @ApiModelProperty(value = "系统主键")
    private Long id;

    /** 标签表主键 */
    @ApiModelProperty(value = "标签表主键")
    @NotNull(message = "缺少标签主键")
    private Long labelBaseInfoRepId;

    /** 映射主键 */
    @ApiModelProperty(value = "映射主键")
    @NotNull(message = "缺少映射主键")
    private Long refId;

    /** 映射类型 */
    @ApiModelProperty(value = "映射类型")
    private Long refType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "修改人")
    private String updator;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "标签名称")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String labelName;


}
