package com.wzxc.kbengine.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】对象 group_base_info_rep
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Data
public class GroupBaseInfoRep {
    private static final long serialVersionUID = 1L;

    /** 系统主键 */
    @ApiModelProperty(value = "系统主键")
    private Long id;

    /** 分组编码 */
    @ApiModelProperty(value = "分组编码")
    private String groupId;

    /** 分组名称 */
    @ApiModelProperty(value = "分组名称")
    private String groupName;

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

    /** 分组类型 */
    @ApiModelProperty(value = "分组类型")
    private Long groupType;

    /** 是否是父节点 */
    @ApiModelProperty(value = "是否是父节点")
    private Integer isParent;

    /** 父id */
    @ApiModelProperty(value = "父id")
    private Long parentId;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 备注 */
    @ApiModelProperty(value = "分组下面的标签列表")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LabelBaseInfoRep> labelBaseInfoRepList;

}
