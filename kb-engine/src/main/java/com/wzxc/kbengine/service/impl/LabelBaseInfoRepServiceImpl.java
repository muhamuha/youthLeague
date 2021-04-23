package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.uuid.IdUtils;
import com.wzxc.configcommon.shiro.JwtFilter;
import com.wzxc.kbengine.dao.ds1.LabelBaseInfoRepMapper;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.vo.LabelBaseInfoRep;
import com.wzxc.kbengine.service.ILabelBaseInfoRepService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Service
public class LabelBaseInfoRepServiceImpl implements ILabelBaseInfoRepService 
{
    @Autowired
    private LabelBaseInfoRepMapper labelBaseInfoRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public LabelBaseInfoRep selectLabelBaseInfoRepById(Long id)
    {
        return labelBaseInfoRepMapper.selectLabelBaseInfoRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param labelBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<LabelBaseInfoRep> selectLabelBaseInfoRepList(LabelBaseInfoRep labelBaseInfoRep)
    {
        return labelBaseInfoRepMapper.selectLabelBaseInfoRepList(labelBaseInfoRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param labelBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertLabelBaseInfoRep(LabelBaseInfoRep labelBaseInfoRep)
    {
        labelBaseInfoRep.setCreator(JwtFilter.getUserId());
        labelBaseInfoRep.setUpdator(JwtFilter.getUserId());
        if(StringUtils.isEmpty(labelBaseInfoRep.getLabelClass())){
            labelBaseInfoRep.setLabelClass("lable_" + IdUtils.fastSimpleUUID());
        }
        labelBaseInfoRep.setLabelId("wz_" + IdUtils.getSnowflakeId());
        labelBaseInfoRep.setCreateTime(DateUtils.getNowDate());
        labelBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        labelBaseInfoRep.setCdOperation("i");
        return labelBaseInfoRepMapper.insertLabelBaseInfoRep(labelBaseInfoRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param labelBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateLabelBaseInfoRep(LabelBaseInfoRep labelBaseInfoRep)
    {
        labelBaseInfoRep.setUpdator(JwtFilter.getUserId());
        labelBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        labelBaseInfoRep.setCdOperation("u");
        return labelBaseInfoRepMapper.updateLabelBaseInfoRep(labelBaseInfoRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLabelBaseInfoRepByIds(String ids)
    {
        return labelBaseInfoRepMapper.deleteLabelBaseInfoRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteLabelBaseInfoRepById(LabelBaseInfoRep labelBaseInfoRep)
    {
        labelBaseInfoRep.setUpdator(JwtFilter.getUserId());
        labelBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        labelBaseInfoRep.setCdOperation("d");
        return labelBaseInfoRepMapper.deleteLabelBaseInfoRepById(labelBaseInfoRep);
    }

    /**
     * 获取通用标签列表
     * @param labelBaseInfoRep
     * @return
     */
    @Override
    public List<LabelBaseInfoRep> selectGeneralLableList(LabelBaseInfoRep labelBaseInfoRep) {
        return labelBaseInfoRepMapper.selectGeneralLableList(labelBaseInfoRep);
    }

    /**
     * 获取分组下面的标签
     * @param id
     * @return
     */
    @Override
    public List<LabelBaseInfoRep> selectLabelListByGroupId(LabelBaseInfoRep labelBaseInfoRep) {
        return labelBaseInfoRepMapper.selectLabelListByGroupId(labelBaseInfoRep);
    }

    /**
     * 判断是否存在重复名称的标签
     * @param labelBaseInfoRep
     * @return
     */
    @Override
    public boolean havaRepeatName(LabelBaseInfoRep labelBaseInfoRep) {
        int count = labelBaseInfoRepMapper.haveRepeatName(labelBaseInfoRep);
        return count > 0;
    }
}
