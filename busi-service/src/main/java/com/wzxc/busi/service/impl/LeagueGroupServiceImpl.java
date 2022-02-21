package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.LeagueGroupMapper;
import com.wzxc.busi.vo.LeagueGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueGroupService;

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
public class LeagueGroupServiceImpl extends ServiceImpl<LeagueGroupMapper, LeagueGroup> implements ILeagueGroupService {

    @Autowired
    private LeagueGroupMapper leagueGroupMapper;

    @Override
    public List<LeagueGroup> selectLeagueGroupList(LeagueGroup leagueGroup) {
        return leagueGroupMapper.selectLeagueGroupList(leagueGroup);
    }

    @Override
    public int insertLeagueGroup(LeagueGroup leagueGroup) {
        return leagueGroupMapper.insertLeagueGroup(leagueGroup);
    }

    @Override
    public int insertWithDistinct(LeagueGroup leagueGroup) {
        return leagueGroupMapper.insertWithDistinct(leagueGroup);
    }

    @Override
    public LeagueGroup queryOneById(Long id) {
        return leagueGroupMapper.queryOneById(id);
    }

    @Override
    public int updateLeagueGroup(LeagueGroup leagueGroup) {
        return leagueGroupMapper.updateLeagueGroup(leagueGroup);
    }



}