package com.wzxc.busi.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueActivityLz;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-01
 */
public interface ILeagueActivityLzService extends IService<LeagueActivityLz> {

    List<LeagueActivityLz> selectLeagueActivityLzList(LeagueActivityLz leagueActivityLz);

    int insertLeagueActivityLz(LeagueActivityLz leagueActivityLz);

    int updateLeagueActivityLz(LeagueActivityLz leagueActivityLz);

    int insertWithDistinct(LeagueActivityLz leagueActivityLz);

    List<Map<String, Object>> scoreTotal(Long id);

    LeagueActivityLz queryOneById(Long id);

    List<String> yearList();
}
