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
@TableName("dsjkb_test.league_elect")
@ApiModel(value="LeagueElect对象", description="")
public class LeagueElect implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "委员id")
    @TableField("commisinor_id")
    private Long commisinorId;

    @ApiModelProperty(value = "委员姓名")
    @TableField("commisinor_name")
    private String commisinorName;

    @ApiModelProperty(value = "当选的地区")
    @TableField("elect_region")
    private String electRegion;

    @ApiModelProperty(value = "提名单位")
    @TableField("group_id")
    private Long groupId;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "是否连任：	0：否	1：是")
    @TableField("is_reelect")
    private Integer isReelect;

    @ApiModelProperty(value = "是否留任：	0：否	1：是")
    @TableField("is_stay")
    private Integer isStay;

    @ApiModelProperty(value = "当选年份")
    @TableField("year")
    private String year;

    @TableField(exist = false)
    private LeagueCommissinor leagueCommissinor;
    @TableField(exist = false)
    private String iphone;

}
