package com.wzxc.zzdpush.service.impl;

import com.wzxc.common.core.text.Convert;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.zzdpush.dao.ds1.HhlZzdPushUserMapper;
import com.wzxc.zzdpush.service.IHhlZzdPushUserService;
import com.wzxc.zzdpush.vo.HhlZzdPushUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HhlZzdPushUserServiceImpl implements IHhlZzdPushUserService {

    @Autowired
    private HhlZzdPushUserMapper hhlZzdPushUserMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public HhlZzdPushUser selectHhlZzdPushUserById(String id)
    {
        return hhlZzdPushUserMapper.selectHhlZzdPushUserById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param hhlZzdPushUser 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<HhlZzdPushUser> selectHhlZzdPushUserList(HhlZzdPushUser hhlZzdPushUser)
    {
        return hhlZzdPushUserMapper.selectHhlZzdPushUserList(hhlZzdPushUser);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param hhlZzdPushUser 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertHhlZzdPushUser(HhlZzdPushUser hhlZzdPushUser)
    {
        hhlZzdPushUser.setCreateTime(DateUtils.getNowDate());
        return hhlZzdPushUserMapper.insertHhlZzdPushUser(hhlZzdPushUser);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param hhlZzdPushUser 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateHhlZzdPushUser(HhlZzdPushUser hhlZzdPushUser)
    {
        return hhlZzdPushUserMapper.updateHhlZzdPushUser(hhlZzdPushUser);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdPushUserByIds(String ids)
    {
        return hhlZzdPushUserMapper.deleteHhlZzdPushUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteHhlZzdPushUserById(String id)
    {
        return hhlZzdPushUserMapper.deleteHhlZzdPushUserById(id);
    }

    /**
     * 通过用户名查询用户
     */
    public HhlZzdPushUser selectUserByUsername(String username){
        return hhlZzdPushUserMapper.selectUserByUsername(username);
    }
}
