package com.wzxc.zzdpush.dao.ds2;

import com.wzxc.zzdpush.vo.HhlZzdIndicator;

import java.util.List;

public interface HhlZzdIndicatorMapper {

    /**
     * 获取推送模板需要的所有参数
     */
    public List<HhlZzdIndicator> selectHhlZzdIndicatorList(HhlZzdIndicator HhlZzdIndicator);

}
