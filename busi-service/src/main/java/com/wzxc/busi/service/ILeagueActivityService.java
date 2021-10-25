package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueActivity;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-10-25
 */
public interface ILeagueActivityService extends IService<LeagueActivity> {

    List<LeagueActivity> selectLeagueActivityList(LeagueActivity leagueActivity);

    int insertLeagueActivity(LeagueActivity leagueActivity);

    int updateLeagueActivity(LeagueActivity leagueActivity);

}
