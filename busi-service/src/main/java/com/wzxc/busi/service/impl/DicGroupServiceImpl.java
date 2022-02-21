package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.busi.dao.DicGroupMapper;
import com.wzxc.busi.vo.DicGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.IDicGroupService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-30
 */
@Service
public class DicGroupServiceImpl extends ServiceImpl<DicGroupMapper, DicGroup> implements IDicGroupService {

    @Autowired
    private DicGroupMapper dicGroupMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dicGroup 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DicGroup> selectDicGroupList(DicGroup dicGroup) {
        return dicGroupMapper.selectDicGroupList(dicGroup);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param dicGroup 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDicGroup(DicGroup dicGroup) {
        return dicGroupMapper.insertDicGroup(dicGroup);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param dicGroup 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDicGroup(DicGroup dicGroup) {
        return dicGroupMapper.updateDicGroup(dicGroup);
    }



}