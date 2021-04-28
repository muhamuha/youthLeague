package com.wzxc.kbengine.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.validate.Check;
import com.wzxc.kbengine.service.IDxQuotaHistoryService;
import com.wzxc.kbengine.vo.DxQuotaHistory;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.vo.DxQuotaCurrent;
import com.wzxc.kbengine.service.IDxQuotaCurrentService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 【请填写功能名称】Controller
 * 
 * @author huanghl
 * @date 2021-04-27
 */
@RestController
@CrossOrigin
@RequestMapping("/dxQuotaCurrent")
@Api(tags="指标操作接口")
public class DxQuotaCurrentController extends BaseController {

    @Autowired
    private IDxQuotaCurrentService dxQuotaCurrentService;
    @Autowired
    private IDxQuotaHistoryService dxQuotaHistoryService;

    /**
     * 查询【请填写功能名称】列表
     */

    @ApiOperation(value = "查询指标列表", notes = "查询指标列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "quotaName",value = "指标名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaDesc",value = "指标定义", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "valueUnit",value = "指标单位", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "nodeId",value = "区县编码", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "quotaValue",value = "指标值", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaYoy",value = "同比", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaMom",value = "环比", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "updator",value = "更新人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "isValid",value = "是否有效:0-有效;1-无效", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = DxQuotaCurrent.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public KbengineResult list(@RequestBody @ApiIgnore DxQuotaCurrent dxQuotaCurrent) throws IOException {
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
    public KbengineResult addSave(@RequestBody @ApiIgnore DxQuotaCurrent dxQuotaCurrent) {
        int isSuccess = dxQuotaCurrentService.insertDxQuotaCurrent(dxQuotaCurrent);
        if(isSuccess == 0){
            return KbengineResult.error("新增失败");
        }
        return KbengineResult.success("新增成功");
    }

    /**
     * 修改保存【请填写功能名称】
     */

    @ApiOperation(value = "修改指标", notes = "修改指标", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "quotaName",value = "指标名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaDesc",value = "指标定义", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "valueUnit",value = "指标单位", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "nodeId",value = "区县编码", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "quotaValue",value = "指标值", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaYoy",value = "同比", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaMom",value = "环比", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "updator",value = "更新人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "isValid",value = "是否有效:0-有效;1-无效", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "createTime",value = "创建时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "updateTime",value = "最后修改时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "remarks",value = "备注", required = false, paramType = "query", dataType="string"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = DxQuotaCurrent.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "dxQuotaCurrent.id", msg = "缺少系统主键"),
    })
    @PostMapping("/edit")
    public KbengineResult editSave(@RequestBody @ApiIgnore DxQuotaCurrent dxQuotaCurrent) {
        int isSuccess = dxQuotaCurrentService.updateDxQuotaCurrent(dxQuotaCurrent);
        if(isSuccess == 0){
            return KbengineResult.error("修改失败");
        }
        dxQuotaCurrent = dxQuotaCurrentService.selectDxQuotaCurrentById(dxQuotaCurrent.getId());
        DxQuotaHistory dxQuotaHistory = new DxQuotaHistory();
        BeanUtils.copyProperties(dxQuotaCurrent,dxQuotaHistory);
        dxQuotaHistory.setQuotaId(dxQuotaCurrent.getId());
        dxQuotaHistoryService.insertDxQuotaHistory(dxQuotaHistory);
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
