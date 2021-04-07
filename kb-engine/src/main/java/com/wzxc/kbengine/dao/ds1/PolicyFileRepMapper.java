package com.wzxc.kbengine.dao.ds1;

import java.util.List;
import com.wzxc.kbengine.vo.PolicyFileRep;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author huanghl
 * @date 2021-03-25
 */
public interface PolicyFileRepMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public PolicyFileRep selectPolicyFileRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param policyFileRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PolicyFileRep> selectPolicyFileRepList(PolicyFileRep policyFileRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param policyFileRep 【请填写功能名称】
     * @return 结果
     */
    public int insertPolicyFileRep(PolicyFileRep policyFileRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param policyFileRep 【请填写功能名称】
     * @return 结果
     */
    public int updatePolicyFileRep(PolicyFileRep policyFileRep);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePolicyFileRepById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePolicyFileRepByIds(String[] ids);

    /**
     * 批导入附件
     * @param policyFileRepList
     * @return
     */
    int insertBatch(@Param("policyFileRepList") List<PolicyFileRep> policyFileRepList);
}
