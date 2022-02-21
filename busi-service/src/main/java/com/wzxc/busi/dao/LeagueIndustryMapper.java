package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueIndustry;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-02
 */
public interface LeagueIndustryMapper extends BaseMapper<LeagueIndustry> {

    List<LeagueIndustry> selectLeagueIndustryList(LeagueIndustry leagueIndustry);

    int insertLeagueIndustry(LeagueIndustry leagueIndustry);

    int insertWithDistinct(LeagueIndustry leagueIndustry);

    int updateLeagueIndustry(LeagueIndustry leagueIndustry);

    LeagueIndustry queryOneById(Long id);
}
