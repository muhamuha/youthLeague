package com.wzxc.kbengine.service;

import java.util.List;
import com.wzxc.kbengine.vo.DxQuotaHistory;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author huanghl
 * @date 2021-04-27
 */
public interface IDxQuotaHistoryService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DxQuotaHistory selectDxQuotaHistoryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dxQuotaHistory 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DxQuotaHistory> selectDxQuotaHistoryList(DxQuotaHistory dxQuotaHistory);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dxQuotaHistory 【请填写功能名称】
     * @return 结果
     */
    public int insertDxQuotaHistory(DxQuotaHistory dxQuotaHistory);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dxQuotaHistory 【请填写功能名称】
     * @return 结果
     */
    public int updateDxQuotaHistory(DxQuotaHistory dxQuotaHistory);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDxQuotaHistoryByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDxQuotaHistoryById(Long id);
}
