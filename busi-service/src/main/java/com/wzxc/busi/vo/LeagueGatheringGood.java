package com.wzxc.busi.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类对象 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-07
 */
@Data
@TableName("league_gathering_good")
@ApiModel(value="LeagueGatheringGood对象", description="")
public class LeagueGatheringGood implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "推荐委员id")
    @TableField("commissinor_id")
    private Long commissinorId;

    @TableField("goodman_id")
    private Long goodmanId;

    @ApiModelProperty(value = "活动名称")
    @TableField("activity_title")
    private String activityTitle;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "聚会日期")
    @TableField("gather_date")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date gatherDate;

    @ApiModelProperty(value = "优秀青年id（推荐人）")
    @TableField("gm_id")
    private Long gmId;

    @ApiModelProperty(value = "创建人")
    @TableField("creater")
    private String creater;

}
