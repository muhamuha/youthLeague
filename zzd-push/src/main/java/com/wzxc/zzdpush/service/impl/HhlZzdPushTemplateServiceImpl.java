package com.wzxc.zzdpush.service.impl;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.MapDataUtil;
import com.wzxc.zzdpush.dao.ds1.HhlZzdPushTemplateMapper;
import com.wzxc.zzdpush.service.IHhlZzdPushTemplateService;
import com.wzxc.zzdpush.vo.HhlZzdPushTemplate;
import com.wzxc.zzdpush.vo.HhlZzdPushTemplateParent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author muhamuha
 * @date 2021-02-02
 */
@Service
@Slf4j
public class HhlZzdPushTemplateServiceImpl implements IHhlZzdPushTemplateService {

    @Autowired
    private HhlZzdPushTemplateMapper hhlZzdPushTemplateMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public HhlZzdPushTemplate selectHhlZzdPushTemplateById(String id)
    {
        return hhlZzdPushTemplateMapper.selectHhlZzdPushTemplateById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param hhlZzdPushTemplate 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Map<String, Object>> selectHhlZzdPushTemplateList(HhlZzdPushTemplate hhlZzdPushTemplate)
    {
        List<HhlZzdPushTemplateParent> hhlZzdPushTemplateParents = hhlZzdPushTemplateMapper.selectHhlZzdPushTemplateList(hhlZzdPushTemplate);
        List<Map<String, Object>> returnMap = new ArrayList<>();
        Iterator<HhlZzdPushTemplateParent> iterator = hhlZzdPushTemplateParents.iterator();
        while(iterator.hasNext()){
            HhlZzdPushTemplateParent hhlZzdPushTemplateParent = iterator.next();
            try {
                Map<String, Object> parentMap = MapDataUtil.getObjectToMap(hhlZzdPushTemplateParent);
                parentMap.remove("templateNameSearch");
                parentMap.remove("contentSearch");
                returnMap.add(parentMap);
            } catch (IllegalAccessException e) {
                log.error("发生异常：", e);
                return null;
            }
        }
        return returnMap;
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param hhlZzdPushTemplate 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertHhlZzdPushTemplate(HhlZzdPushTemplate hhlZzdPushTemplate)
    {
        return hhlZzdPushTemplateMapper.insertHhlZzdPushTemplate(hhlZzdPushTemplate);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param hhlZzdPushTemplate 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateHhlZzdPushTemplate(HhlZzdPushTemplate hhlZzdPushTemplate)
    {
        return hhlZzdPushTemplateMapper.updateHhlZzdPushTemplate(hhlZzdPushTemplate);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdPushTemplateByIds(String ids)
    {
        return hhlZzdPushTemplateMapper.deleteHhlZzdPushTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdPushTemplateById(String id)
    {
        return hhlZzdPushTemplateMapper.deleteHhlZzdPushTemplateById(id);
    }
}
