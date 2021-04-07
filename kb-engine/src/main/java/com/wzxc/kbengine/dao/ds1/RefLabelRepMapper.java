package com.wzxc.kbengine.dao.ds1;

import java.util.List;
import com.wzxc.kbengine.vo.RefLabelRep;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author huanghl
 * @date 2021-03-11
 */
public interface RefLabelRepMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteRefLabelRepById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRefLabelRepByIds(String[] ids);

    /**
     * 清空所有标签
     * @param id
     * @return
     */
    int clearLabels(Long id);
}
