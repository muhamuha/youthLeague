package com.wzxc.busi.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类对象 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2022-01-07
 */
@Data
@TableName("league_publish")
@ApiModel(value="LeaguePublish对象", description="")
public class LeaguePublish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发布者id")
    @TableField("creater_id")
    private Long createrId;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "是否删除	0：正常	1：删除")
    @TableField("is_delete")
    private Integer isDelete;

    @ApiModelProperty(value = "发布人姓名")
    @TableField("creater_name")
    private String createrName;

    @ApiModelProperty(value = "发帖时间")
    @TableField("create_time")
    private Date createTime;

}
