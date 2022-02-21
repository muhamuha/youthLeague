package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueGoodman;
import com.wzxc.busi.vo.LeagueGoodmanRecommend;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-07
 */
public interface LeagueGoodmanMapper extends BaseMapper<LeagueGoodman> {

    List<LeagueGoodman> selectLeagueGoodmanList(LeagueGoodman leagueGoodman);

    int insertLeagueGoodman(LeagueGoodman leagueGoodman);

    int insertWithDistinct(LeagueGoodman leagueGoodman);

    int updateLeagueGoodman(LeagueGoodman leagueGoodman);

    LeagueGoodman queryOneById(Long id);

    List<LeagueGoodmanRecommend> queryRecommendList(LeagueGoodmanRecommend leagueGoodmanRecommend);
}
