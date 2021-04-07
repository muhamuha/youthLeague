package com.wzxc.kbengine.service;

import java.util.List;
import com.wzxc.kbengine.vo.LabelBaseInfoRep;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author huanghl
 * @date 2021-03-11
 */
public interface ILabelBaseInfoRepService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public LabelBaseInfoRep selectLabelBaseInfoRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param labelBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<LabelBaseInfoRep> selectLabelBaseInfoRepList(LabelBaseInfoRep labelBaseInfoRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param labelBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int insertLabelBaseInfoRep(LabelBaseInfoRep labelBaseInfoRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param labelBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int updateLabelBaseInfoRep(LabelBaseInfoRep labelBaseInfoRep);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLabelBaseInfoRepByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteLabelBaseInfoRepById(LabelBaseInfoRep labelBaseInfoRep);

    /**
     * 获取通用标签列表
     * @param labelBaseInfoRep
     * @return
     */
    List<LabelBaseInfoRep> selectGeneralLableList(LabelBaseInfoRep labelBaseInfoRep);

    /**
     * 获取分组下面的标签
     * @param id
     * @return
     */
    List<LabelBaseInfoRep> selectLabelListByGroupId(LabelBaseInfoRep labelBaseInfoRep);

    /**
     * 判断是否存在重复名称的标签
     * @param labelBaseInfoRep
     * @return
     */
    boolean havaRepeatName(LabelBaseInfoRep labelBaseInfoRep);
}
