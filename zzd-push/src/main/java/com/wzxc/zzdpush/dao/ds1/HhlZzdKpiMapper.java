package com.wzxc.zzdpush.dao.ds1;

import java.util.List;
import com.wzxc.zzdpush.vo.HhlZzdKpi;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface HhlZzdKpiMapper 
{
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteHhlZzdKpiById(Integer id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHhlZzdKpiByIds(String[] ids);
}
