package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.busi.vo.LeagueGoodmanRecommend;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.LeagueGoodmanMapper;
import com.wzxc.busi.vo.LeagueGoodman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueGoodmanService;

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
public class LeagueGoodmanServiceImpl extends ServiceImpl<LeagueGoodmanMapper, LeagueGoodman> implements ILeagueGoodmanService {

    @Autowired
    private LeagueGoodmanMapper leagueGoodmanMapper;

    @Override
    public List<LeagueGoodman> selectLeagueGoodmanList(LeagueGoodman leagueGoodman) {
        return leagueGoodmanMapper.selectLeagueGoodmanList(leagueGoodman);
    }

    @Override
    public int insertLeagueGoodman(LeagueGoodman leagueGoodman) {
        return leagueGoodmanMapper.insertLeagueGoodman(leagueGoodman);
    }

    @Override
    public int insertWithDistinct(LeagueGoodman leagueGoodman) {
        return leagueGoodmanMapper.insertWithDistinct(leagueGoodman);
    }

    @Override
    public LeagueGoodman queryOneById(Long id) {
        return leagueGoodmanMapper.queryOneById(id);
    }

    @Override
    public List<LeagueGoodmanRecommend> queryRecommendList(LeagueGoodmanRecommend leagueGoodmanRecommend) {
        return leagueGoodmanMapper.queryRecommendList(leagueGoodmanRecommend);
    }

    @Override
    public int updateLeagueGoodman(LeagueGoodman leagueGoodman) {
        return leagueGoodmanMapper.updateLeagueGoodman(leagueGoodman);
    }



}