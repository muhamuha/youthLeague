package com.wzxc.camunda.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzxc.camunda.dao.CaAdminMapper;
import com.wzxc.camunda.persistence.entity.CaAdmin;
import com.wzxc.camunda.service.ICaAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类 【请填写功能名称】
 * </p>
 *
 * @author MUHAMUHA
 * @date 2021-11-05
 */
@Service
public class CaAdminServiceImpl extends ServiceImpl<CaAdminMapper, CaAdmin> implements ICaAdminService {

    @Autowired
    private CaAdminMapper caAdminMapper;

    /**
     * 查询【请填写功能名称】列表
     *
     * @param caAdmin 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CaAdmin> selectCaAdminList(CaAdmin caAdmin) {
        return caAdminMapper.selectCaAdminList(caAdmin);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param caAdmin 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCaAdmin(CaAdmin caAdmin) {
        return caAdminMapper.insertCaAdmin(caAdmin);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param caAdmin 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCaAdmin(CaAdmin caAdmin) {
        return caAdminMapper.updateCaAdmin(caAdmin);
    }


}