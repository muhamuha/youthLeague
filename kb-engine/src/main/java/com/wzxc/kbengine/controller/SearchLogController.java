package com.wzxc.kbengine.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.kbengine.vo.SearchLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.service.ISearchLogService;

/**
 * 【请填写功能名称】Controller
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@Controller
@RequestMapping("/kbengine/log")
public class SearchLogController extends BaseController
{

    @Autowired
    private ISearchLogService searchLogService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PostMapping("/list")
    public KbengineResult list(@RequestBody SearchLog searchLog) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<SearchLog> list = searchLogService.selectSearchLogList(searchLog);
        buildTableInfo(list, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public KbengineResult add(@RequestBody SearchLog searchLog)
    {
        int isSuccess = searchLogService.insertSearchLog(searchLog);
        if(isSuccess == 0){
            return KbengineResult.error("新增失败");
        }
        return KbengineResult.success("新增成功");
    }

    /**
     * 删除【请填写功能名称】
     */
    @PostMapping("/remove")
    public KbengineResult remove(Long id)
    {
        int isSuccess = searchLogService.deleteSearchLogById(id);
        if(isSuccess == 0){
            return KbengineResult.error("删除失败");
        }
        return KbengineResult.success("删除成功");
    }
}
