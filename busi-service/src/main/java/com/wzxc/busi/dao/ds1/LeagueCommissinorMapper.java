package com.wzxc.busi.dao.ds1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueCommissinor;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-10-20
 */
public interface LeagueCommissinorMapper extends BaseMapper<LeagueCommissinor> {

    List<LeagueCommissinor> selectLeagueCommissinorList(LeagueCommissinor leagueCommissinor);

    int insertLeagueCommissinor(LeagueCommissinor leagueCommissinor);

    int updateLeagueCommissinor(LeagueCommissinor leagueCommissinor);
}
