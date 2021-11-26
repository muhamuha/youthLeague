package com.wzxc.busi.dao.ds1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.DicIndustry;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-26
 */
public interface DicIndustryMapper extends BaseMapper<DicIndustry> {

    List<DicIndustry> selectDicIndustryList(DicIndustry dicIndustry);

    int insertDicIndustry(DicIndustry dicIndustry);

    int updateDicIndustry(DicIndustry dicIndustry);
}
