package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueGroup;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-02
 */
public interface ILeagueGroupService extends IService<LeagueGroup> {

    List<LeagueGroup> selectLeagueGroupList(LeagueGroup leagueGroup);

    int insertLeagueGroup(LeagueGroup leagueGroup);

    int updateLeagueGroup(LeagueGroup leagueGroup);

    int insertWithDistinct(LeagueGroup leagueGroup);

    LeagueGroup queryOneById(Long id);
}
