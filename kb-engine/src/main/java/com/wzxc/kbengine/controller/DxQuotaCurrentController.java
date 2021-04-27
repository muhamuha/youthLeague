package com.wzxc.kbengine.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.kbengine.KbEngineApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.vo.DxQuotaCurrent;
import com.wzxc.kbengine.service.IDxQuotaCurrentService;

import static org.apache.naming.SelectorContext.prefix;

/**
 * 【请填写功能名称】Controller
 * 
 * @author huanghl
 * @date 2021-04-27
 */
@RestController
@CrossOrigin
@RequestMapping("/dxQuotaCurrent")
@Slf4j
public class DxQuotaCurrentController extends BaseController {

    @Autowired
    private IDxQuotaCurrentService dxQuotaCurrentService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PostMapping("/list")
    public KbengineResult list(DxQuotaCurrent dxQuotaCurrent) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<DxQuotaCurrent> list = dxQuotaCurrentService.selectDxQuotaCurrentList(dxQuotaCurrent);
        buildTableInfo(list, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @PostMapping("/add")
    public KbengineResult addSave(DxQuotaCurrent dxQuotaCurrent) {
        int isSuccess = dxQuotaCurrentService.insertDxQuotaCurrent(dxQuotaCurrent);
        if(isSuccess == 0){
            return KbengineResult.error("新增失败");
        }
        return KbengineResult.success("新增成功");
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @PostMapping("/edit")
    public KbengineResult editSave(DxQuotaCurrent dxQuotaCurrent) {
        int isSuccess = dxQuotaCurrentService.updateDxQuotaCurrent(dxQuotaCurrent);
        if(isSuccess == 0){
            return KbengineResult.error("修改失败");
        }
        return KbengineResult.success("修改成功");
    }

    /**
     * 删除【请填写功能名称】
     */
    @PostMapping( "/remove")
    public KbengineResult remove(Long id) {
        int isSuccess = dxQuotaCurrentService.deleteDxQuotaCurrentById(id);
        if(isSuccess == 0){
            return KbengineResult.error("删除失败");
        }
        return KbengineResult.success("删除成功");
    }
}
