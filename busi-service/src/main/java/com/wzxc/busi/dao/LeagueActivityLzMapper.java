package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueActivityLz;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-01
 */
public interface LeagueActivityLzMapper extends BaseMapper<LeagueActivityLz> {

    List<LeagueActivityLz> selectLeagueActivityLzList(LeagueActivityLz leagueActivityLz);

    int insertLeagueActivityLz(LeagueActivityLz leagueActivityLz);

    int insertWithDistinct(LeagueActivityLz leagueActivityLz);

    int updateLeagueActivityLz(LeagueActivityLz leagueActivityLz);

    List<Map<String, Object>> scoreTotal(@Param("id") Long id);

    List<LeagueActivityLz> myLzlist(@Param("id") Long id);

    LeagueActivityLz queryOneById(Long id);

    List<String> yearList();
}
