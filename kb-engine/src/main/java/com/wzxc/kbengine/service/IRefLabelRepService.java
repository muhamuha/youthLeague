package com.wzxc.kbengine.service;

import java.util.List;
import com.wzxc.kbengine.vo.RefLabelRep;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author huanghl
 * @date 2021-03-11
 */
public interface IRefLabelRepService
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public RefLabelRep selectRefLabelRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param policyLabelRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<RefLabelRep> selectRefLabelRepList(RefLabelRep policyLabelRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param policyLabelRep 【请填写功能名称】
     * @return 结果
     */
    public int insertRefLabelRep(RefLabelRep policyLabelRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param policyLabelRep 【请填写功能名称】
     * @return 结果
     */
    public int updateRefLabelRep(RefLabelRep policyLabelRep);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRefLabelRepByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteRefLabelRepById(Long id);

    /**
     * 清空所有的标签
     * @return
     */
    int clearLabels(Long id);
}
