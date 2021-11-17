package com.wzxc.camunda.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.camunda.persistence.entity.CaAdmin;

import java.util.List;

/**
 * Mapper接口 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-11-05
 */
public interface CaAdminMapper extends BaseMapper<CaAdmin> {

    List<CaAdmin> selectCaAdminList(CaAdmin caAdmin);

    int insertCaAdmin(CaAdmin caAdmin);

    int updateCaAdmin(CaAdmin caAdmin);
}
