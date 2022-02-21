package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.LeagueGatheringGoodMapper;
import com.wzxc.busi.vo.LeagueGatheringGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueGatheringGoodService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-07
 */
@Service
public class LeagueGatheringGoodServiceImpl extends ServiceImpl<LeagueGatheringGoodMapper, LeagueGatheringGood> implements ILeagueGatheringGoodService {

    @Autowired
    private LeagueGatheringGoodMapper leagueGatheringGoodMapper;

    @Override
    public List<LeagueGatheringGood> selectLeagueGatheringGoodList(LeagueGatheringGood leagueGatheringGood) {
        return leagueGatheringGoodMapper.selectLeagueGatheringGoodList(leagueGatheringGood);
    }

    @Override
    public int insertLeagueGatheringGood(LeagueGatheringGood leagueGatheringGood) {
        return leagueGatheringGoodMapper.insertLeagueGatheringGood(leagueGatheringGood);
    }

    @Override
    public int insertWithDistinct(LeagueGatheringGood leagueGatheringGood) {
        return leagueGatheringGoodMapper.insertWithDistinct(leagueGatheringGood);
    }

    @Override
    public int updateLeagueGatheringGood(LeagueGatheringGood leagueGatheringGood) {
        return leagueGatheringGoodMapper.updateLeagueGatheringGood(leagueGatheringGood);
    }



}