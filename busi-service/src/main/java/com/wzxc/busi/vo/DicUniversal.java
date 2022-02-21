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
 * @date 2021-12-01
 */
@Data
@TableName("dic_universal")
@ApiModel(value="DicUniversal对象", description="")
public class DicUniversal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("dic_value")
    private String dicValue;

    @TableField("weight")
    private Integer weight;

    @TableField("dic_key")
    private String dicKey;

    @TableField("type")
    private String type;

}
