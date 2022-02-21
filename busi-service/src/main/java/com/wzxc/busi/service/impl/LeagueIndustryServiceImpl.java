package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.LeagueIndustryMapper;
import com.wzxc.busi.vo.LeagueIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueIndustryService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-02
 */
@Service
public class LeagueIndustryServiceImpl extends ServiceImpl<LeagueIndustryMapper, LeagueIndustry> implements ILeagueIndustryService {

    @Autowired
    private LeagueIndustryMapper leagueIndustryMapper;

    @Override
    public List<LeagueIndustry> selectLeagueIndustryList(LeagueIndustry leagueIndustry) {
        return leagueIndustryMapper.selectLeagueIndustryList(leagueIndustry);
    }

    @Override
    public int insertLeagueIndustry(LeagueIndustry leagueIndustry) {
        return leagueIndustryMapper.insertLeagueIndustry(leagueIndustry);
    }

    @Override
    public int insertWithDistinct(LeagueIndustry leagueIndustry) {
        return leagueIndustryMapper.insertWithDistinct(leagueIndustry);
    }

    @Override
    public LeagueIndustry queryOneById(Long id) {
        return leagueIndustryMapper.queryOneById(id);
    }

    @Override
    public int updateLeagueIndustry(LeagueIndustry leagueIndustry) {
        return leagueIndustryMapper.updateLeagueIndustry(leagueIndustry);
    }

}