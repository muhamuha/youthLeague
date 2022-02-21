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

    @ApiModelProperty(value = "活动结束时间")
    @TableField("activity_end")
    private Date activityEnd;

    @ApiModelProperty(value = "活动时间区间：半天 全天")
    @TableField("activity_period")
    private String activityPeriod;

    @ApiModelProperty(value = "活动类型（字典表）")
    @TableField("activity_type")
    private Long activityType;

    @ApiModelProperty(value = "活动地点")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "活动内容或者行程安排")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "主办人名称")
    @TableField("hostman")
    private String hostman;

    @ApiModelProperty(value = "主办人id")
    @TableField("hostman_id")
    private String hostmanId;

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

    @ApiModelProperty(value = "创建人")
    @TableField("creater")
    private String creater;

    @ApiModelProperty(value = "是否开启打卡")
    @TableField("is_sign")
    private Boolean isSign;

    @ApiModelProperty(value = "活动地点的纬度")
    @TableField("lat")
    private Double lat;

    @ApiModelProperty(value = "活动地点的经度")
    @TableField("lon")
    private Double lon;

    @ApiModelProperty(value = "打卡范围")
    @TableField("sign_range")
    private Integer signRange;

    @TableField(exist = false)
    private Integer score;

}
