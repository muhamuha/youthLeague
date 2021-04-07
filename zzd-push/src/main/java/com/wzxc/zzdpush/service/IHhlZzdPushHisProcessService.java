package com.wzxc.zzdpush.service;

import com.wzxc.zzdpush.vo.HhlZzdPushHisProcess;

import java.util.List;

public interface IHhlZzdPushHisProcessService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public HhlZzdPushHisProcess selectHhlZzdPushHisProcessById(Integer id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param hhlZzdPushHisProcess 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<HhlZzdPushHisProcess> selectHhlZzdPushHisProcessList(HhlZzdPushHisProcess hhlZzdPushHisProcess);

    /**
     * 新增【请填写功能名称】
     * 
     * @param hhlZzdPushHisProcess 【请填写功能名称】
     * @return 结果
     */
    public int insertHhlZzdPushHisProcess(HhlZzdPushHisProcess hhlZzdPushHisProcess);

    /**
     * 修改【请填写功能名称】
     * 
     * @param hhlZzdPushHisProcess 【请填写功能名称】
     * @return 结果
     */
    public int updateHhlZzdPushHisProcess(HhlZzdPushHisProcess hhlZzdPushHisProcess);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHhlZzdPushHisProcessByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteHhlZzdPushHisProcessById(Integer id);
}
