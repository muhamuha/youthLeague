package com.wzxc.busi.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.busi.vo.DicIndustry;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-26
 */
public interface IDicIndustryService extends IService<DicIndustry> {

    List<DicIndustry> selectDicIndustryList(DicIndustry dicIndustry);

    int insertDicIndustry(DicIndustry dicIndustry);

    int updateDicIndustry(DicIndustry dicIndustry);

}
