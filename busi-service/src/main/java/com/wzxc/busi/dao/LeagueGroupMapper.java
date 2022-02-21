package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueGroup;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-02
 */
public interface LeagueGroupMapper extends BaseMapper<LeagueGroup> {

    List<LeagueGroup> selectLeagueGroupList(LeagueGroup leagueGroup);

    int insertLeagueGroup(LeagueGroup leagueGroup);

    int insertWithDistinct(LeagueGroup leagueGroup);

    int updateLeagueGroup(LeagueGroup leagueGroup);

    LeagueGroup queryOneById(Long id);
}
