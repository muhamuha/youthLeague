package com.wzxc.kbengine.service;

import java.util.List;
import com.wzxc.kbengine.vo.SearchLog;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author huanghl
 * @date 2021-04-23
 */
public interface ISearchLogService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SearchLog selectSearchLogById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param searchLog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SearchLog> selectSearchLogList(SearchLog searchLog);

    /**
     * 新增【请填写功能名称】
     * 
     * @param searchLog 【请填写功能名称】
     * @return 结果
     */
    public int insertSearchLog(SearchLog searchLog);

    /**
     * 修改【请填写功能名称】
     * 
     * @param searchLog 【请填写功能名称】
     * @return 结果
     */
    public int updateSearchLog(SearchLog searchLog);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSearchLogByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSearchLogById(Long id);
}
