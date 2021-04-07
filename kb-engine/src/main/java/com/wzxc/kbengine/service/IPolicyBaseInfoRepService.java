package com.wzxc.kbengine.service;

import java.util.List;

import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.kbengine.vo.PolicyBaseInfoRep;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author huanghl
 * @date 2021-03-11
 */
public interface IPolicyBaseInfoRepService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public PolicyBaseInfoRep selectPolicyBaseInfoRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param policyBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PolicyBaseInfoRep> selectPolicyBaseInfoRepList(PolicyBaseInfoRep policyBaseInfoRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param policyBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int insertPolicyBaseInfoRep(PolicyBaseInfoRep policyBaseInfoRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param policyBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int updatePolicyBaseInfoRep(PolicyBaseInfoRep policyBaseInfoRep);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePolicyBaseInfoRepByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePolicyBaseInfoRepById(PolicyBaseInfoRep policyBaseInfoRep);

    /**
     * 政策批导入
     * @param insertBatchCommon
     * @return
     */
    int insertBatch(InsertBatchCommon insertBatchCommon);

    /**
     * 通过任务id查询政策列表
     * @param policyBaseInfoRep
     * @return
     */
    List<PolicyBaseInfoRep> selectPolicyBaseInfoRepListByTkId(PolicyBaseInfoRep policyBaseInfoRep);

    /**
     * 查询所有来自oa需要同步附件的政策列表
     * @return
     */
    List<PolicyBaseInfoRep> selectSynFileFromOa();

    /**
     * 批量修改附件信息
     * @param policyBaseInfoReps
     * @return
     */
    int updateBatchFileObjectList(List<PolicyBaseInfoRep> policyBaseInfoReps);

    /**
     * 只搜索和该任务祥相关
     * @param policyBaseInfoRep
     * @return
     */
    List<PolicyBaseInfoRep> selectPolicyBaseInfoRepByTkIdOnly(PolicyBaseInfoRep policyBaseInfoRep);

    /**
     * 查询所有来自oa需要同步正文的政策列表
     * @return
     */
    List<PolicyBaseInfoRep> selectSynPolicyContentFromOa();

    /**
     * 批量修改正文信息
     * @param policyBaseInfoReps
     * @return
     */
    int updateBatchPolicyContentList(List<PolicyBaseInfoRep> policyBaseInfoReps);

    /**
     * 查询所有来自oa需要同步政策附件（同步到policy_file_rep表）的政策列表
     * @return
     */
    List<PolicyBaseInfoRep> selectSynPolicyFileFromOa();

    /**
     * 修改正文信息 -> 无标签正文
     * @param policy
     * @return
     */
    int updatePolicyContentSi(PolicyBaseInfoRep policy);
}
