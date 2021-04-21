package com.wzxc.kbengine.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 【请填写功能名称】对象 SysOrg
 * 
 * @author mengs
 * @date 2021-04-15
 */
@Data
public class SysOrg {
    private static final long serialVersionUID = 1L;

    /** 牵头单位名称 */
    @ApiModelProperty(value = "牵头单位名称")
    private String orgName;

    /** 牵头单位编码 */
    @ApiModelProperty(value = "牵头单位编码")
    private String orgCode;

}
