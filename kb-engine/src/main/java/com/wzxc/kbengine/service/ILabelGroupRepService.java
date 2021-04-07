package com.wzxc.kbengine.service;

import java.util.List;
import com.wzxc.kbengine.vo.LabelGroupRep;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author huanghl
 * @date 2021-03-12
 */
public interface ILabelGroupRepService 
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
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLabelGroupRepByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteLabelGroupRepById(Long id);

    /**
     * 通过分组id删除标签与分组之间的映射关系
     * @param id
     * @return
     */
    public int deleteLabelGroupRepByGroupId(Long id);

    /**
     * 清空分组信息（通过标签所属类）
     * @param labelClass
     * @return
     */
    int clearGroup(String labelClass);
}
