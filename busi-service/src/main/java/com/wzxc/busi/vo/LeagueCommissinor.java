package com.wzxc.busi.vo;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @date 2021-10-29
 */
@Data
@TableName("dsjkb_test.league_commissinor")
@ApiModel(value="LeagueCommissinor对象", description="")
public class LeagueCommissinor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭住址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "出生日期")
    @TableField("birthday")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "毕业院校")
    @TableField("campus")
    private String campus;

    @ApiModelProperty(value = "所在公司")
    @TableField("company")
    private String company;

    @ApiModelProperty(value = "创建人（浙政钉id）")
    @TableField("creater")
    private String creater;

    @ApiModelProperty(value = "学位")
    @TableField("degree")
    private String degree;

    @ApiModelProperty(value = "市级以上政协委员的情况")
    @TableField("deputy_cppcc")
    private String deputyCppcc;

    @ApiModelProperty(value = "市级以上人大代表情况")
    @TableField("deputy_npc")
    private String deputyNpc;

    @ApiModelProperty(value = "市级以上党代表的情况")
    @TableField("deputy_party")
    private String deputyParty;

    @ApiModelProperty(value = "教育情况	1. 中专/高中	2. 专科	3. 本科	4. 硕士研究生	5. 博士")
    @TableField("education")
    private Integer education;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "荣誉附件地址")
    @TableField("honor_file")
    private String honorFile;

    @ApiModelProperty(value = "荣誉层级	1. 省级	2. 市级	3. 县级")
    @TableField("honor_level")
    private Integer honorLevel;

    @ApiModelProperty(value = "荣誉名称")
    @TableField("honor_name")
    private String honorName;

    @ApiModelProperty(value = "户籍")
    @TableField("household")
    private String household;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "身份证号")
    @TableField("idcard")
    private String idcard;

    @ApiModelProperty(value = "所在行业（字典表）")
    @TableField("industry_id")
    private Long industryId;

    @ApiModelProperty(value = "手机号码")
    @TableField("iphone")
    private String iphone;

    @ApiModelProperty(value = "入委时间")
    @TableField("join_date")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date joinDate;

    @ApiModelProperty(value = "青联职务")
    @TableField("league_office")
    private String leagueOffice;

    @ApiModelProperty(value = "出委时间")
    @TableField("leave_date")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date leaveDate;

    @ApiModelProperty(value = "离开原因")
    @TableField("leave_reason")
    private String leaveReason;

    @ApiModelProperty(value = "所在地")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "民族")
    @TableField("nation")
    private String nation;

    @ApiModelProperty(value = "职务")
    @TableField("org_office")
    private String orgOffice;

    @ApiModelProperty(value = "政府所在单位和职务")
    @TableField("org_position")
    private String orgPosition;

    @ApiModelProperty(value = "职称")
    @TableField("org_title")
    private String orgTitle;

    @ApiModelProperty(value = "所在政府单位")
    @TableField("organization")
    private String organization;

    @ApiModelProperty(value = "籍贯")
    @TableField("origin")
    private String origin;

    @ApiModelProperty(value = "个人照片地址")
    @TableField("picture")
    private String picture;

    @ApiModelProperty(value = "政治面貌")
    @TableField("political_status")
    private String politicalStatus;

    @ApiModelProperty(value = "公司职务")
    @TableField("position")
    private String position;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "社会职务")
    @TableField("social_office")
    private String socialOffice;

    @ApiModelProperty(value = "职业（字典）")
    @TableField("vocation_id")
    private Long vocationId;

    @ApiModelProperty(value = "工作所在地")
    @TableField("workplace")
    private String workplace;

    @ApiModelProperty(value = "浙政钉code")
    @TableField("employee_code")
    private String employeeCode;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_delete")
    private int isDelete;

}
