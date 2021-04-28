package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.kbengine.shiro.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.DxQuotaCurrentMapper;
import com.wzxc.kbengine.vo.DxQuotaCurrent;
import com.wzxc.kbengine.service.IDxQuotaCurrentService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-04-27
 */
@Service
public class DxQuotaCurrentServiceImpl implements IDxQuotaCurrentService 
{
    @Autowired
    private DxQuotaCurrentMapper dxQuotaCurrentMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public DxQuotaCurrent selectDxQuotaCurrentById(Long id)
    {
        return dxQuotaCurrentMapper.selectDxQuotaCurrentById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dxQuotaCurrent 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DxQuotaCurrent> selectDxQuotaCurrentList(DxQuotaCurrent dxQuotaCurrent)
    {
        return dxQuotaCurrentMapper.selectDxQuotaCurrentList(dxQuotaCurrent);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param dxQuotaCurrent 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDxQuotaCurrent(DxQuotaCurrent dxQuotaCurrent)
    {
        dxQuotaCurrent.setCreator(JwtFilter.getUserId());
        dxQuotaCurrent.setCreateTime(DateUtils.getNowDate());
        dxQuotaCurrent.setUpdator(JwtFilter.getUserId());
        dxQuotaCurrent.setUpdateTime(DateUtils.getNowDate());
        return dxQuotaCurrentMapper.insertDxQuotaCurrent(dxQuotaCurrent);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param dxQuotaCurrent 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDxQuotaCurrent(DxQuotaCurrent dxQuotaCurrent)
    {
        dxQuotaCurrent.setUpdator(JwtFilter.getUserId());
        dxQuotaCurrent.setUpdateTime(DateUtils.getNowDate());
        return dxQuotaCurrentMapper.updateDxQuotaCurrent(dxQuotaCurrent);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDxQuotaCurrentByIds(String ids)
    {
        return dxQuotaCurrentMapper.deleteDxQuotaCurrentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteDxQuotaCurrentById(Long id)
    {
        return dxQuotaCurrentMapper.deleteDxQuotaCurrentById(id);
    }

    @Override
    public int insertBatch(InsertBatchCommon insertBatchCommon) {
        return dxQuotaCurrentMapper.insertBatch(insertBatchCommon);
    }
}
