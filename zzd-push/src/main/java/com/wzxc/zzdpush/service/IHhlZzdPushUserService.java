package com.wzxc.zzdpush.service;

import com.wzxc.zzdpush.vo.HhlZzdPushUser;

import java.util.List;

public interface IHhlZzdPushUserService {

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public HhlZzdPushUser selectHhlZzdPushUserById(String id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param hhlZzdPushUser 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<HhlZzdPushUser> selectHhlZzdPushUserList(HhlZzdPushUser hhlZzdPushUser);

    /**
     * 新增【请填写功能名称】
     *
     * @param hhlZzdPushUser 【请填写功能名称】
     * @return 结果
     */
    public int insertHhlZzdPushUser(HhlZzdPushUser hhlZzdPushUser);

    /**
     * 修改【请填写功能名称】
     *
     * @param hhlZzdPushUser 【请填写功能名称】
     * @return 结果
     */
    public int updateHhlZzdPushUser(HhlZzdPushUser hhlZzdPushUser);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHhlZzdPushUserByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteHhlZzdPushUserById(String id);
}
