package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.SysRegion;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-26
 */
public interface ISysRegionService extends IService<SysRegion> {

    List<SysRegion> selectSysRegionList(SysRegion sysRegion);

    int insertSysRegion(SysRegion sysRegion);

    int updateSysRegion(SysRegion sysRegion);

}
