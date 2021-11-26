package com.wzxc.busi.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类对象 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-26
 */
@Data
@TableName("sys_region")
@ApiModel(value="SysRegion对象", description="")
public class SysRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "区域层级 1：省；2：市；3：区县；4：街道；5：社区")
    @TableField("area_level")
    private Integer areaLevel;

    @ApiModelProperty(value = "创建人用户id")
    @TableField("creator")
    private String creator;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "区域节点，默认值330300")
    @TableField("node_id")
    private Integer nodeId;

    @ApiModelProperty(value = "父节点id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "行政区划代码")
    @TableField("region_cd")
    private String regionCd;

    @ApiModelProperty(value = "行政区划名称")
    @TableField("region_name")
    private String regionName;

    @ApiModelProperty(value = "更新人用户id")
    @TableField("updator")
    private String updator;

}
