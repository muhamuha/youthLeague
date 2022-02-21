package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeaguePublish;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2022-01-07
 */
public interface LeaguePublishMapper extends BaseMapper<LeaguePublish> {

    List<LeaguePublish> selectLeaguePublishList(LeaguePublish leaguePublish);

    int insertLeaguePublish(LeaguePublish leaguePublish);

    int insertWithDistinct(LeaguePublish leaguePublish);

    int updateLeaguePublish(LeaguePublish leaguePublish);

    boolean logicDelete(Long id);
}
