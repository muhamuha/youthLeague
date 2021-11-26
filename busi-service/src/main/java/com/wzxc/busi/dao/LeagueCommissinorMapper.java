package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueCommissinor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-10-29
 */
public interface LeagueCommissinorMapper extends BaseMapper<LeagueCommissinor> {

    List<LeagueCommissinor> selectLeagueCommissinorList(LeagueCommissinor leagueCommissinor);

    int insertLeagueCommissinor(LeagueCommissinor leagueCommissinor);

    int updateLeagueCommissinor(LeagueCommissinor leagueCommissinor);

    LeagueCommissinor selectLeagueCommissinorById(@Param("id") Long id);

    boolean removeLogic(@Param("employeeCode") String employeeCode);
}
