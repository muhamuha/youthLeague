package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.configcommon.shiro.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.RefLabelRepMapper;
import com.wzxc.kbengine.vo.RefLabelRep;
import com.wzxc.kbengine.service.IRefLabelRepService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Service
public class RefLabelRepServiceImpl implements IRefLabelRepService
{
    @Autowired
    private RefLabelRepMapper refLabelRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public RefLabelRep selectRefLabelRepById(Long id)
    {
        return refLabelRepMapper.selectRefLabelRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param policyLabelRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<RefLabelRep> selectRefLabelRepList(RefLabelRep policyLabelRep)
    {
        return refLabelRepMapper.selectRefLabelRepList(policyLabelRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param policyLabelRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertRefLabelRep(RefLabelRep policyLabelRep)
    {
        policyLabelRep.setCreator(JwtFilter.getUserId());
        policyLabelRep.setUpdator(JwtFilter.getUserId());
        policyLabelRep.setCreateTime(DateUtils.getNowDate());
        policyLabelRep.setUpdateTime(DateUtils.getNowDate());
        return refLabelRepMapper.insertRefLabelRep(policyLabelRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param policyLabelRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateRefLabelRep(RefLabelRep policyLabelRep)
    {
        return refLabelRepMapper.updateRefLabelRep(policyLabelRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRefLabelRepByIds(String ids)
    {
        return refLabelRepMapper.deleteRefLabelRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteRefLabelRepById(Long id)
    {
        return refLabelRepMapper.deleteRefLabelRepById(id);
    }

    @Override
    public int clearLabels(Long id) {
        return refLabelRepMapper.clearLabels(id);
    }
}
