package com.wzxc.zzdpush.service;

import com.wzxc.zzdpush.vo.HhlZzdPushTemplate;
import com.wzxc.zzdpush.vo.HhlZzdPushTemplateParent;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author muhamuha
 * @date 2021-02-02
 */
public interface IHhlZzdPushTemplateService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public HhlZzdPushTemplate selectHhlZzdPushTemplateById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param hhlZzdPushTemplate 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Map<String, Object>>  selectHhlZzdPushTemplateList(HhlZzdPushTemplate hhlZzdPushTemplate);

    /**
     * 新增【请填写功能名称】
     * 
     * @param hhlZzdPushTemplate 【请填写功能名称】
     * @return 结果
     */
    public int insertHhlZzdPushTemplate(HhlZzdPushTemplate hhlZzdPushTemplate);

    /**
     * 修改【请填写功能名称】
     * 
     * @param hhlZzdPushTemplate 【请填写功能名称】
     * @return 结果
     */
    public int updateHhlZzdPushTemplate(HhlZzdPushTemplate hhlZzdPushTemplate);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHhlZzdPushTemplateByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteHhlZzdPushTemplateById(String id);

}
