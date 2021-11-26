package com.wzxc.busi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.busi.dao.ds1.DicIndustryMapper;
import com.wzxc.busi.vo.DicIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.busi.service.IDicIndustryService;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-26
 */
@Service
public class DicIndustryServiceImpl extends ServiceImpl<DicIndustryMapper, DicIndustry> implements IDicIndustryService {

    @Autowired
    private DicIndustryMapper dicIndustryMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dicIndustry 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DicIndustry> selectDicIndustryList(DicIndustry dicIndustry) {
        return dicIndustryMapper.selectDicIndustryList(dicIndustry);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param dicIndustry 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDicIndustry(DicIndustry dicIndustry) {
        return dicIndustryMapper.insertDicIndustry(dicIndustry);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param dicIndustry 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDicIndustry(DicIndustry dicIndustry) {
        return dicIndustryMapper.updateDicIndustry(dicIndustry);
    }



}