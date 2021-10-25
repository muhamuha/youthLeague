package com.wzxc.busi.dao.ds1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueElect;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-10-25
 */
public interface LeagueElectMapper extends BaseMapper<LeagueElect> {

    List<LeagueElect> selectLeagueElectList(LeagueElect leagueElect);

    int insertLeagueElect(LeagueElect leagueElect);

    int updateLeagueElect(LeagueElect leagueElect);
}
