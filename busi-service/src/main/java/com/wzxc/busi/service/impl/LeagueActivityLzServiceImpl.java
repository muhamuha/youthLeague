package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.busi.dao.LeagueActivityLzMapper;
import com.wzxc.busi.vo.LeagueActivityLz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueActivityLzService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-01
 */
@Service
public class LeagueActivityLzServiceImpl extends ServiceImpl<LeagueActivityLzMapper, LeagueActivityLz> implements ILeagueActivityLzService {

    @Autowired
    private LeagueActivityLzMapper leagueActivityLzMapper;

    @Override
    public List<LeagueActivityLz> selectLeagueActivityLzList(LeagueActivityLz leagueActivityLz) {
        return leagueActivityLzMapper.selectLeagueActivityLzList(leagueActivityLz);
    }

    @Override
    public LeagueActivityLz queryOneById(Long id) {
        return leagueActivityLzMapper.queryOneById(id);
    }

    @Override
    public List<String> yearList() {
        return leagueActivityLzMapper.yearList();
    }

    @Override
    public int insertLeagueActivityLz(LeagueActivityLz leagueActivityLz) {
        return leagueActivityLzMapper.insertLeagueActivityLz(leagueActivityLz);
    }

    @Override
    public int insertWithDistinct(LeagueActivityLz leagueActivityLz) {
        return leagueActivityLzMapper.insertWithDistinct(leagueActivityLz);
    }

    @Override
    public List<Map<String, Object>> scoreTotal(Long id) {
        return leagueActivityLzMapper.scoreTotal(id);
    }

    @Override
    public int updateLeagueActivityLz(LeagueActivityLz leagueActivityLz) {
        return leagueActivityLzMapper.updateLeagueActivityLz(leagueActivityLz);
    }

}