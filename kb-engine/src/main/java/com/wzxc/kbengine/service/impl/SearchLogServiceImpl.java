package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.kbengine.dao.ds1.SearchLogMapper;
import com.wzxc.kbengine.shiro.JwtFilter;
import com.wzxc.kbengine.vo.SearchLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.service.ISearchLogService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@Service
public class SearchLogServiceImpl implements ISearchLogService 
{
    @Autowired
    private SearchLogMapper searchLogMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SearchLog selectSearchLogById(Long id)
    {
        return searchLogMapper.selectSearchLogById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param searchLog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SearchLog> selectSearchLogList(SearchLog searchLog)
    {
        return searchLogMapper.selectSearchLogList(searchLog);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param searchLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSearchLog(SearchLog searchLog)
    {
        searchLog.setCreator(JwtFilter.getUserId());
        searchLog.setCreateTime(DateUtils.getNowDate());
        return searchLogMapper.insertSearchLog(searchLog);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param searchLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSearchLog(SearchLog searchLog)
    {
        return searchLogMapper.updateSearchLog(searchLog);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSearchLogByIds(String ids)
    {
        return searchLogMapper.deleteSearchLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSearchLogById(Long id)
    {
        return searchLogMapper.deleteSearchLogById(id);
    }
}
