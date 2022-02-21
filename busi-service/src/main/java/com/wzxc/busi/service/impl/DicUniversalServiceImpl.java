package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.DicUniversalMapper;
import com.wzxc.busi.vo.DicUniversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.IDicUniversalService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-01
 */
@Service
public class DicUniversalServiceImpl extends ServiceImpl<DicUniversalMapper, DicUniversal> implements IDicUniversalService {

    @Autowired
    private DicUniversalMapper dicUniversalMapper;

    @Override
    public List<DicUniversal> selectDicUniversalList(DicUniversal dicUniversal) {
        return dicUniversalMapper.selectDicUniversalList(dicUniversal);
    }

    @Override
    public int insertDicUniversal(DicUniversal dicUniversal) {
        return dicUniversalMapper.insertDicUniversal(dicUniversal);
    }

    @Override
    public int insertWithDistinct(DicUniversal dicUniversal) {
        return dicUniversalMapper.insertWithDistinct(dicUniversal);
    }

    @Override
    public int updateDicUniversal(DicUniversal dicUniversal) {
        return dicUniversalMapper.updateDicUniversal(dicUniversal);
    }



}