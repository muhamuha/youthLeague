package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.kbengine.shiro.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.DxQuotaHistoryMapper;
import com.wzxc.kbengine.vo.DxQuotaHistory;
import com.wzxc.kbengine.service.IDxQuotaHistoryService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-04-27
 */
@Service
public class DxQuotaHistoryServiceImpl implements IDxQuotaHistoryService 
{
    @Autowired
    private DxQuotaHistoryMapper dxQuotaHistoryMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public DxQuotaHistory selectDxQuotaHistoryById(Long id)
    {
        return dxQuotaHistoryMapper.selectDxQuotaHistoryById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dxQuotaHistory 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DxQuotaHistory> selectDxQuotaHistoryList(DxQuotaHistory dxQuotaHistory)
    {
        return dxQuotaHistoryMapper.selectDxQuotaHistoryList(dxQuotaHistory);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param dxQuotaHistory 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDxQuotaHistory(DxQuotaHistory dxQuotaHistory)
    {
        dxQuotaHistory.setCreator(JwtFilter.getUserId());
        dxQuotaHistory.setCreateTime(DateUtils.getNowDate());
        return dxQuotaHistoryMapper.insertDxQuotaHistory(dxQuotaHistory);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param dxQuotaHistory 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDxQuotaHistory(DxQuotaHistory dxQuotaHistory)
    {
        return dxQuotaHistoryMapper.updateDxQuotaHistory(dxQuotaHistory);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDxQuotaHistoryByIds(String ids)
    {
        return dxQuotaHistoryMapper.deleteDxQuotaHistoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteDxQuotaHistoryById(Long id)
    {
        return dxQuotaHistoryMapper.deleteDxQuotaHistoryById(id);
    }

    @Override
    public int insertBatch(InsertBatchCommon insertBatchCommon) {
        return dxQuotaHistoryMapper.insertBatch(insertBatchCommon);
    }
}
