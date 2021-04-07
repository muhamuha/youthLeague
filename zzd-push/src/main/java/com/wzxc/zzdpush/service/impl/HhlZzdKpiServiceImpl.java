package com.wzxc.zzdpush.service.impl;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.zzdpush.dao.ds1.HhlZzdKpiMapper;
import com.wzxc.zzdpush.service.IHhlZzdKpiService;
import com.wzxc.zzdpush.vo.HhlZzdKpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HhlZzdKpiServiceImpl implements IHhlZzdKpiService {

    @Autowired
    private HhlZzdKpiMapper hhlZzdKpiMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public HhlZzdKpi selectHhlZzdKpiById(Integer id)
    {
        return hhlZzdKpiMapper.selectHhlZzdKpiById(id);
    }


    /**
     * 查询【请填写功能名称】列表
     *
     * @param hhlZzdKpi 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<HhlZzdKpi> selectHhlZzdKpiList(HhlZzdKpi hhlZzdKpi)
    {
        return hhlZzdKpiMapper.selectHhlZzdKpiList(hhlZzdKpi);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param hhlZzdKpi 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertHhlZzdKpi(HhlZzdKpi hhlZzdKpi)
    {
        hhlZzdKpi.setCreateTime(DateUtils.getNowDate());
        return hhlZzdKpiMapper.insertHhlZzdKpi(hhlZzdKpi);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param hhlZzdKpi 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateHhlZzdKpi(HhlZzdKpi hhlZzdKpi)
    {
        return hhlZzdKpiMapper.updateHhlZzdKpi(hhlZzdKpi);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdKpiByIds(String ids)
    {
        return hhlZzdKpiMapper.deleteHhlZzdKpiByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdKpiById(Integer id)
    {
        return hhlZzdKpiMapper.deleteHhlZzdKpiById(id);
    }
}
