package com.wzxc.kbengine.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.annotation.InsertBatchParam;
import com.wzxc.common.annotation.InsertBatchParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.core.page.TableDataInfo;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.file.ExcelUtils;
import com.wzxc.common.utils.reflect.ReflectUtils;
import com.wzxc.common.utils.uuid.IdUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.common.validate.Filter;
import com.wzxc.kbengine.service.impl.PolicyBaseInfoRepServiceImpl;
import com.wzxc.kbengine.service.impl.RefLabelRepServiceImpl;
import com.wzxc.kbengine.service.impl.TkPolicyRepServiceImpl;
import com.wzxc.kbengine.shiro.JwtFilter;
import com.wzxc.kbengine.vo.LabelBaseInfoRep;
import com.wzxc.kbengine.vo.PolicyBaseInfoRep;
import com.wzxc.kbengine.vo.RefLabelRep;
import com.wzxc.kbengine.vo.TkPolicyRep;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/policyBaseInfo")
@Slf4j
@Api(tags="知识库政策操作接口")
public class PolicyBaseInfoRepController extends BaseController {

    @Autowired
    private PolicyBaseInfoRepServiceImpl policyBaseInfoRepService;

    @Autowired
    private TkPolicyRepServiceImpl tkPolicyRepService;

    @Autowired
    private RefLabelRepServiceImpl refLabelRepService;

    @ApiOperation(value = "查询政策列表", notes = "查询政策列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyId",value = "政策主键", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unitName",value = "发布部门", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyType",value = "发布层级（0：国家 1：省级 2：市级 3：区县）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "publishTimeBegin",value = "发布时间（from，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "publishTimeEnd",value = "发送时间（to，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "keyword",value = "关键字（名称或文号）", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "drafter",value = "拟稿人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policySystem",value = "领域（0:党政机关整体智治系统,1:数字政府系统;2:数字社会系统;3:数字经济系统;4:数字法制系统）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyStatus",value = "状态（0：启用 1：停用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "taskId",value = "所属任务id（默认所有任务）", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "tkBaseInfoId",value = "所属任务id（默认所有任务）", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "isValid",value = "是否有效（0：有效 1：无效，默认0有效）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize",value = "页码（默认10）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageNum",value = "页数（默认1）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "tkOnly",value = "是否只显示任务相关（0：都显示，1：只显示，默认都显示）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = PolicyBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/policies/get")
    public KbengineResult list(@RequestBody @ApiIgnore PolicyBaseInfoRep policyBaseInfoRep) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<PolicyBaseInfoRep> policyBaseInfoReps = new ArrayList<>();
        if(policyBaseInfoRep.getTkBaseInfoId() == null && policyBaseInfoRep.getTaskId() == null){ // 只查询政策表
            policyBaseInfoReps = policyBaseInfoRepService.selectPolicyBaseInfoRepList(policyBaseInfoRep);
        } else{ // 需要关联 政策任务关联表
            // 判断是否只显示任务相关的政策
            if(policyBaseInfoRep.getTkOnly() != null && policyBaseInfoRep.getTkOnly() == 1){
                policyBaseInfoReps = policyBaseInfoRepService.selectPolicyBaseInfoRepByTkIdOnly(policyBaseInfoRep);
            } else{
                policyBaseInfoReps = policyBaseInfoRepService.selectPolicyBaseInfoRepListByTkId(policyBaseInfoRep);
                List<PolicyBaseInfoRep> tkPolicyList = new ArrayList<>();
                for(PolicyBaseInfoRep p : policyBaseInfoReps){
                    if(p.getTkFlag() == 1){
                        tkPolicyList.add(p);
                    }
                }
                resultMap.put("tkList", tkPolicyList);
            }
        }
        try{
            for(PolicyBaseInfoRep policyBaseInfoRepEle : policyBaseInfoReps){
                if(StringUtils.isEmpty(policyBaseInfoRepEle.getFileObjectStr())){
                    continue;
                }
                JSONArray fileList = JSONObject.parseArray(policyBaseInfoRepEle.getFileObjectStr());
                List<Map<String, String>> files = new ArrayList<>();
                for(Object fileEle : fileList){
                    Map<String, String> map = new HashMap<>();
                    map.put("fileName", ((JSONObject) fileEle).getString("fileName"));
                    map.put("fileUrl", ((JSONObject) fileEle).getString("fileUrl"));
                    files.add(map);
                }
                policyBaseInfoRepEle.setFileObjectList(files);
            }

        } catch(Exception e){
        }
        buildTableInfo(policyBaseInfoReps, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    @ApiOperation(value = "添加政策", notes = "添加政策", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileObjectList",value = "政策文件对象数组", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "filePublishTime",value = "政策发布时间", required = true, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "fileImpTime",value = "政策实施时间", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "unscrambleName",value = "解读文件名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unscrambleUrl",value = "解读文件url", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unitName",value = "发布部门", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unitCode",value = "发布部门统一信用代码", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "resource",value = "数源（OA/内跑/省门户）", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyName",value = "政策名称", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyNumber",value = "文号", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyType",value = "发布层级（0：国家 1：省级 2：市级 3：区县）", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "drafter",value = "拟稿人", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyContentSi",value = "政策内容", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policySystem",value = "领域（0:党政机关整体智治系统,1:数字政府系统;2:数字社会系统;3:数字经济系统;4:数字法制系统）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyStatus",value = "状态（0：启用 1：停用，默认为0启用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "remark",value = "备注", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "labelList",value = "标签id（数组）", required = false, paramType = "query", dataType="long[]"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.filePublishTime", msg = "缺少政策发布时间（filePublishTime）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.unitName", msg = "缺少发布部门（unitName）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.resource", msg = "缺少数源（resource）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.policyName", msg = "缺少政策名称（policyName）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.policyNumber", msg = "缺少文号（policyNumber）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.policyType", msg = "缺少发布层级（policyType）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.drafter", msg = "缺少拟稿人（drafter）"),
    })
    @PostMapping("/policies/add")
    public KbengineResult add(@RequestBody @ApiIgnore PolicyBaseInfoRep policyBaseInfoRep, HttpServletRequest httpServletRequest){
        int isSuccess = policyBaseInfoRepService.insertPolicyBaseInfoRep(policyBaseInfoRep);
        if(isSuccess > 0){
            // 判断是否需要添加标签
            if(policyBaseInfoRep.getLabelList() != null && policyBaseInfoRep.getLabelList().length > 0){
                Long[] labelList = policyBaseInfoRep.getLabelList();
                for (Long label : labelList){
                    RefLabelRep refLabelRep = new RefLabelRep();
                    refLabelRep.setLabelBaseInfoRepId(Long.valueOf(label));
                    refLabelRep.setRefId(policyBaseInfoRep.getId());
                    refLabelRep.setRefType(3L);
                    refLabelRepService.insertRefLabelRep(refLabelRep);
                }
            }
            // 添加与任务之间的关联
            if(policyBaseInfoRep.getTkBaseInfoId() != null){
                TkPolicyRep tkPolicyRep = new TkPolicyRep();
                tkPolicyRep.setPolicyRepId(Long.valueOf(policyBaseInfoRep.getId()));
                tkPolicyRep.setTkBaseInfoId(policyBaseInfoRep.getTkBaseInfoId());
                tkPolicyRepService.insertTkPolicyRep(tkPolicyRep);
            }
            return KbengineResult.success("添加成功");
        } else{
            return KbengineResult.error("添加失败");
        }
    }

    @ApiOperation(value = "修改政策", notes = "修改政策", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "fileObjectList",value = "政策文件对象数组", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "filePublishTime",value = "政策发布时间", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "fileImpTime",value = "政策实施时间", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "unscrambleName",value = "解读文件名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unscrambleUrl",value = "解读文件url", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unitName",value = "发布部门", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unitCode",value = "发布部门统一信用代码", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "resource",value = "数源（OA/内跑/省门户）", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyName",value = "政策名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyNumber",value = "文号", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyType",value = "发布层级（0：国家 1：省级 2：市级 3：区县）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "drafter",value = "拟稿人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policySystem",value = "领域（0:党政机关整体智治系统,1:数字政府系统;2:数字社会系统;3:数字经济系统;4:数字法制系统）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyStatus",value = "状态（0：启用 1：停用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyContentSi",value = "政策内容", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "remark",value = "备注", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "labelList",value = "标签id（数组）", required = false, paramType = "query", dataType="long[]"),
            @ApiImplicitParam(name = "tkBaseInfoId",value = "任务id", required = false, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.id", msg = "缺少系统主键"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.unitName", msg = "缺少发布部门"),
    })
    @PostMapping("/policies/update")
    public KbengineResult update(@RequestBody @ApiIgnore PolicyBaseInfoRep policyBaseInfoRep){
        int isSuccess = policyBaseInfoRepService.updatePolicyBaseInfoRep(policyBaseInfoRep);
        if(isSuccess > 0){
            // 清空政策的标签
            refLabelRepService.clearLabels(policyBaseInfoRep.getId());
            // 添加标签
            if(policyBaseInfoRep.getLabelList() != null && policyBaseInfoRep.getLabelList().length > 0){
                for(Long labelBaseInfoRefId : policyBaseInfoRep.getLabelList()){
                    RefLabelRep refLabelRep = new RefLabelRep();
                    refLabelRep.setRefId(policyBaseInfoRep.getId());
                    refLabelRep.setRefType(3L);
                    refLabelRep.setLabelBaseInfoRepId(labelBaseInfoRefId);
                    refLabelRepService.insertRefLabelRep(refLabelRep);
                }
            }
            return KbengineResult.success("修改成功");
        } else{
            return KbengineResult.error("修改失败");
        }
    }

    /**
     * 删除政策
     */
    @ApiOperation(value = "删除政策", notes = "删除政策", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.id", msg = "缺少系统主键"),
    })
    @PostMapping("/policies/delete")
    public KbengineResult delete(@RequestBody @ApiIgnore PolicyBaseInfoRep policyBaseInfoRep){
        int isSuccess = policyBaseInfoRepService.deletePolicyBaseInfoRepById(policyBaseInfoRep);
        if(isSuccess > 0){
            return KbengineResult.success("删除成功");
        } else{
            return KbengineResult.error("删除失败");
        }
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
    @GetMapping(value = "/policies/downloadTemplate")
    public KbengineResult downLoad(HttpServletResponse response) throws Exception {
        String fileName = "policyInsertTemplate.xlsx";
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
            @ApiImplicitParam(name = "file", value = "文件名称", required = true, paramType = "query", dataType="Object"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @InsertBatchParams({
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "政策所属领域（必填）", fieldName = "policy_system", express = "com.wzxc.kbengine.en.policyBaseInfoRep.PolicySystem"),
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "发布层级描述（必填）", fieldName = "policy_type", express = "com.wzxc.kbengine.en.policyBaseInfoRep.PolicyType"),
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "地区（必填）", fieldName = "node_id", express = "com.wzxc.kbengine.en.policyBaseInfoRep.NodeId"),
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "政策名称（必填）", fieldName = "policy_name"),
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "政策文号（必填）", fieldName = "policy_number"),
            @InsertBatchParam(value = Check.DateOrEmpty, fieldNameZh = "发布时间（选填）", fieldName = "file_publish_time"),
            @InsertBatchParam(value = Check.DateOrEmpty, fieldNameZh = "实施时间（选填）", fieldName = "file_imp_time"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "制定单位名称（选填）", fieldName = "unit_name"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "政策正文（选填）", fieldName = "policy_content_si"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "备注（选填）", fieldName = "remark"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "拟稿人（选填）", fieldName = "drafter"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "政策文件（选填，格式：文件名称+“$”+url地址，多个用逗号隔开）", fieldName = "file_object_list", filter = Filter.Customize, express = "com.wzxc.kbengine.en.policyBaseInfoRep.FileObjectList"),
    })
    @PostMapping(value = "/policies/batch")
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
        fieldNameList.add("policy_id");
        fieldNameList.add("create_time");
        fieldNameList.add("update_time");
        fieldNameList.add("cd_operation");
        fieldNameList.add("resource");
        fieldNameList.add("creator");
        fieldNameList.add("updator");
        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss" );
        for(List<String> row : contentList){
            row.add("wz_" + IdUtils.getSnowflakeId());
            row.add(sdf.format(DateUtils.getNowDate()));
            row.add(sdf.format(DateUtils.getNowDate()));
            row.add("i");
            row.add("数字温州 - 批导入");
            row.add(JwtFilter.getUserId());
            row.add(JwtFilter.getUserId());
        }
        policyBaseInfoRepService.insertBatch(insertBatchCommon);
        return KbengineResult.success("批导入成功");
    }

    /**
     * 接口批导入
     */
    @ApiOperation(value = "批导入-接口导入", notes = "批导入-接口导入", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "导入数据", required = true, paramType = "query", dataType="Object"),
})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.filePublishTime", msg = "缺少政策发布时间（filePublishTime）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.unitName", msg = "缺少发布部门（unitName）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.resource", msg = "缺少数源（resource）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.policyName", msg = "缺少政策名称（policyName）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.policyNumber", msg = "缺少文号（policyNumber）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.policyType", msg = "缺少发布层级（policyType）"),
            @CheckParam(value = Check.NotNull, argName = "policyBaseInfoRep.drafter", msg = "缺少拟稿人（drafter）"),
    })
    @PostMapping(value = "/policies/batch/1")
    public KbengineResult insertBatch1(@RequestBody List<PolicyBaseInfoRep> PolicyBaseInfoRepList){
        Method method = ReflectUtils.getAccessibleMethodByName(this, "insertBatch", 1);
        Annotation annotation = ReflectUtils.getAnntationByMethod(method, "InsertBatchParams");
        InsertBatchParam[] insertBatchParams = ((InsertBatchParams) annotation).value();
        return KbengineResult.success("批导入成功");
    }

}

