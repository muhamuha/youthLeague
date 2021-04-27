package com.wzxc.kbengine.dao.ds1;

import java.util.List;
import com.wzxc.kbengine.vo.DxQuotaCurrent;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author huanghl
 * @date 2021-04-27
 */
public interface DxQuotaCurrentMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DxQuotaCurrent selectDxQuotaCurrentById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dxQuotaCurrent 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DxQuotaCurrent> selectDxQuotaCurrentList(DxQuotaCurrent dxQuotaCurrent);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dxQuotaCurrent 【请填写功能名称】
     * @return 结果
     */
    public int insertDxQuotaCurrent(DxQuotaCurrent dxQuotaCurrent);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dxQuotaCurrent 【请填写功能名称】
     * @return 结果
     */
    public int updateDxQuotaCurrent(DxQuotaCurrent dxQuotaCurrent);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDxQuotaCurrentById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDxQuotaCurrentByIds(String[] ids);
}
