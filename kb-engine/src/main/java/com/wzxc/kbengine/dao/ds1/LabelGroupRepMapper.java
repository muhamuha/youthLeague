package com.wzxc.kbengine.dao.ds1;

import java.util.List;
import com.wzxc.kbengine.vo.LabelGroupRep;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author huanghl
 * @date 2021-03-12
 */
public interface LabelGroupRepMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public LabelGroupRep selectLabelGroupRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param labelGroupRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<LabelGroupRep> selectLabelGroupRepList(LabelGroupRep labelGroupRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param labelGroupRep 【请填写功能名称】
     * @return 结果
     */
    public int insertLabelGroupRep(LabelGroupRep labelGroupRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param labelGroupRep 【请填写功能名称】
     * @return 结果
     */
    public int updateLabelGroupRep(LabelGroupRep labelGroupRep);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteLabelGroupRepById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLabelGroupRepByIds(String[] ids);

    /**
     * 通过分组id删除标签之间的映射关系
     * @param id
     * @return
     */
    int deleteLabelGroupRepByGroupId(Long id);

    /**
     * 根据标签类删除映射关系
     * @param labelClass
     * @return
     */
    int clearGroup(String labelClass);
}
