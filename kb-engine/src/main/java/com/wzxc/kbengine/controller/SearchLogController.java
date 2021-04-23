package com.wzxc.kbengine.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.kbengine.vo.SearchLog;
import io.swagger.annotations.*;
import lombok.extern.java.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.service.ISearchLogService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 【请填写功能名称】Controller
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@Controller
@RequestMapping("/kbengine/log")
@Api(tags="日志操作接口")
public class SearchLogController extends BaseController
{

    @Autowired
    private ISearchLogService searchLogService;

    /**
     * 查询【请填写功能名称】列表
     */
    @ApiOperation(value = "查询日志列表", notes = "查询日志列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logType",value = "日志类型:0-搜索浏览量日志;1-搜索次数（关联类型）日志", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "paramtersIn",value = "入参", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "searchType",value = "搜索的类型:0-综合;1-任务;2：指标;3-工作;4-政策;5-评价;6-问题", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "standby1",value = "备用字段1", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "standby2",value = "备用字段2", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "paramtersOut",value = "反参", required = false, paramType = "query", dataType="String"),
    })

    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = SearchLog.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public KbengineResult list(@RequestBody @ApiIgnore SearchLog searchLog) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<SearchLog> list = searchLogService.selectSearchLogList(searchLog);
        buildTableInfo(list, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    /**
     * 新增【请填写功能名称】
     */

    @ApiOperation(value = "添加日志", notes = "添加日志", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logType",value = "日志类型:0-搜索浏览量日志;1-搜索次数（关联类型）日志", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "paramtersIn",value = "入参", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "searchType",value = "搜索的类型:0-综合;1-任务;2：指标;3-工作;4-政策;5-评价;6-问题", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "standby1",value = "备用字段1", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "standby2",value = "备用字段2", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "paramtersOut",value = "反参", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
    })
    @PostMapping("/add")
    public KbengineResult add(@RequestBody @ApiIgnore SearchLog searchLog)
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
    @ApiOperation(value = "删除日志", notes = "删除日志", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping( "/remove")
    public KbengineResult remove(Long id)
    {
        int isSuccess = searchLogService.deleteSearchLogById(id);
        if(isSuccess == 0){
            return KbengineResult.error("删除失败");
        }
        return KbengineResult.success("删除成功");
    }
}
