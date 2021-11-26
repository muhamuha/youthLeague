package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.busi.dao.LeagueElectMapper;
import com.wzxc.busi.vo.LeagueElect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueElectService;

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
public class LeagueElectServiceImpl extends ServiceImpl<LeagueElectMapper, LeagueElect> implements ILeagueElectService {

    @Autowired
    private LeagueElectMapper leagueElectMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param leagueElect 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<LeagueElect> selectLeagueElectList(LeagueElect leagueElect) {
        return leagueElectMapper.selectLeagueElectList(leagueElect);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param leagueElect 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertLeagueElect(LeagueElect leagueElect) {
        return leagueElectMapper.insertLeagueElect(leagueElect);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param leagueElect 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateLeagueElect(LeagueElect leagueElect) {
        return leagueElectMapper.updateLeagueElect(leagueElect);
    }



}