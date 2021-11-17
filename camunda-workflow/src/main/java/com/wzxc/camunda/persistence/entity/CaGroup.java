package com.wzxc.camunda.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类对象 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-11-02
 */
@Data
@TableName("dsjkb_test.ca_group")
public class CaGroup extends GroupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("create_time")
    private Date createTime;

}
