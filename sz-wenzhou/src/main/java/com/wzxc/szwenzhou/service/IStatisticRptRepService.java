package com.wzxc.szwenzhou.service;

import com.wzxc.szwenzhou.vo.StatisticRptRep;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author mengs
 * @date 2021-04-13
 */
public interface IStatisticRptRepService
{
    /**
     * 查询【请填写功能名称】列表
     * 
     * @param statisticRptRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<StatisticRptRep> selectStatisticRptRepList(StatisticRptRep statisticRptRep);

}
