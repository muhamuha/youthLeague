package com.wzxc.kbengine.dao.ds1;

import java.util.List;

import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.kbengine.vo.QsBaseInfoRep;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author huanghl
 * @date 2021-04-23
 */
public interface QsBaseInfoRepMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public QsBaseInfoRep selectQsBaseInfoRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param qsBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<QsBaseInfoRep>

    selectQsBaseInfoRepList(QsBaseInfoRep qsBaseInfoRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param qsBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int insertQsBaseInfoRep(QsBaseInfoRep qsBaseInfoRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param qsBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int updateQsBaseInfoRep(QsBaseInfoRep qsBaseInfoRep);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteQsBaseInfoRepById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteQsBaseInfoRepByIds(String[] ids);

    int insertBatch(InsertBatchCommon insertBatchCommon);
}
