package com.wzxc.kbengine.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.vo.DxQuotaHistory;
import com.wzxc.kbengine.service.IDxQuotaHistoryService;

/**
 * 【请填写功能名称】Controller
 * 
 * @author huanghl
 * @date 2021-04-27
 */
@RestController
@CrossOrigin
@RequestMapping("/dxQuotaHistory")
@Slf4j
public class DxQuotaHistoryController extends BaseController
{

    @Autowired
    private IDxQuotaHistoryService dxQuotaHistoryService;


    /**
     * 查询【请填写功能名称】列表
     */
    @PostMapping("/list")
    public KbengineResult list(DxQuotaHistory dxQuotaHistory) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<DxQuotaHistory> list = dxQuotaHistoryService.selectDxQuotaHistoryList(dxQuotaHistory);
        buildTableInfo(list, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @PostMapping("/add")
    public KbengineResult addSave(DxQuotaHistory dxQuotaHistory)
    {
        int isSuccess = dxQuotaHistoryService.insertDxQuotaHistory(dxQuotaHistory);
        if(isSuccess == 0){
            return KbengineResult.error("新增失败");
        }
        return KbengineResult.success("新增成功");
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @PostMapping("/edit")
    public KbengineResult editSave(DxQuotaHistory dxQuotaHistory)
    {
        int isSuccess = dxQuotaHistoryService.updateDxQuotaHistory(dxQuotaHistory);
        if(isSuccess == 0){
            return KbengineResult.error("修改失败");
        }
        return KbengineResult.success("修改成功");
    }

    /**
     * 删除【请填写功能名称】
     */
    @PostMapping( "/remove")
    public KbengineResult remove(Long id)
    {
        int isSuccess = dxQuotaHistoryService.deleteDxQuotaHistoryById(id);
        if(isSuccess == 0){
            return KbengineResult.error("删除失败");
        }
        return KbengineResult.success("删除成功");
    }
}
