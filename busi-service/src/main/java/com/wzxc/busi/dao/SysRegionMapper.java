package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.SysRegion;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-26
 */
public interface SysRegionMapper extends BaseMapper<SysRegion> {

    List<SysRegion> selectSysRegionList(SysRegion sysRegion);

    int insertSysRegion(SysRegion sysRegion);

    int updateSysRegion(SysRegion sysRegion);
}
