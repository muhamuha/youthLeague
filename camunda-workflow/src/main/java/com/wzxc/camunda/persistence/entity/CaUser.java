package com.wzxc.camunda.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;

import java.io.Serializable;

/**
 * 实体类对象 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-11-02
 */
@Data
@TableName("dsjkb_test.sys_employee")
public class CaUser extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("employee_name")
    private String firstname;

    @TableField("employee_code")
    private String id;

    @TableField("employee_name")
    private String lastname;

    @TableField("employee_name")
    private String employeeName;

    @TableField("employee_code")
    private String employeeCode;

}
