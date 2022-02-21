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
 * @date 2021-12-02
 */
@Data
@TableName("league_industry")
@ApiModel(value="LeagueIndustry对象", description="")
public class LeagueIndustry implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "委员id")
    @TableField("commissinor_id")
    private Long commissinorId;

    @ApiModelProperty(value = "职位")
    @TableField("position")
    private Long position;
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String positionName;

    @ApiModelProperty(value = "创建人")
    @TableField("creater")
    private String creater;

    @ApiModelProperty(value = "社团id")
    @TableField("industry_id")
    private Long industryId;
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String industryName;

    @ApiModelProperty(value = "委员姓名")
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    @ApiModelProperty(value = "手机号")
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String iphone;

    @TableField(exist = false)
    private LeagueCommissinor leagueCommissinor;

}
