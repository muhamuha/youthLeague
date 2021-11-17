package com.wzxc.camunda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzxc.camunda.persistence.entity.CaAdmin;

import java.util.List;

/**
 * <p>
 * 服务类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-05
 */
public interface ICaAdminService extends IService<CaAdmin> {

    List<CaAdmin> selectCaAdminList(CaAdmin caAdmin);

    int insertCaAdmin(CaAdmin caAdmin);

    int updateCaAdmin(CaAdmin caAdmin);

}
