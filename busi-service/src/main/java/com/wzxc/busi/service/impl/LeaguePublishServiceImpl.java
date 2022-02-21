package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.LeaguePublishMapper;
import com.wzxc.busi.vo.LeaguePublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeaguePublishService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2022-01-07
 */
@Service
public class LeaguePublishServiceImpl extends ServiceImpl<LeaguePublishMapper, LeaguePublish> implements ILeaguePublishService {

    @Autowired
    private LeaguePublishMapper leaguePublishMapper;

    @Override
    public List<LeaguePublish> selectLeaguePublishList(LeaguePublish leaguePublish) {
        return leaguePublishMapper.selectLeaguePublishList(leaguePublish);
    }

    @Override
    public int insertLeaguePublish(LeaguePublish leaguePublish) {
        return leaguePublishMapper.insertLeaguePublish(leaguePublish);
    }

    @Override
    public int insertWithDistinct(LeaguePublish leaguePublish) {
        return leaguePublishMapper.insertWithDistinct(leaguePublish);
    }

    @Override
    public boolean logicDelete(Long id) {
        return leaguePublishMapper.logicDelete(id);
    }

    @Override
    public int updateLeaguePublish(LeaguePublish leaguePublish) {
        return leaguePublishMapper.updateLeaguePublish(leaguePublish);
    }



}