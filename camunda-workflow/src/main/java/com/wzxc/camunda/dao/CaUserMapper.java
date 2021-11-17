package com.wzxc.camunda.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wzxc.camunda.persistence.entity.CaUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-11-02
 */
public interface CaUserMapper extends BaseMapper<CaUser> {

    List<CaUser> selectCaUserList(CaUser caUser);

    int insertCaUser(CaUser caUser);

    int updateCaUser(CaUser caUser);

    List<CaUser> findAll(@Param(Constants.WRAPPER) LambdaQueryWrapper<CaUser> queryWrapper);

    List<CaUser> findByGroupId(@Param(Constants.WRAPPER) LambdaQueryWrapper queryWrapper, @Param("groupId") String groupId);

    long aggragate();
}
