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
 * @date 2022-01-07
 */
@Data
@TableName("league_answer")
@ApiModel(value="LeagueAnswer对象", description="")
public class LeagueAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "回复人id")
    @TableField("answer_id")
    private Long answerId;

    @TableField("content")
    private String content;

    @ApiModelProperty(value = "帖子id")
    @TableField("publish_id")
    private Long publishId;

    @ApiModelProperty(value = "回复人姓名")
    @TableField("answer_name")
    private String answerName;

}
