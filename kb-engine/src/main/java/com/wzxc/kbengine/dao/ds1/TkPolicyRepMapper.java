package com.wzxc.kbengine.dao.ds1;

import java.util.List;
import com.wzxc.kbengine.vo.TkPolicyRep;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author huanghl
 * @date 2021-03-11
 */
public interface TkPolicyRepMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TkPolicyRep selectTkPolicyRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tkPolicyRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TkPolicyRep> selectTkPolicyRepList(TkPolicyRep tkPolicyRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tkPolicyRep 【请填写功能名称】
     * @return 结果
     */
    public int insertTkPolicyRep(TkPolicyRep tkPolicyRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tkPolicyRep 【请填写功能名称】
     * @return 结果
     */
    public int updateTkPolicyRep(TkPolicyRep tkPolicyRep);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTkPolicyRepById(TkPolicyRep tkPolicyRep);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTkPolicyRepByIds(String[] ids);

    /**
     * 通过政策id删除关联
     * @param id
     * @return
     */
    int deleteTkPolicyRepByTkId(Long id);

    /**
     * 删除任务与政策之间的关联
     * @param tkBaseInfoId
     * @param policyBaseInfoIds
     * @return
     */
    int deleteTkPolicyRepByPolicyId(@Param("tkBaseInfoId") Long tkBaseInfoId, @Param("policyBaseInfoIds") List<Object> policyBaseInfoIds);
}
