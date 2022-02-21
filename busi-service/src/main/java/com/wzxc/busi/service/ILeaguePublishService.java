package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeaguePublish;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2022-01-07
 */
public interface ILeaguePublishService extends IService<LeaguePublish> {

    List<LeaguePublish> selectLeaguePublishList(LeaguePublish leaguePublish);

    int insertLeaguePublish(LeaguePublish leaguePublish);

    int updateLeaguePublish(LeaguePublish leaguePublish);

    int insertWithDistinct(LeaguePublish leaguePublish);

    boolean logicDelete(Long id);
}
