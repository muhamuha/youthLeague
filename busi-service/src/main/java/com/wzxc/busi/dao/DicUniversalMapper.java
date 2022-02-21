package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.DicUniversal;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-01
 */
public interface DicUniversalMapper extends BaseMapper<DicUniversal> {

    List<DicUniversal> selectDicUniversalList(DicUniversal dicUniversal);

    int insertDicUniversal(DicUniversal dicUniversal);

    int insertWithDistinct(DicUniversal dicUniversal);

    int updateDicUniversal(DicUniversal dicUniversal);
}
