package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueElect;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-10-25
 */
public interface ILeagueElectService extends IService<LeagueElect> {

    List<LeagueElect> selectLeagueElectList(LeagueElect leagueElect);

    int insertLeagueElect(LeagueElect leagueElect);

    int updateLeagueElect(LeagueElect leagueElect);

}
