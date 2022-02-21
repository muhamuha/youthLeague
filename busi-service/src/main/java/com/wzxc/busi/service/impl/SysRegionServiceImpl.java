package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.SysRegionMapper;
import com.wzxc.busi.vo.SysRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ISysRegionService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-26
 */
@Service
public class SysRegionServiceImpl extends ServiceImpl<SysRegionMapper, SysRegion> implements ISysRegionService {

    @Autowired
    private SysRegionMapper sysRegionMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysRegion 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysRegion> selectSysRegionList(SysRegion sysRegion) {
        return sysRegionMapper.selectSysRegionList(sysRegion);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysRegion 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysRegion(SysRegion sysRegion) {
        return sysRegionMapper.insertSysRegion(sysRegion);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysRegion 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysRegion(SysRegion sysRegion) {
        return sysRegionMapper.updateSysRegion(sysRegion);
    }



}