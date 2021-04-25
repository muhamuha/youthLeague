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
import com.wzxc.configcommon.shiro.JwtFilter;
import com.wzxc.kbengine.service.impl.QsBaseInfoRepServiceImpl;
import com.wzxc.kbengine.vo.QsBaseInfoRep;
import io.swagger.annotations.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wzxc.kbengine.service.IQsBaseInfoRepService;
import org.springframework.web.multipart.MultipartFile;
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
@RequestMapping("/kbengine/FAQ")
@Api(tags="FAQ操作接口")
public class QsBaseInfoRepController extends BaseController {

    @Autowired
    private IQsBaseInfoRepService qsBaseInfoRepService;

    private final String [] columnHeads={"问题标题","问题描述","问题答案","问题所属领域","访问权限","审核状态","创建人","更新人","数据来源"};

    /**
     * 查询【请填写功能名称】列表
     */
    @ApiOperation(value = "查询FAQ列表", notes = "查询FAQ列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qTitle",value = "问题标题", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qSystem",value = "问题所属领域:0-党政机关整体智治系统;1-数字政府系统;2-数字社会系统;3-数字经济系统;4-数字法制系统", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "qPermission",value = "访问权限:0-全部可见", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "status",value = "审核状态:0-待审核;1-审核通过", required = false, paramType = "query", dataType="int"),
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
    @ApiOperation(value = "添加FAQ", notes = "添加FAQ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qTitle",value = "问题标题", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qDes",value = "问题描述", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qAnswer",value = "问题回答", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "qSystem",value = "问题所属领域:0-党政机关整体智治系统;1-数字政府系统;2-数字社会系统;3-数字经济系统;4-数字法制系统", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "qPermission",value = "访问权限:0-全部可见（默认0全部可见）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "status",value = "审核状态:0-待审核;1-审核通过（默认0待审核）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.qTitle", msg = "缺少问题标题（qTitle）"),
            @CheckParam(value = Check.NotNull, argName = "qsBaseInfoRep.qDes", msg = "缺少问题描述（qDes）"),
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
    @ApiOperation(value = "修改FAQ", notes = "修改FAQ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "qTitle",value = "问题标题", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qDes",value = "问题描述", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "qAnswer",value = "问题回答", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "qSystem",value = "问题所属领域:0-党政机关整体智治系统;1-数字政府系统;2-数字社会系统;3-数字经济系统;4-数字法制系统", required = true, paramType = "query", dataType="int"),
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
    })
    @PostMapping("/edit")
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
    @ApiOperation(value = "删除FAQ", notes = "删除FAQ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "id", msg = "缺少系统主键"),
    })
    @PostMapping("/remove")
    public KbengineResult remove(Long id)
    {
        int isSuccess = qsBaseInfoRepService.deleteQsBaseInfoRepById(id);
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
        String fileName = "FAQInsertTemplate.xls";
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
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "问题标题", fieldName = "q_title"),
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "问题描述", fieldName = "q_des"),
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "问题答案", fieldName = "q_answer"),
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "问题所属领域", fieldName = "q_system", express = "com.wzxc.kbengine.en.QsBaseInfoRep.QSystem"),
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "访问权限", fieldName = "q_permission", express = "com.wzxc.kbengine.en.QsBaseInfoRep.QPermission"),
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "审核状态", fieldName = "status", express = "com.wzxc.kbengine.en.QsBaseInfoRep.Status"),
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
        // 获取数据
        List<List<String>> contentList = ExcelUtils.exportContentListFromExcel(file, fieldNameList, insertBatchParams);
        if(contentList == null || contentList.size() == 0){
            return KbengineResult.success("导入成功");
        }
        InsertBatchCommon insertBatchCommon = new InsertBatchCommon();
        insertBatchCommon.setFieldList(fieldNameList);
        insertBatchCommon.setContentList(contentList);
        fieldNameList.add("resource");
        fieldNameList.add("creator");
        fieldNameList.add("updator");
        for(List<String> row : contentList){
            row.add("0");
            row.add(JwtFilter.getUserId());
            row.add(JwtFilter.getUserId());
        }
        qsBaseInfoRepService.insertBatch(insertBatchCommon);
        return KbengineResult.success("批导入成功");
    }

    @ApiOperation(value = "FAQ列表导出", notes = "FAQ列表导出", httpMethod = "GET")
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
