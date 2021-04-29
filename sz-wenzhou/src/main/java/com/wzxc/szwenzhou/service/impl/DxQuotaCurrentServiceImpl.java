package com.wzxc.szwenzhou.service.impl;

import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;

import com.wzxc.szwenzhou.dao.ds1.DxQuotaCurrentMapper;
import com.wzxc.szwenzhou.service.IDxQuotaCurrentService;
import com.wzxc.szwenzhou.shiro.JwtFilter;
import com.wzxc.szwenzhou.vo.DxQuotaCurrent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<DxQuotaCurrent> selectDxQuotaCurrentByName(String quotaName)
    {
        return dxQuotaCurrentMapper.selectDxQuotaCurrentByName(quotaName);
    }
}
