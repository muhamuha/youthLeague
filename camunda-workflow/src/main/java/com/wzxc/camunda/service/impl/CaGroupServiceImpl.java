package com.wzxc.camunda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.camunda.dao.CaGroupMapper;
import com.wzxc.camunda.persistence.entity.CaGroup;
import com.wzxc.camunda.service.ICaGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CaGroupServiceImpl extends ServiceImpl<CaGroupMapper, CaGroup> implements ICaGroupService {

    @Autowired
    private CaGroupMapper caGroupMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param caGroup 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CaGroup> selectCaGroupList(CaGroup caGroup) {
        return caGroupMapper.selectCaGroupList(caGroup);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param caGroup 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCaGroup(CaGroup caGroup) {
        return caGroupMapper.insertCaGroup(caGroup);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param caGroup 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCaGroup(CaGroup caGroup) {
        return caGroupMapper.updateCaGroup(caGroup);
    }

    @Override
    public List<CaGroup> findAllByUserId(LambdaQueryWrapper<CaGroup> queryWrapper, String userId) {
        return caGroupMapper.findAllByUserId(queryWrapper, userId);
    }


}