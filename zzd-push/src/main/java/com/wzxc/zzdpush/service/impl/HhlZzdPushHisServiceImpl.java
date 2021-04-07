package com.wzxc.zzdpush.service.impl;

import com.wzxc.common.utils.DateUtils;
import com.wzxc.zzdpush.dao.ds1.HhlZzdPushHisMapper;
import com.wzxc.zzdpush.service.IHhlZzdPushHisService;
import com.wzxc.zzdpush.vo.HhlZzdPushHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HhlZzdPushHisServiceImpl implements IHhlZzdPushHisService {

    @Autowired
    private HhlZzdPushHisMapper hhlZzdPushHisMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public HhlZzdPushHis selectHhlZzdPushHisById(String id)
    {
        return hhlZzdPushHisMapper.selectHhlZzdPushHisById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param hhlZzdPushHis 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<HhlZzdPushHis> selectHhlZzdPushHisList(HhlZzdPushHis hhlZzdPushHis)
    {
        return hhlZzdPushHisMapper.selectHhlZzdPushHisList(hhlZzdPushHis);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param hhlZzdPushHis 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertHhlZzdPushHis(HhlZzdPushHis hhlZzdPushHis)
    {
        hhlZzdPushHis.setCreateTime(DateUtils.getNowDate());
        return hhlZzdPushHisMapper.insertHhlZzdPushHis(hhlZzdPushHis);
    }

    /**
     * 根据messageId获取日志
     * @param bizMsgId
     * @return
     */
    public HhlZzdPushHis selectHhlZzdPushHisByMessageId(String bizMsgId) {
        return hhlZzdPushHisMapper.selectHhlZzdPushHisByMessageId(bizMsgId);
    }

    public int updateHhlZzdPushHis(HhlZzdPushHis hhlZzdPushHis){
        return hhlZzdPushHisMapper.updateHhlZzdPushHis(hhlZzdPushHis);
    }
}
