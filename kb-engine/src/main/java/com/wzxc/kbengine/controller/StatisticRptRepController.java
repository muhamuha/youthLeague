package com.wzxc.kbengine.controller;

import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.file.ExcelUtils;
import com.wzxc.kbengine.service.impl.StatisticRptRepServiceImpl;
import com.wzxc.kbengine.service.impl.SysOrgServiceImpl;
import com.wzxc.kbengine.vo.StatisticRptRep;
import com.wzxc.kbengine.vo.SysOrg;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/statistic")
@Slf4j
@Api(tags="任务统计报表操作接口")
public class StatisticRptRepController extends BaseController {

    @Autowired
    private StatisticRptRepServiceImpl statisticRptRepService;
    @Autowired
    private SysOrgServiceImpl sysOrgService;

    private final String [] columnHeads={"牵头单位ID", "牵头单位", "领域", "任务来源", "任务名称", "任务层级",
            "任务路径", "任务指标数", "改革清单数", "工作计划数", "政策体系数","业务维护员ID", "业务维护员", "手机号"};

    @ApiOperation(value = "查询牵头单位列表", notes = "查询牵头单位列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgName",value = "单位名称", required = false, paramType = "query", dataType="string"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = SysOrg.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/org/list")
    public KbengineResult orgList(@RequestBody @ApiIgnore SysOrg sysOrg) throws IOException {
        List<SysOrg> orgList = sysOrgService.selectSysOrgList(sysOrg);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows",orgList);
        return KbengineResult.success("查询成功", resultMap);
    }

    @ApiOperation(value = "查询任务统计列表", notes = "查询任务统计列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode",value = "牵头单位编码", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskSystem",value = "领域", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskLevel",value = "任务层级", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskSource",value = "任务来源", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskName",value = "任务名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaCount",value = "指标", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyCount",value = "政策", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = StatisticRptRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/report/list")
    public KbengineResult list(@RequestBody @ApiIgnore StatisticRptRep statisticRptRep) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<StatisticRptRep> statisticRptReps = statisticRptRepService.selectStatisticRptRepList(statisticRptRep);
        buildTableInfo(statisticRptReps, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    @ApiOperation(value = "任务统计报表导出", notes = "任务统计报表导出", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode",value = "牵头单位编码", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskSystem",value = "领域", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskLevel",value = "任务层级", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskSource",value = "任务来源", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "taskName",value = "任务名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "quotaCount",value = "指标", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyCount",value = "政策", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/report/download")
    public KbengineResult download(@RequestParam(value = "orgCode", required = false) String orgCode,
                                   @RequestParam(value = "taskSystem", required = false) String taskSystem,
                                   @RequestParam(value = "taskLevel", required = false) String taskLevel,
                                   @RequestParam(value = "taskSource", required = false) String taskSource,
                                   @RequestParam(value = "taskName", required = false) String taskName,
                                   @RequestParam(value = "quotaCount", required = false) int quotaCount,
                                   @RequestParam(value = "policyCount", required = false) int policyCount,
                                   HttpServletResponse response) throws IOException {
        StatisticRptRep statisticRptRep = new StatisticRptRep();
        statisticRptRep.setOrgCode(orgCode);
        statisticRptRep.setTaskLevel(taskLevel);
        statisticRptRep.setTaskSystem(taskSystem);
        statisticRptRep.setTaskSource(taskSource);
        statisticRptRep.setTaskName(taskName);
        statisticRptRep.setQuotaCount(quotaCount);
        statisticRptRep.setPolicyCount(policyCount);
        Map<String, Object> resultMap = new HashMap<>();
        List<StatisticRptRep> statisticRptReps = statisticRptRepService.selectStatisticRptRepList(statisticRptRep);
        List<List<Object>> excelList = new ArrayList<List<Object>>();
        List columnHeadList = new ArrayList();
        for (String column : columnHeads) {
            columnHeadList.add(column);
        }
        excelList.add(columnHeadList);
        for(int i = 0;i<statisticRptReps.size();i++){
            List dataList = new ArrayList();
            StatisticRptRep statisticRptInfo = statisticRptReps.get(i);
            dataList.add(statisticRptInfo.getOrgCode()==null?"":statisticRptInfo.getOrgCode());
            dataList.add(statisticRptInfo.getOrgName()==null?"":statisticRptInfo.getOrgName());
            dataList.add(statisticRptInfo.getTaskSystem()==null?"":statisticRptInfo.getTaskSystem());
            dataList.add(statisticRptInfo.getTaskSource()==null?"":statisticRptInfo.getTaskSource());
            dataList.add(statisticRptInfo.getTaskName()==null?"":statisticRptInfo.getTaskName());
            dataList.add(statisticRptInfo.getTaskLevel()==null?"":statisticRptInfo.getTaskLevel());
            dataList.add(statisticRptInfo.getTaskPathName()==null?"":statisticRptInfo.getTaskPathName());
            dataList.add(statisticRptInfo.getQuotaCount()==null?"0":statisticRptInfo.getQuotaCount().toString());
            dataList.add(statisticRptInfo.getReformCount()==null?"0":statisticRptInfo.getReformCount().toString());
            dataList.add(statisticRptInfo.getPlanCount()==null?"0":statisticRptInfo.getPlanCount().toString());
            dataList.add(statisticRptInfo.getPolicyCount()==null?"0":statisticRptInfo.getPolicyCount().toString());
            dataList.add(statisticRptInfo.getTaskMaintainerId()==null?"":statisticRptInfo.getTaskMaintainerId());
            dataList.add(statisticRptInfo.getFullName()==null?"":statisticRptInfo.getFullName());
            dataList.add(statisticRptInfo.getMobile()==null?"":statisticRptInfo.getMobile());
            excelList.add(dataList);
        }

        try {
            XSSFWorkbook workbook = ExcelUtils.exportEXCELFile(excelList,"统计通报表");

            response.reset(); // 非常重要
            response.setContentType("Content-Type: application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + "统计通报表.xlsx");
            OutputStream out = response.getOutputStream();
            try {
                workbook.write(out);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                out.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
