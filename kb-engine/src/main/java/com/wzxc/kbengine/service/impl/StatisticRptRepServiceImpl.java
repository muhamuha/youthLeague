package com.wzxc.kbengine.service.impl;

import com.wzxc.kbengine.dao.ds1.StatisticRptRepMapper;
import com.wzxc.kbengine.service.IStatisticRptRepService;
import com.wzxc.kbengine.vo.StatisticRptRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author mengs
 * @date 2021-04-13
 */
@Service
public class StatisticRptRepServiceImpl implements IStatisticRptRepService
{
    @Autowired
    private StatisticRptRepMapper statisticRptRepMapper;

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param statisticRptRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<StatisticRptRep> selectStatisticRptRepList(StatisticRptRep statisticRptRep)
    {
        if(statisticRptRep.getOrgCode()!=null&&statisticRptRep.getOrgCode()!=""){
            String orgCode = "'"+statisticRptRep.getOrgCode().replace(",","','")+"'";
            statisticRptRep.setOrgCode(orgCode);
        }
        if(statisticRptRep.getTaskLevel()!=null&&statisticRptRep.getTaskLevel()!=""&&statisticRptRep.getTaskLevel().contains("5")){
            return statisticRptRepMapper.selectStatisticRptRepListForTaskLevel(statisticRptRep);
        }else{
            return statisticRptRepMapper.selectStatisticRptRepList(statisticRptRep);
        }
    }

}
