package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.kbengine.dao.ds1.PolicyFileRepMapper;
import com.wzxc.kbengine.vo.PolicyFileRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.service.IPolicyFileRepService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-25
 */
@Service
public class PolicyFileRepServiceImpl implements IPolicyFileRepService 
{
    @Autowired
    private PolicyFileRepMapper policyFileRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public PolicyFileRep selectPolicyFileRepById(Long id)
    {
        return policyFileRepMapper.selectPolicyFileRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param policyFileRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PolicyFileRep> selectPolicyFileRepList(PolicyFileRep policyFileRep)
    {
        return policyFileRepMapper.selectPolicyFileRepList(policyFileRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param policyFileRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertPolicyFileRep(PolicyFileRep policyFileRep)
    {
        policyFileRep.setCreateTime(DateUtils.getNowDate());
        return policyFileRepMapper.insertPolicyFileRep(policyFileRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param policyFileRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updatePolicyFileRep(PolicyFileRep policyFileRep)
    {
        return policyFileRepMapper.updatePolicyFileRep(policyFileRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePolicyFileRepByIds(String ids)
    {
        return policyFileRepMapper.deletePolicyFileRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deletePolicyFileRepById(Long id)
    {
        return policyFileRepMapper.deletePolicyFileRepById(id);
    }

    @Override
    public int insertBatch(List<PolicyFileRep> policyFileRepList) {
        return policyFileRepMapper.insertBatch(policyFileRepList);
    }
}
