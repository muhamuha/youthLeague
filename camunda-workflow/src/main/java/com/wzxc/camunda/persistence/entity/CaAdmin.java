package com.wzxc.camunda.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类对象 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-11-05
 */
@Data
@TableName("dsjkb_test.ca_admin")
@ApiModel(value = "CaAdmin对象", description = "")
public class CaAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("email")
    private String email;

    @TableField("firstname")
    private String firstname;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField("lastname")
    private String lastname;

    @TableField("password")
    private String password;

    @TableField("user_id")
    private String userId;

}
