package com.wzxc.kbengine.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.kbengine.vo.QsBaseInfoRep;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.service.IQsBaseInfoRepService;

/**
 * 【请填写功能名称】Controller
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@Controller
@RequestMapping("/kbengine/rep")
public class QsBaseInfoRepController extends BaseController {

    @Autowired
    private IQsBaseInfoRepService qsBaseInfoRepService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PostMapping("/list")
    @ResponseBody
    public KbengineResult list(@RequestBody QsBaseInfoRep qsBaseInfoRep) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<QsBaseInfoRep> list = qsBaseInfoRepService.selectQsBaseInfoRepList(qsBaseInfoRep);
        buildTableInfo(list, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public KbengineResult add(@RequestBody QsBaseInfoRep qsBaseInfoRep) {
        int isSuccess = qsBaseInfoRepService.insertQsBaseInfoRep(qsBaseInfoRep);
        if(isSuccess == 0){
            return KbengineResult.error("新增失败");
        }
        return KbengineResult.success("新增成功");
    }


    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public KbengineResult edit(@RequestBody QsBaseInfoRep qsBaseInfoRep) {
        int isSuccess = qsBaseInfoRepService.updateQsBaseInfoRep(qsBaseInfoRep);
        if(isSuccess == 0){
            return KbengineResult.error("修改失败");
        }
        return KbengineResult.success("修改成功");
    }

    /**
     * 删除【请填写功能名称】
     */
    @PostMapping( "/remove")
    @ResponseBody
    public KbengineResult remove(Long id)
    {
        int isSuccess = qsBaseInfoRepService.deleteQsBaseInfoRepById(id);
        if(isSuccess == 0){
            return KbengineResult.error("删除失败");
        }
        return KbengineResult.success("删除成功");
    }
}
