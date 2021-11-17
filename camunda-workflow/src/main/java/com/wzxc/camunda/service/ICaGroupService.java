package com.wzxc.camunda.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.camunda.persistence.entity.CaGroup;

import java.util.List;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-02
 */
public interface ICaGroupService extends IService<CaGroup> {

    List<CaGroup> selectCaGroupList(CaGroup caGroup);

    int insertCaGroup(CaGroup caGroup);

    int updateCaGroup(CaGroup caGroup);

    List<CaGroup> findAllByUserId(LambdaQueryWrapper<CaGroup> queryWrapper, String userId);
}
