package com.wzxc.busi.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类对象 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-10-20
 */
@Data
@TableName("league_commissinor")
@ApiModel(value="LeagueCommissinor对象", description="")
public class LeagueCommissinor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭住址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "所在公司")
    @TableField("company")
    private String company;

    @ApiModelProperty(value = "创建人（浙政钉id）")
    @TableField("creater")
    private String creater;

    @ApiModelProperty(value = "教育情况	1. 中专/高中	2. 专科	3. 本科	4. 硕士研究生	5. 博士")
    @TableField("education")
    private Integer education;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "荣誉附件地址")
    @TableField("honor_file")
    private String honorFile;

    @ApiModelProperty(value = "荣誉层级	1. 省级	2. 市级	3. 县级")
    @TableField("honor_level")
    private Integer honorLevel;

    @ApiModelProperty(value = "荣誉名称")
    @TableField("honor_name")
    private String honorName;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所在行业（字典表）")
    @TableField("industry_id")
    private Long industryId;

    @ApiModelProperty(value = "手机号码")
    @TableField("iphone")
    private String iphone;

    @ApiModelProperty(value = "入委时间")
    @TableField("join_date")
    private Date joinDate;

    @ApiModelProperty(value = "出委时间")
    @TableField("leave_date")
    private Date leaveDate;

    @ApiModelProperty(value = "离开原因")
    @TableField("leave_reason")
    private String leaveReason;

    @ApiModelProperty(value = "所在地")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "政府单位职务")
    @TableField("org_position")
    private String orgPosition;

    @ApiModelProperty(value = "政府所在单位")
    @TableField("organization")
    private String organization;

    @ApiModelProperty(value = "个人照片地址")
    @TableField("picture")
    private String picture;

    @ApiModelProperty(value = "职务")
    @TableField("position")
    private String position;

}
