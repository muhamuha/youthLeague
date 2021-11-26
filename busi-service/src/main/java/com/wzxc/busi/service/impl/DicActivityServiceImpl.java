package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.busi.dao.DicActivityMapper;
import com.wzxc.busi.vo.DicActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.IDicActivityService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-23
 */
@Service
public class DicActivityServiceImpl extends ServiceImpl<DicActivityMapper, DicActivity> implements IDicActivityService {

    @Autowired
    private DicActivityMapper dicActivityMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dicActivity 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DicActivity> selectDicActivityList(DicActivity dicActivity) {
        return dicActivityMapper.selectDicActivityList(dicActivity);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param dicActivity 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDicActivity(DicActivity dicActivity) {
        return dicActivityMapper.insertDicActivity(dicActivity);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param dicActivity 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDicActivity(DicActivity dicActivity) {
        return dicActivityMapper.updateDicActivity(dicActivity);
    }



}