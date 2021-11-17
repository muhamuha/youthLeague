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
 * @date 2021-10-25
 */
@Data
@TableName("dsjkb_test.league_activity")
@ApiModel(value="LeagueActivity对象", description="")
public class LeagueActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动开始时间")
    @TableField("activity_begin")
    private Date activityBegin;

    @TableField("activity_end")
    private Date activityEnd;

    @ApiModelProperty(value = "活动类型（字典表）")
    @TableField("activity_type")
    private Long activityType;

    @ApiModelProperty(value = "活动地点")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "活动内容或者行程安排")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "主办人id")
    @TableField("hostman")
    private Long hostman;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "活动别名")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "行业范围")
    @TableField("range_industry")
    private String rangeIndustry;

    @ApiModelProperty(value = "地区范围")
    @TableField("range_region")
    private String rangeRegion;

    @ApiModelProperty(value = "签到开始时间")
    @TableField("sign_begin")
    private Date signBegin;

    @ApiModelProperty(value = "签到结束时间")
    @TableField("sign_end")
    private Date signEnd;

}
