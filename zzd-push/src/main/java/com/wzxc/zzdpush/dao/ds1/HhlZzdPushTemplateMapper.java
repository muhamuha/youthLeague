package com.wzxc.zzdpush.dao.ds1;

import com.wzxc.zzdpush.vo.HhlZzdPushTemplate;
import com.wzxc.zzdpush.vo.HhlZzdPushTemplateParent;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author muhamuha
 * @date 2021-02-02
 */
public interface HhlZzdPushTemplateMapper {
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
    public List<HhlZzdPushTemplateParent> selectHhlZzdPushTemplateList(HhlZzdPushTemplate hhlZzdPushTemplate);

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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteHhlZzdPushTemplateById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHhlZzdPushTemplateByIds(String[] ids);

}
