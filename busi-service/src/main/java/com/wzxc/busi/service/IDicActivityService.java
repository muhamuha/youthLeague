package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.DicActivity;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-23
 */
public interface IDicActivityService extends IService<DicActivity> {

    List<DicActivity> selectDicActivityList(DicActivity dicActivity);

    int insertDicActivity(DicActivity dicActivity);

    int updateDicActivity(DicActivity dicActivity);

}
