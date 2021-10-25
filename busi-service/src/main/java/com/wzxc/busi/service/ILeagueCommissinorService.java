package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.LeagueCommissinor;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-10-20
 */
public interface ILeagueCommissinorService extends IService<LeagueCommissinor> {

    List<LeagueCommissinor> selectLeagueCommissinorList(LeagueCommissinor leagueCommissinor);

    int insertLeagueCommissinor(LeagueCommissinor leagueCommissinor);

    int updateLeagueCommissinor(LeagueCommissinor leagueCommissinor);

}
