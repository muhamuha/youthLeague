package com.wzxc.zzdpush.service;

import com.wzxc.zzdpush.vo.HhlZzdPushHis;

import java.util.List;

public interface IHhlZzdPushHisService {

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public HhlZzdPushHis selectHhlZzdPushHisById(String id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param hhlZzdPushHis 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<HhlZzdPushHis> selectHhlZzdPushHisList(HhlZzdPushHis hhlZzdPushHis);

    /**
     * 新增【请填写功能名称】
     *
     * @param hhlZzdPushHis 【请填写功能名称】
     * @return 结果
     */
    public int insertHhlZzdPushHis(HhlZzdPushHis hhlZzdPushHis);

    public int updateHhlZzdPushHis(HhlZzdPushHis hhlZzdPushHis);
}
