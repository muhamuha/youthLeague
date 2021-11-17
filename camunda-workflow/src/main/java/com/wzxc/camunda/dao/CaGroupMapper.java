package com.wzxc.camunda.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wzxc.camunda.persistence.entity.CaGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-11-02
 */
public interface CaGroupMapper extends BaseMapper<CaGroup> {

    List<CaGroup> selectCaGroupList(CaGroup caGroup);

    int insertCaGroup(CaGroup caGroup);

    int updateCaGroup(CaGroup caGroup);

    List<CaGroup> findAllByUserId(@Param(Constants.WRAPPER) LambdaQueryWrapper<CaGroup> queryWrapper, @Param("userId") String userId);
}
