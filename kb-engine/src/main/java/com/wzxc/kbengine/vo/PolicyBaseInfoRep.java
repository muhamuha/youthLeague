package com.wzxc.kbengine.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wzxc.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】对象 policy_base_info_rep
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Data
public class PolicyBaseInfoRep extends BaseEntity
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系统主键 */
    @ApiModelProperty(value = "系统主键")
    private Long id;

    /** 政策唯一编号，业务主键 */
    @ApiModelProperty(value = "政策唯一编号，业务主键")
    private String policyId;

    /** 发布层级描述 */
    @ApiModelProperty(value = "发布层级描述 1：国级 2：省级 3：市级 4：区县")
    private Integer policyType;

    /** 文件对象数组 */
    @ApiModelProperty(value = "文件对象数组")
    private List<Map<String, String>> fileObjectList;

    @JsonIgnore
    private String fileObjectStr;

    /** 发布时间 */
    @ApiModelProperty(value = "发布时间")
    private Date filePublishTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date publishTimeBegin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date publishTimeEnd;

    /** 实施时间 */
    @ApiModelProperty(value = "实施时间")
    private Date fileImpTime;

    /** 解读文件名称 */
    @ApiModelProperty(value = "解读文件名称")
    private String unscrambleName;

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

    /** 创建人名称 */
    @ApiModelProperty(value = "创建人名称")
    private String creator;

    /** 更新人名称 */
    @ApiModelProperty(value = "更新人名称")
    private String updator;

    /** 操作类型代码 */
    @ApiModelProperty(value = "操作类型代码")
    private String cdOperation;

    /** 数源 */
    @ApiModelProperty(value = "数源")
    private String resource;

    /** 政策名称 */
    @ApiModelProperty(value = "政策名称")
    private String policyName;

    /** 政策文号 */
    @ApiModelProperty(value = "政策文号")
    private String policyNumber;

    /** 政策所属领域 */
    @ApiModelProperty(value = "政策所属领域")
    private String policySystem;

    /** 政策状态 */
    @ApiModelProperty(value = "政策状态")
    private Long policyStatus;

    /** 拟稿人名称 */
    @ApiModelProperty(value = "拟稿人名称")
    private String drafter;

    /** 解读文件url */
    @ApiModelProperty(value = "解读文件url")
    private String unscrambleUrl;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 标签 */
    @ApiModelProperty(value = "标签数组（新增政策的时候用）")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long[] labelList;

    /** 是否有效 */
    @ApiModelProperty(value = "是否有效")
    private Integer isValid;

    /** 政策的标签列表信息 */
    @ApiModelProperty(value = "政策的标签列表信息")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LabelBaseInfoRep> labelBaseInfoRepList;

    /** 政策的下载外键 */
    @ApiModelProperty(value = "政策的下载外键")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fileOutId;

    /** 关联的任务id */
    @ApiModelProperty(value = "关联的任务id（主键）")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long tkBaseInfoId;

    /** 关联的任务id */
    @ApiModelProperty(value = "关联的任务id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String taskId;

    /** 是否与任务相关 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer tkFlag;

    /** 是否只显示任务相关 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer tkOnly;

    /** 正文内容 */
    @ApiModelProperty(value = "正文内容")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String policyContent;

    /** 正文内容无标签 */
    @ApiModelProperty(value = "正文内容（无html标签）")
    private String policyContentSi;

}
