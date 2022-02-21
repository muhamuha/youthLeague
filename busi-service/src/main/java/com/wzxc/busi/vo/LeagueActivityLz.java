package com.wzxc.busi.vo;

import camundajar.impl.scala.Int;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wzxc.busi.en.lz.LzType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类对象 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-01
 */
@Data
@TableName("league_activity_lz")
@ApiModel(value="LeagueActivityLz对象", description="")
public class LeagueActivityLz implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "荣誉名称")
    @TableField("honor_name")
    private String honorName;

    @ApiModelProperty(value = "荣誉类型")
    @TableField("honor_type")
    private String honorType;

    @ApiModelProperty(value = "荣誉时间")
    @TableField("honor_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date honorTime;

    @ApiModelProperty(value = "履职分")
    @TableField("score")
    private Integer score;

    @ApiModelProperty(value = "创建人")
    @TableField("creater")
    private String creater;

    @ApiModelProperty(value = "委员id")
    @TableField("commissinor_id")
    private Long commissinorId;

    @TableField(exist = false)
    private Integer lzType = LzType.ACTIVITY.getValue();
    @TableField(exist = false)
    private LeagueCommissinor leagueCommissinor;

}
