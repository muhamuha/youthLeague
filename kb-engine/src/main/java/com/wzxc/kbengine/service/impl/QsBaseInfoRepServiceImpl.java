package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.QsBaseInfoRepMapper;
import com.wzxc.kbengine.vo.QsBaseInfoRep;
import com.wzxc.kbengine.service.IQsBaseInfoRepService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@Service
public class QsBaseInfoRepServiceImpl implements IQsBaseInfoRepService 
{
    @Autowired
    private QsBaseInfoRepMapper qsBaseInfoRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public QsBaseInfoRep selectQsBaseInfoRepById(Long id)
    {
        return qsBaseInfoRepMapper.selectQsBaseInfoRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param qsBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<QsBaseInfoRep> selectQsBaseInfoRepList(QsBaseInfoRep qsBaseInfoRep)
    {
        return qsBaseInfoRepMapper.selectQsBaseInfoRepList(qsBaseInfoRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param qsBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertQsBaseInfoRep(QsBaseInfoRep qsBaseInfoRep)
    {
        qsBaseInfoRep.setCreateTime(DateUtils.getNowDate());
        return qsBaseInfoRepMapper.insertQsBaseInfoRep(qsBaseInfoRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param qsBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateQsBaseInfoRep(QsBaseInfoRep qsBaseInfoRep)
    {
        qsBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        return qsBaseInfoRepMapper.updateQsBaseInfoRep(qsBaseInfoRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteQsBaseInfoRepByIds(String ids)
    {
        return qsBaseInfoRepMapper.deleteQsBaseInfoRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteQsBaseInfoRepById(Long id)
    {
        return qsBaseInfoRepMapper.deleteQsBaseInfoRepById(id);
    }
}
