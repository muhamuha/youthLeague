package com.wzxc.kbengine.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.file.ExcelUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.kbengine.vo.QsBaseInfoRep;
import io.swagger.annotations.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.service.IQsBaseInfoRepService;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

/**
 * 【请填写功能名称】Controller
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@RestController
@CrossOrigin
@RequestMapping("/kbengine/rep")
@Api(tags="问题操作接口")
public class QsBaseInfoRepController extends BaseController {

    @Autowired
    private IQsBaseInfoRepService qsBaseInfoRepService;

    private final String [] columnHeads={"问题标题","问题描述","问题答案","问题所属领域","访问权限","审核状态","创建人","更新人","数据来源"};
    /**
     * 查询【请填写功能名称】列表
     */

    @ApiOperation(value = "查询问题列表", notes = "查询问题列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qTitle",value = "问题标题", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qDes",value = "问题描述", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qAnswer",value = "问题回答", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "qSystem",value = "问题所属领域:0-党政机关整体智治系统;1-数字政府系统;2-数字社会系统;3-数字经济系统;4-数字法制系统", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "qPermission",value = "访问权限:0-全部可见", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "status",value = "审核状态:0-待审核;1-审核通过", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "updator",value = "更新人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "resource",value = "数源:0-手工录入;1-批导入", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isValid",value = "是否有效:0-有效;1-无效", required = false, paramType = "query", dataType="int"),
    })

    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = QsBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public KbengineResult list(@RequestBody @ApiIgnore QsBaseInfoRep qsBaseInfoRep) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<QsBaseInfoRep> list = qsBaseInfoRepService.selectQsBaseInfoRepList(qsBaseInfoRep);
        buildTableInfo(list, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    /**
     * 新增【请填写功能名称】
     */

    @ApiOperation(value = "添加问题", notes = "添加问题", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qTitle",value = "问题标题", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qDes",value = "问题描述", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qAnswer",value = "问题回答", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "qSystem",value = "问题所属领域:0-党政机关整体智治系统;1-数字政府系统;2-数字社会系统;3-数字经济系统;4-数字法制系统",
                    required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "qPermission",value = "访问权限:0-全部可见", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "status",value = "审核状态:0-待审核;1-审核通过", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "createTime",value = "创建时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "updateTime",value = "最后修改时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "updator",value = "更新人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "resource",value = "数源:0-手工录入;1-批导入", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isValid",value = "是否有效:0-有效;1-无效", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.qTitle", msg = "缺少问题标题（qTitle）"),
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.qSystem", msg = "缺少问题所属领域（qSystem）"),
    })
    @PostMapping("/add")
    public KbengineResult add(@RequestBody @ApiIgnore QsBaseInfoRep qsBaseInfoRep) {
        int isSuccess = qsBaseInfoRepService.insertQsBaseInfoRep(qsBaseInfoRep);
        if(isSuccess == 0){
            return KbengineResult.error("新增失败");
        }
        return KbengineResult.success("新增成功");
    }


    /**
     * 修改【请填写功能名称】
     */

    @ApiOperation(value = "修改问题", notes = "修改问题", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "qTitle",value = "问题标题", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qDes",value = "问题描述", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qAnswer",value = "问题回答", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "qSystem",value = "问题所属领域:0-党政机关整体智治系统;1-数字政府系统;2-数字社会系统;3-数字经济系统;4-数字法制系统",
                    required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "qPermission",value = "访问权限:0-全部可见", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "status",value = "审核状态:0-待审核;1-审核通过", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "createTime",value = "创建时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "updateTime",value = "最后修改时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "updator",value = "更新人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "resource",value = "数源:0-手工录入;1-批导入", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isValid",value = "是否有效:0-有效;1-无效", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.id", msg = "缺少系统主键"),
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.qTitle", msg = "缺少问题标题（qTitle）"),
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.qSystem", msg = "缺少问题所属领域（qSystem）"),
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.qPermission", msg = "缺少访问权限（qPermission）"),
    })
    @GetMapping("/edit/{id}")
    public KbengineResult edit(@RequestBody @ApiIgnore QsBaseInfoRep qsBaseInfoRep) {
        int isSuccess = qsBaseInfoRepService.updateQsBaseInfoRep(qsBaseInfoRep);
        if(isSuccess == 0){
            return KbengineResult.error("修改失败");
        }
        return KbengineResult.success("修改成功");
    }

    /**
     * 删除【请填写功能名称】
     */
    @ApiOperation(value = "删除问题", notes = "删除问题", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
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

    @ApiOperation(value = "问题列表导出", notes = "问题列表导出", httpMethod = "GET")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "qTitle",value = "问题标题", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qDes",value = "问题描述", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qAnswer",value = "问题回答", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "qSystem",value = "问题所属领域:0-党政机关整体智治系统;1-数字政府系统;2-数字社会系统;3-数字经济系统;4-数字法制系统",
                    required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "qPermission",value = "访问权限:0-全部可见", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "status",value = "审核状态:0-待审核;1-审核通过", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "updator",value = "更新人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "resource",value = "数源:0-手工录入;1-批导入", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isValid",value = "是否有效:0-有效;1-无效", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/download")
    public KbengineResult download(@RequestBody @ApiIgnore QsBaseInfoRep qsBaseInfoRep, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        List<QsBaseInfoRep> list = qsBaseInfoRepService.selectQsBaseInfoRepList(qsBaseInfoRep);
        List<List<Object>> excelList = new ArrayList<List<Object>>();
        List columnHeadList = new ArrayList();
        for (String column : columnHeads) {
            columnHeadList.add(column);
        }
        excelList.add(columnHeadList);
        for(int i = 0;i<list.size();i++){
            List dataList = new ArrayList();
            QsBaseInfoRep qsBaseInfo = list.get(i);
            dataList.add(qsBaseInfo.getQTitle()==null?"":qsBaseInfo.getQTitle());
            dataList.add(qsBaseInfo.getQDes()==null?"":qsBaseInfo.getQDes());
            dataList.add(qsBaseInfo.getQAnswer()==null?"":qsBaseInfo.getQAnswer());
            dataList.add(qsBaseInfo.getQSystem()==null?"":qsBaseInfo.getQSystem());
            dataList.add(qsBaseInfo.getQPermission()==null?"":qsBaseInfo.getQPermission());
            dataList.add(qsBaseInfo.getStatus()==null?"":qsBaseInfo.getStatus());
            dataList.add(qsBaseInfo.getCreator()==null?"":qsBaseInfo.getCreator());
            dataList.add(qsBaseInfo.getUpdator()==null?"":qsBaseInfo.getUpdator());
            dataList.add(qsBaseInfo.getResource()==null?"":qsBaseInfo.getResource());
            excelList.add(dataList);
        }

        try {
            XSSFWorkbook workbook = ExcelUtils.exportEXCELFile(excelList,"问题清单表");

            response.reset(); // 非常重要
            response.setContentType("Content-Type: application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + "问题清单表.xlsx");
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
