package com.wzxc.zzdpush.service;

import com.wzxc.zzdpush.vo.HhlZzdKpi;

import java.util.List;

public interface IHhlZzdKpiService {

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public HhlZzdKpi selectHhlZzdKpiById(Integer id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param hhlZzdKpi 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<HhlZzdKpi> selectHhlZzdKpiList(HhlZzdKpi hhlZzdKpi);

    /**
     * 新增【请填写功能名称】
     *
     * @param hhlZzdKpi 【请填写功能名称】
     * @return 结果
     */
    public int insertHhlZzdKpi(HhlZzdKpi hhlZzdKpi);

    /**
     * 修改【请填写功能名称】
     *
     * @param hhlZzdKpi 【请填写功能名称】
     * @return 结果
     */
    public int updateHhlZzdKpi(HhlZzdKpi hhlZzdKpi);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHhlZzdKpiByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteHhlZzdKpiById(Integer id);
}
