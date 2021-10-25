package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.ds1.LeagueActivityMapper;
import com.wzxc.busi.vo.LeagueActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueActivityService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-10-25
 */
@Service
public class LeagueActivityServiceImpl extends ServiceImpl<LeagueActivityMapper, LeagueActivity> implements ILeagueActivityService {

    @Autowired
    private LeagueActivityMapper leagueActivityMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param leagueActivity 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<LeagueActivity> selectLeagueActivityList(LeagueActivity leagueActivity) {
        return leagueActivityMapper.selectLeagueActivityList(leagueActivity);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param leagueActivity 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertLeagueActivity(LeagueActivity leagueActivity) {
        return leagueActivityMapper.insertLeagueActivity(leagueActivity);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param leagueActivity 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateLeagueActivity(LeagueActivity leagueActivity) {
        return leagueActivityMapper.updateLeagueActivity(leagueActivity);
    }



}