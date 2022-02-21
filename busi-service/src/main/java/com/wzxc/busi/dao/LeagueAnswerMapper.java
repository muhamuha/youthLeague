package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueAnswer;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2022-01-07
 */
public interface LeagueAnswerMapper extends BaseMapper<LeagueAnswer> {

    List<LeagueAnswer> selectLeagueAnswerList(LeagueAnswer leagueAnswer);

    int insertLeagueAnswer(LeagueAnswer leagueAnswer);

    int insertWithDistinct(LeagueAnswer leagueAnswer);

    int updateLeagueAnswer(LeagueAnswer leagueAnswer);
}
