package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.DicUniversal;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-12-01
 */
public interface IDicUniversalService extends IService<DicUniversal> {

    List<DicUniversal> selectDicUniversalList(DicUniversal dicUniversal);

    int insertDicUniversal(DicUniversal dicUniversal);

    int updateDicUniversal(DicUniversal dicUniversal);

    int insertWithDistinct(DicUniversal dicUniversal);

}
