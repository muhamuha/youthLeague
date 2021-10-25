package com.wzxc.busi.dao.ds1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueActivity;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-10-25
 */
public interface LeagueActivityMapper extends BaseMapper<LeagueActivity> {

    List<LeagueActivity> selectLeagueActivityList(LeagueActivity leagueActivity);

    int insertLeagueActivity(LeagueActivity leagueActivity);

    int updateLeagueActivity(LeagueActivity leagueActivity);
}
