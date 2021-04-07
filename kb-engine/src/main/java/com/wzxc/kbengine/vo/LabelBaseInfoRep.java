package com.wzxc.kbengine.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】对象 label_base_info_rep
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Data
public class LabelBaseInfoRep {
    private static final long serialVersionUID = 1L;

    /** 系统主键 */
    @ApiModelProperty(value = "系统主键")
    private Long id;

    /** 标签主键 */
    @ApiModelProperty(value = "标签主键")
    private String labelId;

    /** 标签名称 */
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createTimeBegin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createTimeEnd;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /** 创建人名称 */
    @ApiModelProperty(value = "创建人名称")
    private String creator;

    /** 更新人名称 */
    @ApiModelProperty(value = "更新人名称")
    private String updator;

    /** 操作类型代码 */
    @ApiModelProperty(value = "操作类型代码")
    private String cdOperation;

    /** 标签所属类型 */
    @ApiModelProperty(value = "标签所属类型")
    private Integer labelType;

    /** 标签状态 */
    @ApiModelProperty(value = "标签状态")
    private Integer labelStatus;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 标签所属分组（新增标签时使用） */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long[] groupBaseInfoRepIdList;

    /** 标签所属分组（查询标签时使用） */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long groupBaseInfoRepId;

    /** 标签所属类 */
    @ApiModelProperty(value = "标签所属类")
    private String labelClass;

    /** 标签所属分组详情 */
    private List<GroupBaseInfoRep> groupBaseInfoRepList;

}
