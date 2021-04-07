package com.wzxc.zzdpush.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 hhl_zzd_kpi
 * 
 * @author muhamuha
 * @date 2021-02-05
 */
@Data
public class HhlZzdKpi {

    private Integer id;

    private String kpiName;

    private String kpiId;

    private Integer status;

    private Date createTime;

    private String kpiValue; // 实际值

}
