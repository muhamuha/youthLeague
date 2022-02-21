package com.wzxc.busi.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体类对象 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-07
 */
@Data
@TableName("league_goodman")
@ApiModel(value="LeagueGoodman对象", description="")
public class LeagueGoodman implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手机号码")
    @TableField("iphone")
    private String iphone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "家庭住址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "教育情况")
    @TableField("education")
    private String education;

    @ApiModelProperty(value = "所在公司")
    @TableField("company")
    private String company;

    @ApiModelProperty(value = "荣誉层级	1. 省级	2. 市级	3. 县级")
    @TableField("honor_level")
    private Integer honorLevel;

    @ApiModelProperty(value = "荣誉名称")
    @TableField("honor_name")
    private String honorName;

    @ApiModelProperty(value = "荣誉附件地址")
    @TableField("honor_file")
    private String honorFile;

    @ApiModelProperty(value = "政府所在单位和职务")
    @TableField("org_position")
    private String orgPosition;

    @ApiModelProperty(value = "个人照片地址")
    @TableField("picture")
    private String picture;

    @ApiModelProperty(value = "工作所在地")
    @TableField("workplace")
    private String workplace;

    @ApiModelProperty(value = "所在行业（字典表）")
    @TableField("industry")
    private String industry;

    @ApiModelProperty(value = "最新修改时间")
    @TableField("updte_time")
    private String updteTime;

    @ApiModelProperty(value = "创建人（浙政钉id）")
    @TableField("creater")
    private String creater;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    @TableField("birthday")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "身份证号")
    @TableField("idcard")
    private String idcard;

    @ApiModelProperty(value = "户籍")
    @TableField("household")
    private String household;

    @ApiModelProperty(value = "籍贯")
    @TableField("origin")
    private String origin;

    @ApiModelProperty(value = "民族")
    @TableField("nation")
    private String nation;

    @ApiModelProperty(value = "政治面貌")
    @TableField("political_status")
    private String politicalStatus;

    @ApiModelProperty(value = "学位")
    @TableField("degree")
    private String degree;

    @ApiModelProperty(value = "职称（字典）")
    @TableField("org_title")
    private String orgTitle;

    @ApiModelProperty(value = "职级（字典）")
    @TableField("org_office")
    private String orgOffice;

    @ApiModelProperty(value = "职业（字典）")
    @TableField("vocation")
    private String vocation;

    @ApiModelProperty(value = "社会职务")
    @TableField("social_office")
    private String socialOffice;

    @ApiModelProperty(value = "市级以上党代表的情况")
    @TableField("deputy_party")
    private String deputyParty;

    @ApiModelProperty(value = "市级以上人大代表情况")
    @TableField("deputy_npc")
    private String deputyNpc;

    @ApiModelProperty(value = "市级以上政协委员的情况")
    @TableField("deputy_cppcc")
    private String deputyCppcc;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "所在地")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "所在政府单位")
    @TableField("organization")
    private String organization;

    @ApiModelProperty(value = "公司职务")
    @TableField("position")
    private String position;

    @ApiModelProperty(value = "毕业院校")
    @TableField("campus")
    private String campus;

    @ApiModelProperty(value = "浙政钉id")
    @TableField("employee_code")
    private String employeeCode;

    @ApiModelProperty(value = "是否删除	0：未删除	1：已删除")
    @TableField("is_delete")
    private Integer isDelete;

    @ApiModelProperty(value = "推荐委员id")
    @TableField("commissinor_id")
    private Long commissinorId;

    @ApiModelProperty(value = "优秀青年id（推荐方）")
    @TableField("goodman_id")
    private Long goodmanId;

    @TableField(exist = false)
    private LeagueCommissinor leagueCommissinor;
    @TableField(exist = false)
    private LeagueGoodman leagueGoodman;
//    @TableField(exist = false)
//    private List<LeagueGatheringGood> leagueGatheringGoodList;
    @TableField(exist = false)
    private String commissinorName;
}
