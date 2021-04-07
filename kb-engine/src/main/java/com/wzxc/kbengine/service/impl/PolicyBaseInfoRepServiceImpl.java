package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.uuid.IdUtils;
import com.wzxc.kbengine.shiro.JwtFilter;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.PolicyBaseInfoRepMapper;
import com.wzxc.kbengine.vo.PolicyBaseInfoRep;
import com.wzxc.kbengine.service.IPolicyBaseInfoRepService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Service
public class PolicyBaseInfoRepServiceImpl implements IPolicyBaseInfoRepService 
{
    @Autowired
    private PolicyBaseInfoRepMapper policyBaseInfoRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public PolicyBaseInfoRep selectPolicyBaseInfoRepById(Long id)
    {
        return policyBaseInfoRepMapper.selectPolicyBaseInfoRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param policyBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PolicyBaseInfoRep> selectPolicyBaseInfoRepList(PolicyBaseInfoRep policyBaseInfoRep)
    {
        if(policyBaseInfoRep.getIsValid() == null){
            policyBaseInfoRep.setIsValid(0);
        }
        return policyBaseInfoRepMapper.selectPolicyBaseInfoRepList(policyBaseInfoRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param policyBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertPolicyBaseInfoRep(PolicyBaseInfoRep policyBaseInfoRep)
    {
        policyBaseInfoRep.setPolicyId("wz_" + IdUtils.getSnowflakeId());
        if(policyBaseInfoRep.getFileObjectList() != null && policyBaseInfoRep.getFileObjectList().size() > 0){
            policyBaseInfoRep.setFileObjectStr(JSON.toJSONString(policyBaseInfoRep.getFileObjectList()));
        }
        policyBaseInfoRep.setCdOperation("i");
        policyBaseInfoRep.setCreateTime(DateUtils.getNowDate());
        policyBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        policyBaseInfoRep.setCreator(JwtFilter.getUserId());
        policyBaseInfoRep.setUpdator(JwtFilter.getUserId());
        return policyBaseInfoRepMapper.insertPolicyBaseInfoRep(policyBaseInfoRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param policyBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updatePolicyBaseInfoRep(PolicyBaseInfoRep policyBaseInfoRep)
    {
        if(policyBaseInfoRep.getFileObjectList() != null && policyBaseInfoRep.getFileObjectList().size() > 0){
            policyBaseInfoRep.setFileObjectStr(JSON.toJSONString(policyBaseInfoRep.getFileObjectList()));
        }
        policyBaseInfoRep.setUpdator(JwtFilter.getUserId());
        policyBaseInfoRep.setCdOperation("u");
        policyBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        return policyBaseInfoRepMapper.updatePolicyBaseInfoRep(policyBaseInfoRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePolicyBaseInfoRepByIds(String ids)
    {
        return policyBaseInfoRepMapper.deletePolicyBaseInfoRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deletePolicyBaseInfoRepById(PolicyBaseInfoRep policyBaseInfoRep)
    {
        policyBaseInfoRep.setUpdator(JwtFilter.getUserId());
        policyBaseInfoRep.setCdOperation("d");
        policyBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        return policyBaseInfoRepMapper.deletePolicyBaseInfoRepById(policyBaseInfoRep);
    }

    @Override
    public int insertBatch(InsertBatchCommon insertBatchCommon) {
        return policyBaseInfoRepMapper.insertBatch(insertBatchCommon);
    }

    @Override
    public List<PolicyBaseInfoRep> selectPolicyBaseInfoRepListByTkId(PolicyBaseInfoRep policyBaseInfoRep) {
        if(policyBaseInfoRep.getIsValid() == null){
            policyBaseInfoRep.setIsValid(0);
        }
        return policyBaseInfoRepMapper.selectPolicyBaseInfoRepListByTkId(policyBaseInfoRep);
    }

    @Override
    public List<PolicyBaseInfoRep> selectSynFileFromOa() {
        return policyBaseInfoRepMapper.selectSynFileFromOa();
    }

    @Override
    public int updateBatchFileObjectList(List<PolicyBaseInfoRep> policyBaseInfoReps) {
        return policyBaseInfoRepMapper.updateBatchFileObjectList(policyBaseInfoReps);
    }

    @Override
    public List<PolicyBaseInfoRep> selectPolicyBaseInfoRepByTkIdOnly(PolicyBaseInfoRep policyBaseInfoRep) {
        if(policyBaseInfoRep.getIsValid() == null){
            policyBaseInfoRep.setIsValid(0);
        }
        return policyBaseInfoRepMapper.selectPolicyBaseInfoRepByTkIdOnly(policyBaseInfoRep);
    }

    @Override
    public List<PolicyBaseInfoRep> selectSynPolicyContentFromOa() {
        return policyBaseInfoRepMapper.selectSynPolicyContentFromOa();
    }

    @Override
    public int updateBatchPolicyContentList(List<PolicyBaseInfoRep> policyBaseInfoReps) {
        return policyBaseInfoRepMapper.updateBatchPolicyContentList(policyBaseInfoReps);
    }

    @Override
    public List<PolicyBaseInfoRep> selectSynPolicyFileFromOa() {
        return policyBaseInfoRepMapper.selectSynPolicyFileFromOa();
    }

    @Override
    public int updatePolicyContentSi(PolicyBaseInfoRep policy) {
        return policyBaseInfoRepMapper.updatePolicyContentSi(policy);
    }
}
