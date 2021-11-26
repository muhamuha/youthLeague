package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.DicActivity;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-23
 */
public interface DicActivityMapper extends BaseMapper<DicActivity> {

    List<DicActivity> selectDicActivityList(DicActivity dicActivity);

    int insertDicActivity(DicActivity dicActivity);

    int updateDicActivity(DicActivity dicActivity);
}
