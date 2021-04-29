package com.wzxc.szwenzhou.service;

import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.szwenzhou.vo.DxQuotaCurrent;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author huanghl
 * @date 2021-04-27
 */
public interface IDxQuotaCurrentService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public List<DxQuotaCurrent> selectDxQuotaCurrentByName(String quotaName);
}
