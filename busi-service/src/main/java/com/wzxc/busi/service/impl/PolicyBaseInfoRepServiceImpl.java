package com.wzxc.busi.service.impl;

import com.alibaba.fastjson.JSON;
import com.wzxc.busi.dao.ds1.PolicyBaseInfoRepMapper;
import com.wzxc.busi.service.IPolicyBaseInfoRepService;
import com.wzxc.busi.vo.PolicyBaseInfoRep;
import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Service
public class PolicyBaseInfoRepServiceImpl implements IPolicyBaseInfoRepService
{
    @Autowired
    private PolicyBaseInfoRepMapper policyBaseInfoRepMapper;

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param policyBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PolicyBaseInfoRep> selectPolicyBaseInfoRepList(PolicyBaseInfoRep policyBaseInfoRep)
    {
        if(policyBaseInfoRep.getIsValid() == null){
            policyBaseInfoRep.setIsValid(0);
        }
        return policyBaseInfoRepMapper.selectPolicyBaseInfoRepList(policyBaseInfoRep);
    }

}
