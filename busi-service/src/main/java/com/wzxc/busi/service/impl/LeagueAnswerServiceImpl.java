package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.LeagueAnswerMapper;
import com.wzxc.busi.vo.LeagueAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueAnswerService;

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
public class LeagueAnswerServiceImpl extends ServiceImpl<LeagueAnswerMapper, LeagueAnswer> implements ILeagueAnswerService {

    @Autowired
    private LeagueAnswerMapper leagueAnswerMapper;

    @Override
    public List<LeagueAnswer> selectLeagueAnswerList(LeagueAnswer leagueAnswer) {
        return leagueAnswerMapper.selectLeagueAnswerList(leagueAnswer);
    }

    @Override
    public int insertLeagueAnswer(LeagueAnswer leagueAnswer) {
        return leagueAnswerMapper.insertLeagueAnswer(leagueAnswer);
    }

    @Override
    public int insertWithDistinct(LeagueAnswer leagueAnswer) {
        return leagueAnswerMapper.insertWithDistinct(leagueAnswer);
    }

    @Override
    public int updateLeagueAnswer(LeagueAnswer leagueAnswer) {
        return leagueAnswerMapper.updateLeagueAnswer(leagueAnswer);
    }



}