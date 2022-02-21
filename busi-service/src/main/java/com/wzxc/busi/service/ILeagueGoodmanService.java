package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueGoodman;
import com.wzxc.busi.vo.LeagueGoodmanRecommend;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-07
 */
public interface ILeagueGoodmanService extends IService<LeagueGoodman> {

    List<LeagueGoodman> selectLeagueGoodmanList(LeagueGoodman leagueGoodman);

    int insertLeagueGoodman(LeagueGoodman leagueGoodman);

    int updateLeagueGoodman(LeagueGoodman leagueGoodman);

    int insertWithDistinct(LeagueGoodman leagueGoodman);

    LeagueGoodman queryOneById(Long id);

    List<LeagueGoodmanRecommend> queryRecommendList(LeagueGoodmanRecommend leagueGoodmanRecommend);
}
