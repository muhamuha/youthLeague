package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.configcommon.shiro.JwtFilter;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.TkPolicyRepMapper;
import com.wzxc.kbengine.vo.TkPolicyRep;
import com.wzxc.kbengine.service.ITkPolicyRepService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Service
public class TkPolicyRepServiceImpl implements ITkPolicyRepService 
{
    @Autowired
    private TkPolicyRepMapper tkPolicyRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TkPolicyRep selectTkPolicyRepById(Long id)
    {
        return tkPolicyRepMapper.selectTkPolicyRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tkPolicyRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TkPolicyRep> selectTkPolicyRepList(TkPolicyRep tkPolicyRep)
    {
        return tkPolicyRepMapper.selectTkPolicyRepList(tkPolicyRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tkPolicyRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTkPolicyRep(TkPolicyRep tkPolicyRep)
    {
        tkPolicyRep.setCreator(JwtFilter.getUserId());
        tkPolicyRep.setUpdator(JwtFilter.getUserId());
        tkPolicyRep.setCreateTime(DateUtils.getNowDate());
        tkPolicyRep.setUpdateTime(DateUtils.getNowDate());
        return tkPolicyRepMapper.insertTkPolicyRep(tkPolicyRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tkPolicyRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTkPolicyRep(TkPolicyRep tkPolicyRep)
    {
        tkPolicyRep.setUpdateTime(DateUtils.getNowDate());
        return tkPolicyRepMapper.updateTkPolicyRep(tkPolicyRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTkPolicyRepByIds(String ids)
    {
        return tkPolicyRepMapper.deleteTkPolicyRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteTkPolicyRepById(TkPolicyRep tkPolicyRep)
    {
        return tkPolicyRepMapper.deleteTkPolicyRepById(tkPolicyRep);
    }

    /**
     * 通过政策id删除关联
     * @param id
     * @return
     */
    @Override
    public int deleteTkPolicyRepByTkId(Long id) {
        return tkPolicyRepMapper.deleteTkPolicyRepByTkId(id);
    }

    /**
     * 删除任务与政策之间的关联
     * @param tkBaseInfoId
     * @param policyBaseInfoIds
     * @return
     */
    @Override
    public int deleteTkPolicyRepByPolicyId(Long tkBaseInfoId, List<Object> policyBaseInfoIds) {
        return tkPolicyRepMapper.deleteTkPolicyRepByPolicyId(tkBaseInfoId, policyBaseInfoIds);
    }
}
