package com.wzxc.zzdpush.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.zzdpush.dao.ds1.HhlZzdPushHisProcessMapper;
import com.wzxc.zzdpush.service.IHhlZzdPushHisProcessService;
import com.wzxc.zzdpush.vo.HhlZzdPushHisProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HhlZzdPushHisProcessServiceImpl implements IHhlZzdPushHisProcessService
{
    @Autowired
    private HhlZzdPushHisProcessMapper hhlZzdPushHisProcessMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public HhlZzdPushHisProcess selectHhlZzdPushHisProcessById(Integer id)
    {
        return hhlZzdPushHisProcessMapper.selectHhlZzdPushHisProcessById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param hhlZzdPushHisProcess 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<HhlZzdPushHisProcess> selectHhlZzdPushHisProcessList(HhlZzdPushHisProcess hhlZzdPushHisProcess)
    {
        return hhlZzdPushHisProcessMapper.selectHhlZzdPushHisProcessList(hhlZzdPushHisProcess);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param hhlZzdPushHisProcess 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertHhlZzdPushHisProcess(HhlZzdPushHisProcess hhlZzdPushHisProcess)
    {
        hhlZzdPushHisProcess.setCreateTime(DateUtils.getNowDate());
        return hhlZzdPushHisProcessMapper.insertHhlZzdPushHisProcess(hhlZzdPushHisProcess);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param hhlZzdPushHisProcess 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateHhlZzdPushHisProcess(HhlZzdPushHisProcess hhlZzdPushHisProcess)
    {
        return hhlZzdPushHisProcessMapper.updateHhlZzdPushHisProcess(hhlZzdPushHisProcess);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdPushHisProcessByIds(String ids)
    {
        return hhlZzdPushHisProcessMapper.deleteHhlZzdPushHisProcessByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdPushHisProcessById(Integer id)
    {
        return hhlZzdPushHisProcessMapper.deleteHhlZzdPushHisProcessById(id);
    }
}
