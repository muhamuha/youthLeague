package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueGatheringGood;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-07
 */
public interface ILeagueGatheringGoodService extends IService<LeagueGatheringGood> {

    List<LeagueGatheringGood> selectLeagueGatheringGoodList(LeagueGatheringGood leagueGatheringGood);

    int insertLeagueGatheringGood(LeagueGatheringGood leagueGatheringGood);

    int updateLeagueGatheringGood(LeagueGatheringGood leagueGatheringGood);

    int insertWithDistinct(LeagueGatheringGood leagueGatheringGood);

}
