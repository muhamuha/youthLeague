package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueActRegister;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-24
 */
public interface LeagueActRegisterMapper extends BaseMapper<LeagueActRegister> {

    List<LeagueActRegister> selectLeagueActRegisterList(LeagueActRegister leagueActRegister);

    int insertLeagueActRegister(LeagueActRegister leagueActRegister);

    int updateLeagueActRegister(LeagueActRegister leagueActRegister);
}
