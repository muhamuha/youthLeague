package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueActivity;
import com.wzxc.busi.vo.LeagueCommissinor;
import org.apache.ibatis.annotations.Param;

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

    List<LeagueActivity> selectRegisterLeagueActivityList(@Param("commissinor") LeagueCommissinor leagueCommissinor);
}
