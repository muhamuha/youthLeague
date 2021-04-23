package com.wzxc.kbengine.service.impl;

import java.util.List;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.uuid.IdUtils;
import com.wzxc.configcommon.shiro.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wzxc.kbengine.dao.ds1.GroupBaseInfoRepMapper;
import com.wzxc.kbengine.vo.GroupBaseInfoRep;
import com.wzxc.kbengine.service.IGroupBaseInfoRepService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author huanghl
 * @date 2021-03-11
 */
@Service
public class GroupBaseInfoRepServiceImpl implements IGroupBaseInfoRepService 
{
    @Autowired
    private GroupBaseInfoRepMapper groupBaseInfoRepMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public GroupBaseInfoRep selectGroupBaseInfoRepById(Long id)
    {
        return groupBaseInfoRepMapper.selectGroupBaseInfoRepById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param groupBaseInfoRep 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<GroupBaseInfoRep> selectGroupBaseInfoRepList(GroupBaseInfoRep groupBaseInfoRep)
    {
        return groupBaseInfoRepMapper.selectGroupBaseInfoRepList(groupBaseInfoRep);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param groupBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertGroupBaseInfoRep(GroupBaseInfoRep groupBaseInfoRep)
    {
        groupBaseInfoRep.setCreator(JwtFilter.getUserId());
        groupBaseInfoRep.setUpdator(JwtFilter.getUserId());
        groupBaseInfoRep.setGroupId("wz_" + IdUtils.getSnowflakeId());
        groupBaseInfoRep.setCreateTime(DateUtils.getNowDate());
        groupBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        return groupBaseInfoRepMapper.insertGroupBaseInfoRep(groupBaseInfoRep);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param groupBaseInfoRep 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateGroupBaseInfoRep(GroupBaseInfoRep groupBaseInfoRep)
    {
        groupBaseInfoRep.setUpdator(JwtFilter.getUserId());
        groupBaseInfoRep.setUpdateTime(DateUtils.getNowDate());
        return groupBaseInfoRepMapper.updateGroupBaseInfoRep(groupBaseInfoRep);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGroupBaseInfoRepByIds(String ids)
    {
        return groupBaseInfoRepMapper.deleteGroupBaseInfoRepByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteGroupBaseInfoRepById(Long id)
    {
        return groupBaseInfoRepMapper.deleteGroupBaseInfoRepById(id);
    }

    @Override
    public boolean havaRepeatName(GroupBaseInfoRep groupBaseInfoRep) {
        groupBaseInfoRep.setGroupName(groupBaseInfoRep.getGroupName().trim());
        int count = groupBaseInfoRepMapper.haveRepeatName(groupBaseInfoRep);
        return count > 0;
    }
}
