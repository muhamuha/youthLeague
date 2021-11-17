package com.wzxc.camunda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.camunda.dao.CaUserMapper;
import com.wzxc.camunda.persistence.entity.CaUser;
import com.wzxc.camunda.service.ICaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-02
 */
@Service
public class CaUserServiceImpl extends ServiceImpl<CaUserMapper, CaUser> implements ICaUserService {

    @Autowired
    private CaUserMapper caUserMapper;

    @Override
    public List<CaUser> selectCaUserList(CaUser caUser) {
        return null;
    }

    @Override
    public int insertCaUser(CaUser caUser) {
        return 0;
    }

    @Override
    public int updateCaUser(CaUser caUser) {
        return 0;
    }

    public List<CaUser> findAll(LambdaQueryWrapper<CaUser> queryWrapper) {
        List<CaUser> users = caUserMapper.findAll(queryWrapper);
        return users;
    }

    public List<CaUser> findByGroupId(LambdaQueryWrapper queryWrapper, String groupId) {
        List<CaUser> users = caUserMapper.findByGroupId(queryWrapper, groupId);
        return users;
    }

    public long aggragate() {
        return caUserMapper.aggragate();
    }
}