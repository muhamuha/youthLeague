package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueAnswer;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2022-01-07
 */
public interface ILeagueAnswerService extends IService<LeagueAnswer> {

    List<LeagueAnswer> selectLeagueAnswerList(LeagueAnswer leagueAnswer);

    int insertLeagueAnswer(LeagueAnswer leagueAnswer);

    int updateLeagueAnswer(LeagueAnswer leagueAnswer);

    int insertWithDistinct(LeagueAnswer leagueAnswer);

}
