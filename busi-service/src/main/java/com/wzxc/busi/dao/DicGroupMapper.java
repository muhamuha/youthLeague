package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.DicGroup;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-30
 */
public interface DicGroupMapper extends BaseMapper<DicGroup> {

    List<DicGroup> selectDicGroupList(DicGroup dicGroup);

    int insertDicGroup(DicGroup dicGroup);

    int updateDicGroup(DicGroup dicGroup);
}
