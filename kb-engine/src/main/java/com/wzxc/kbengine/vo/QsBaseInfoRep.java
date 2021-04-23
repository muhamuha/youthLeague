package com.wzxc.kbengine.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 qs_base_info_rep
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@Data
public class QsBaseInfoRep
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 问题标题 */
    @JsonProperty("qTitle")
    private String qTitle;

    /** 问题描述 */
    @JsonProperty("qDes")
    private String qDes;

    /** 问题回答 */
    @JsonProperty("qAnswer")
    private String qAnswer;

    /** 问题所属领域
0:党政机关整体智治系统,
1:数字政府系统;
2:数字社会系统;
3:数字经济系统;
4:数字法制系统 */
    @JsonProperty("qSystem")
    private Integer qSystem;

    /** 访问权限
0：全部可见
 */
    @JsonProperty("qPermission")
    private Integer qPermission;

    /** 审核状态：
0：待审核
1：审核通过 */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    /** 创建者 */
    private String creator;

    /** 更新者 */
    private String updator;

    /** 数源
0：手工录入
1：批导入 */
    private Integer resource;

    /** 是否有效
0：有效
1：无效 */
    private Integer isValid;

}
