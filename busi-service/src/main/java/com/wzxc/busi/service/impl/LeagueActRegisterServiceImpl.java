package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.busi.dao.LeagueActRegisterMapper;
import com.wzxc.busi.vo.LeagueActRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.ILeagueActRegisterService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-24
 */
@Service
public class LeagueActRegisterServiceImpl extends ServiceImpl<LeagueActRegisterMapper, LeagueActRegister> implements ILeagueActRegisterService {

    @Autowired
    private LeagueActRegisterMapper leagueActRegisterMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param leagueActRegister 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<LeagueActRegister> selectLeagueActRegisterList(LeagueActRegister leagueActRegister) {
        return leagueActRegisterMapper.selectLeagueActRegisterList(leagueActRegister);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param leagueActRegister 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertLeagueActRegister(LeagueActRegister leagueActRegister) {
        return leagueActRegisterMapper.insertLeagueActRegister(leagueActRegister);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param leagueActRegister 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateLeagueActRegister(LeagueActRegister leagueActRegister) {
        return leagueActRegisterMapper.updateLeagueActRegister(leagueActRegister);
    }



}