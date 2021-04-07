package com.wzxc.zzdpush.service.impl;

import com.wzxc.common.core.text.Convert;
import com.wzxc.zzdpush.dao.ds2.HhlZzdIndicatorMapper;
import com.wzxc.zzdpush.service.IHhlZzdIndicatorService;
import com.wzxc.zzdpush.vo.HhlZzdIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HhlZzdIndicatorServiceImpl implements IHhlZzdIndicatorService {

    @Autowired
    private HhlZzdIndicatorMapper hhlZzdIndicatorMapper;


    /**
     * 查询【请填写功能名称】列表
     *
     * @param HhlZzdIndicator 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<HhlZzdIndicator> selectHhlZzdIndicatorList(HhlZzdIndicator HhlZzdIndicator)
    {
        return hhlZzdIndicatorMapper.selectHhlZzdIndicatorList(HhlZzdIndicator);
    }

}
