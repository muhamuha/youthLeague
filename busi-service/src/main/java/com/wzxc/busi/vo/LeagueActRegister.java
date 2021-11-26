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
 * @date 2021-11-24
 */
@Data
@TableName("league_act_register")
@ApiModel(value="LeagueActRegister对象", description="")
public class LeagueActRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动id")
    @TableField("activity_id")
    private Long activityId;

    @TableField("commissinor_id")
    private Long commissinorId;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "开始通知内容")
    @TableField("notify_message")
    private String notifyMessage;

    @ApiModelProperty(value = "开始通知时间")
    @TableField("notify_time")
    private Date notifyTime;

    @ApiModelProperty(value = "是否发送浙政钉开始通知")
    @TableField("post_notify")
    private Integer postNotify;

    @ApiModelProperty(value = "是否发送浙政钉打卡通知")
    @TableField("post_sign")
    private Integer postSign;

    @ApiModelProperty(value = "打卡通知内容")
    @TableField("sign_message")
    private String signMessage;

    @ApiModelProperty(value = "打卡通知时间")
    @TableField("sign_notify_time")
    private Date signNotifyTime;

    @ApiModelProperty(value = "打卡时间")
    @TableField("sign_time")
    private Date signTime;

    @TableField(exist = false)
    private LeagueCommissinor leagueCommissinor;
    @TableField(exist = false)
    private LeagueActivity leagueActivity;
}
