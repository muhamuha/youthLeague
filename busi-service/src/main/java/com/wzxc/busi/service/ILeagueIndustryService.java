package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueIndustry;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-02
 */
public interface ILeagueIndustryService extends IService<LeagueIndustry> {

    List<LeagueIndustry> selectLeagueIndustryList(LeagueIndustry leagueIndustry);

    int insertLeagueIndustry(LeagueIndustry leagueIndustry);

    int updateLeagueIndustry(LeagueIndustry leagueIndustry);

    int insertWithDistinct(LeagueIndustry leagueIndustry);

    LeagueIndustry queryOneById(Long id);
}
