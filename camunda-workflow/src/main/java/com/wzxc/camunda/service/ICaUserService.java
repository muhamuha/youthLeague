package com.wzxc.camunda.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.camunda.persistence.entity.CaUser;

import java.util.List;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-02
 */
public interface ICaUserService extends IService<CaUser> {

    List<CaUser> selectCaUserList(CaUser caUser);

    int insertCaUser(CaUser caUser);

    int updateCaUser(CaUser caUser);

    List<CaUser> findAll(LambdaQueryWrapper<CaUser> queryWrapper);

    List<CaUser> findByGroupId(LambdaQueryWrapper queryWrapper, String groupId);

    long aggragate();
}
