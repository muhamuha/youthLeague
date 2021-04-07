package com.wzxc.kbengine.dao.ds1;

import java.util.List;
import com.wzxc.kbengine.vo.GroupBaseInfoRep;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author huanghl
 * @date 2021-03-11
 */
public interface GroupBaseInfoRepMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public GroupBaseInfoRep selectGroupBaseInfoRepById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param groupBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<GroupBaseInfoRep> selectGroupBaseInfoRepList(GroupBaseInfoRep groupBaseInfoRep);

    /**
     * 新增【请填写功能名称】
     * 
     * @param groupBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int insertGroupBaseInfoRep(GroupBaseInfoRep groupBaseInfoRep);

    /**
     * 修改【请填写功能名称】
     * 
     * @param groupBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    public int updateGroupBaseInfoRep(GroupBaseInfoRep groupBaseInfoRep);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】
     * @return 结果
     */
    public int deleteGroupBaseInfoRepById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupBaseInfoRepByIds(String[] ids);

    /**
     * 是否存在重复名称的标签
     * @param groupBaseInfoRep
     * @return
     */
    public int haveRepeatName(GroupBaseInfoRep groupBaseInfoRep);
}
