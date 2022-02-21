package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.DicGroup;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-30
 */
public interface IDicGroupService extends IService<DicGroup> {

    List<DicGroup> selectDicGroupList(DicGroup dicGroup);

    int insertDicGroup(DicGroup dicGroup);

    int updateDicGroup(DicGroup dicGroup);

}
