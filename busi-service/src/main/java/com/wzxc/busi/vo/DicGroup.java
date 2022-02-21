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
 * @date 2021-11-30
 */
@Data
@TableName("dic_group")
@ApiModel(value="DicGroup对象", description="")
public class DicGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "青联团体名称")
    @TableField("group_name")
    private String groupName;

    @ApiModelProperty(value = "组织类型：	1. 社会团体	2. 基金会	3. 社会服务机构	4. 人民团体	5. 其他")
    @TableField("group_type")
    private String groupType;

    @ApiModelProperty(value = "是否民政局注册")
    @TableField("is_register")
    private String isRegister;

    @ApiModelProperty(value = "主管业主单位")
    @TableField("upper_group")
    private String upperGroup;

}
