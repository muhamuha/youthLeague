package com.wzxc.kbengine.controller;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.annotation.InsertBatchParam;
import com.wzxc.common.annotation.InsertBatchParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.file.ExcelUtils;
import com.wzxc.common.utils.reflect.ReflectUtils;
import com.wzxc.common.utils.uuid.IdUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.kbengine.service.IDxQuotaHistoryService;
import com.wzxc.kbengine.shiro.JwtFilter;
import com.wzxc.kbengine.vo.DxQuotaHistory;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.vo.DxQuotaCurrent;
import com.wzxc.kbengine.service.IDxQuotaCurrentService;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 下载批导入excel模板
     * @param filePath  文件上传时，返回的相对路径
     * @param response
     * @param isOnLine  传入true，表示打开，但是打开的是浏览器能识别的文件，比如图片、pdf，word等无法打开
     *                  传入false,只是下载，如果不传入这个参数默认为false
     * @throws Exception
     */
    @ApiOperation(value = "下载批导入模板", notes = "下载批导入模板", httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping(value = "/downloadTemplate")
    public KbengineResult downLoad(HttpServletResponse response) throws Exception {
        String fileName = "dxQuotaCurrentInsertTemplate.xlsx";
        InputStream templateFile = this.getClass().getClassLoader().getResourceAsStream("static" + File.separator + fileName);
        if (templateFile == null) {
            return KbengineResult.error("模板文件不存在");
        }
        fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        BufferedInputStream br = new BufferedInputStream(templateFile);
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset(); // 非常重要
        response.setContentType("Content-Type: application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
        return null;
    }

    /**
     * excel批导入
     */
    @ApiOperation(value = "批导入", notes = "批导入", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",value = "文件名称", required = true, paramType = "query", dataType="Object"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @InsertBatchParams({
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "指标名称（必填）", fieldName = "quota_name"),
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "地区（必填）", fieldName = "node_id", express = "com.wzxc.kbengine.en.dxQuotaCurrent.NodeId"),
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "指标值（必填）", fieldName = "quota_value"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "指标单位（选填）", fieldName = "value_unit"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "指标定义（选填）", fieldName = "quota_desc"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "同比值（选填）", fieldName = "quota_yoy"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "环比值（选填）", fieldName = "quota_mom"),
    })
    @PostMapping(value = "/batch")
    public KbengineResult insertBatch(@RequestParam("file") MultipartFile file) {
        Method method = ReflectUtils.getAccessibleMethodByName(this, "insertBatch", 1);
        Annotation annotation = ReflectUtils.getAnntationByMethod(method, "InsertBatchParams");
        InsertBatchParam[] insertBatchParams = ((InsertBatchParams) annotation).value();
        if (file.isEmpty()) {
            return KbengineResult.error("上传失败，请选择文件");
        }
        // 获取字段中文名称
        List<String> fieldNameZhList = ExcelUtils.exportTitleListFromExcel(file);
        // 获取字段名称
        List<String> fieldNameList = ExcelUtils.getFieldNameList(fieldNameZhList, insertBatchParams);
        List<String> fieldNameList1 = ExcelUtils.getFieldNameList(fieldNameZhList, insertBatchParams);
        // 获取数据
        List<List<String>> contentList = ExcelUtils.exportContentListFromExcel(file, fieldNameList, insertBatchParams);
        List<List<String>> contentList1 = ExcelUtils.exportContentListFromExcel(file, fieldNameList1, insertBatchParams);
        if(contentList == null || contentList.size() == 0){
            return KbengineResult.success("导入成功");
        }
        InsertBatchCommon insertBatchCommon = new InsertBatchCommon();
        insertBatchCommon.setFieldList(fieldNameList);
        insertBatchCommon.setContentList(contentList);
        fieldNameList.add("creator");
        fieldNameList.add("updator");
        for(List<String> row : contentList){
            row.add(JwtFilter.getUserId());
            row.add(JwtFilter.getUserId());
        }
        dxQuotaCurrentService.insertBatch(insertBatchCommon);

        // 批导入指标历史
        InsertBatchCommon insertBatchCommon1 = new InsertBatchCommon();
        insertBatchCommon1.setFieldList(fieldNameList1);
        insertBatchCommon1.setContentList(contentList1);
        fieldNameList1.add("creator");
        for(List<String> row : contentList1){
            row.add(JwtFilter.getUserId());
        }
        dxQuotaHistoryService.insertBatch(insertBatchCommon1);
        return KbengineResult.success("批导入成功");
    }
}
