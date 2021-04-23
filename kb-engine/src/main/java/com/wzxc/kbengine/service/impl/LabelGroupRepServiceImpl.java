package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.configcommon.shiro.JwtFilter;
import com.wzxc.kbengine.service.ILabelGroupRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.LabelGroupRepMapper;
import com.wzxc.kbengine.vo.LabelGroupRep;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-12
 */
@Service
public class LabelGroupRepServiceImpl implements ILabelGroupRepService
{
    @Autowired
    private LabelGroupRepMapper labelGroupRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public LabelGroupRep selectLabelGroupRepById(Long id)
    {
        return labelGroupRepMapper.selectLabelGroupRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param labelGroupRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<LabelGroupRep> selectLabelGroupRepList(LabelGroupRep labelGroupRep)
    {
        return labelGroupRepMapper.selectLabelGroupRepList(labelGroupRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param labelGroupRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertLabelGroupRep(LabelGroupRep labelGroupRep)
    {
        labelGroupRep.setCreator(JwtFilter.getUserId());
        labelGroupRep.setUpdator(JwtFilter.getUserId());
        labelGroupRep.setUpdateTime(DateUtils.getNowDate());
        labelGroupRep.setCreateTime(DateUtils.getNowDate());
        return labelGroupRepMapper.insertLabelGroupRep(labelGroupRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param labelGroupRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateLabelGroupRep(LabelGroupRep labelGroupRep)
    {
        labelGroupRep.setUpdateTime(DateUtils.getNowDate());
        return labelGroupRepMapper.updateLabelGroupRep(labelGroupRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLabelGroupRepByIds(String ids)
    {
        return labelGroupRepMapper.deleteLabelGroupRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteLabelGroupRepById(Long id)
    {
        return labelGroupRepMapper.deleteLabelGroupRepById(id);
    }

    /**
     * 通过分组id删除标签与分组之间的映射关系
     * @param id
     * @return
     */
    @Override
    public int deleteLabelGroupRepByGroupId(Long id) {
        return labelGroupRepMapper.deleteLabelGroupRepByGroupId(id);
    }

    @Override
    public int clearGroup(String labelClass) {
        return labelGroupRepMapper.clearGroup(labelClass);
    }
}
