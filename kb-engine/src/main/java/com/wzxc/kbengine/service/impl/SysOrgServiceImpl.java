package com.wzxc.kbengine.service.impl;

import com.wzxc.kbengine.dao.ds1.SysOrgMapper;
import com.wzxc.kbengine.service.ISysOrgService;
import com.wzxc.kbengine.vo.SysOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author mengs
 * @date 2021-04-13
 */
@Service
public class SysOrgServiceImpl implements ISysOrgService
{
    @Autowired
    private SysOrgMapper sysOrgMapper;

    /**
     * 查询【请填写功能名称】列表
     * 
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysOrg> selectSysOrgList(SysOrg sysOrg)
    {
        return sysOrgMapper.selectSysOrgList(sysOrg);
    }

}
