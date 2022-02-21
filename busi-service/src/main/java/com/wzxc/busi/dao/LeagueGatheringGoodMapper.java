package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueGatheringGood;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-07
 */
public interface LeagueGatheringGoodMapper extends BaseMapper<LeagueGatheringGood> {

    List<LeagueGatheringGood> selectLeagueGatheringGoodList(LeagueGatheringGood leagueGatheringGood);

    int insertLeagueGatheringGood(LeagueGatheringGood leagueGatheringGood);

    int insertWithDistinct(LeagueGatheringGood leagueGatheringGood);

    int updateLeagueGatheringGood(LeagueGatheringGood leagueGatheringGood);
}
