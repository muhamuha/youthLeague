package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueActRegister;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-24
 */
public interface ILeagueActRegisterService extends IService<LeagueActRegister> {

    List<LeagueActRegister> selectLeagueActRegisterList(LeagueActRegister leagueActRegister);

    int insertLeagueActRegister(LeagueActRegister leagueActRegister);

    int updateLeagueActRegister(LeagueActRegister leagueActRegister);

}
