package com.wzxc.zzdpush.dao.ds1;

import com.wzxc.zzdpush.vo.HhlZzdPushHis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HhlZzdPushHisMapper {

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public HhlZzdPushHis selectHhlZzdPushHisById(String id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param hhlZzdPushHis 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<HhlZzdPushHis> selectHhlZzdPushHisList(HhlZzdPushHis hhlZzdPushHis);

    /**
     * 新增【请填写功能名称】
     *
     * @param hhlZzdPushHis 【请填写功能名称】
     * @return 结果
     */
    public int insertHhlZzdPushHis(HhlZzdPushHis hhlZzdPushHis);

    /**
     * 修改【请填写功能名称】
     *
     * @param hhlZzdPushHis 【请填写功能名称】
     * @return 结果
     */
    public int updateHhlZzdPushHis(HhlZzdPushHis hhlZzdPushHis);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteHhlZzdPushHisById(Integer id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHhlZzdPushHisByIds(String[] ids);

    HhlZzdPushHis selectHhlZzdPushHisByMessageId(@Param("bizMsgId") String bizMsgId);
}
