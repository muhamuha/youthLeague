package com.wzxc.kbengine.vo;

import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 policy_file_rep
 * 
 * @author huanghl
 * @date 2021-03-25
 */
@Data
public class PolicyFileRep
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** oa外键 */
    private Long fileOutId;

    /** 附件名称 */
    private String fileName;

    /** 附件url地址 */
    private String fileUrl;

    /** 状态
        0：有效
        1：无效 */
    private Long status;

    private Date createTime;

}
