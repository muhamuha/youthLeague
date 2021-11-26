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
 * @date 2021-11-23
 */
@Data
@TableName("dic_activity")
@ApiModel(value="DicActivity对象", description="")
public class DicActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分数")
    @TableField("score")
    private String score;

    @ApiModelProperty(value = "活动名称")
    @TableField("activity_name")
    private String activityName;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

}
