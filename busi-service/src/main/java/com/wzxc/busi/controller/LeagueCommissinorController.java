package com.wzxc.busi.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.busi.service.impl.LeagueElectServiceImpl;
import com.wzxc.busi.vo.LeagueElect;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.annotation.InsertBatchParam;
import com.wzxc.common.annotation.InsertBatchParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.dao.InsertBatchCommon;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.core.domain.MyEntry;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.MapDataUtil;
import com.wzxc.common.utils.file.ExcelUtils;
import com.wzxc.common.utils.reflect.ReflectUtils;
import com.wzxc.common.utils.uuid.IdUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.validate.Filter;
import com.wzxc.webservice.shiro.JwtFilter;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.engine.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-10-29
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueCommissinor")
@Api(tags="委员管理类")
@Slf4j
public class LeagueCommissinorController extends BaseController {

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;
    @Autowired
    private LeagueElectServiceImpl leagueElectService;

    @Autowired
    private HistoryService historyService;

    private static final String passEventId = "Event_1azd78g";

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
                @ApiImplicitParam(name = "address", value = "家庭住址", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "birthday", value = "出生日期", required = false, paramType = "query", dataType="Date"),
                @ApiImplicitParam(name = "campus", value = "毕业院校", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "company", value = "所在公司", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "creater", value = "创建人（浙政钉id）", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "degree", value = "学位", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "deputyCppcc", value = "市级以上政协委员的情况", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "deputyNpc", value = "市级以上人大代表情况", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "deputyParty", value = "市级以上党代表的情况", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "education", value = "教育情况	1. 中专/高中	2. 专科	3. 本科	4. 硕士研究生	5. 博士", required = false, paramType = "query", dataType="Integer"),
                @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "gender", value = "性别", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "honorFile", value = "荣誉附件地址", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "honorLevel", value = "荣誉层级	1. 省级	2. 市级	3. 县级", required = false, paramType = "query", dataType="Integer"),
                @ApiImplicitParam(name = "honorName", value = "荣誉名称", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "household", value = "户籍", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "id", value = "主键", required = false, paramType = "query", dataType="Long"),
                @ApiImplicitParam(name = "idcard", value = "身份证号", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "industry", value = "所在行业", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "iphone", value = "手机号码", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "joinDate", value = "入委时间", required = false, paramType = "query", dataType="Date"),
                @ApiImplicitParam(name = "leagueOffice", value = "青联职务", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "leaveDate", value = "出委时间", required = false, paramType = "query", dataType="Date"),
                @ApiImplicitParam(name = "leaveReason", value = "离开原因", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "location", value = "所在地", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "name", value = "姓名", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "nation", value = "民族", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "orgOffice", value = "职级", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "orgPosition", value = "政府所在单位和职务", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "orgTitle", value = "职称", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "organization", value = "所在政府单位", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "origin", value = "籍贯", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "picture", value = "个人照片地址", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "politicalStatus", value = "政治面貌", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "position", value = "公司职务", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "socialOffice", value = "社会职务", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "vocationId", value = "职业（字典）", required = false, paramType = "query", dataType="Long"),
                @ApiImplicitParam(name = "workplace", value = "工作所在地", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeagueCommissinor leagueCommissinor) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<LeagueCommissinor> list = leagueCommissinorService.selectLeagueCommissinorList(leagueCommissinor);
        buildTableInfo(list, resultMap);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
    * 查询详情【请填写功能名称】
    *
    * @param id
    * @return
    */
    @ApiOperation(value = "查询详情", notes = "查询详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = false, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail")
    public Object getById(@RequestParam(required = false) Long id) {
        Map<String, Object> resultMap = new HashMap<>();

        // 获取用户id
        if(id == null){
            LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());
            id = leagueCommissinor.getId();
        }

        LeagueCommissinor leagueCommissinor = leagueCommissinorService.getById(id);
        if(leagueCommissinor == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", leagueCommissinor);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    synchronized public BusiResult add(LeagueCommissinor leagueCommissinor) {
        // 判断委员是否存在
        String employeeCode = leagueCommissinor.getEmployeeCode();
        long leagueCount = leagueCommissinorService.leagueCommissinorCount(employeeCode);
        if(leagueCount > 0){
            return BusiResult.error("新增失败，失败原因：该委员已存在");
        }
        leagueCommissinor.setCreater(JwtFilter.getUserId());
        int isSuccess = leagueCommissinorService.insertLeagueCommissinor(leagueCommissinor);
        if(isSuccess == 0){
            return BusiResult.error("新增失败，失败原因：数据库拒绝新增操作");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 修改记录【请填写功能名称】
     */
    @ApiOperation(value = "修改记录", notes = "修改记录", httpMethod = "POST")
    @ApiImplicitParams({
                        @ApiImplicitParam(name = "address", value = "家庭住址", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "birthday", value = "出生日期", required = false, paramType = "query", dataType="Date"),
                        @ApiImplicitParam(name = "campus", value = "毕业院校", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "company", value = "所在公司", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "creater", value = "创建人（浙政钉id）", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "degree", value = "学位", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "deputyCppcc", value = "市级以上政协委员的情况", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "deputyNpc", value = "市级以上人大代表情况", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "deputyParty", value = "市级以上党代表的情况", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "education", value = "教育情况	1. 中专/高中	2. 专科	3. 本科	4. 硕士研究生	5. 博士", required = false, paramType = "query", dataType="Integer"),
                        @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "gender", value = "性别", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "honorFile", value = "荣誉附件地址", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "honorLevel", value = "荣誉层级	1. 省级	2. 市级	3. 县级", required = false, paramType = "query", dataType="Integer"),
                        @ApiImplicitParam(name = "honorName", value = "荣誉名称", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "household", value = "户籍", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType="Long"),
                        @ApiImplicitParam(name = "idcard", value = "身份证号", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "industry", value = "所在行业", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "iphone", value = "手机号码", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "joinDate", value = "入委时间", required = false, paramType = "query", dataType="Date"),
                        @ApiImplicitParam(name = "leagueOffice", value = "青联职务", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "leaveDate", value = "出委时间", required = false, paramType = "query", dataType="Date"),
                        @ApiImplicitParam(name = "leaveReason", value = "离开原因", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "location", value = "所在地", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "name", value = "姓名", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "nation", value = "民族", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "orgOffice", value = "职务", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "orgPosition", value = "政府所在单位和职务", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "orgTitle", value = "职称", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "organization", value = "所在政府单位", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "origin", value = "籍贯", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "picture", value = "个人照片地址", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "politicalStatus", value = "政治面貌", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "position", value = "公司职务", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "socialOffice", value = "社会职务", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "vocationId", value = "职业（字典）", required = false, paramType = "query", dataType="Long"),
                        @ApiImplicitParam(name = "workplace", value = "工作所在地", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore LeagueCommissinor leagueCommissinor) {
        int isSuccess = leagueCommissinorService.updateLeagueCommissinor(leagueCommissinor);
        if(isSuccess == 0){
            return BusiResult.error("修改失败");
        }
        return BusiResult.success("修改成功");
    }

    /**
     * 删除记录（物理删除）【请填写功能名称】
     */
    synchronized private BusiResult remove(Long id) {
        boolean isSuccess = leagueCommissinorService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

    /**
     * 逻辑删除
     * @param employeeCode
     * @return
     */
    private BusiResult removeLogic(String employeeCode) {
        boolean isSuccess = leagueCommissinorService.removeLogic(employeeCode);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

    /**
     * 通过接口 - 批导入
     */
    @ApiOperation(value = "批导入-接口导入", notes = "批导入-接口导入", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "导入数据", required = true, paramType = "query", dataType="Object"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @InsertBatchParams({
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "提名单位", fieldName = "group_name"),
            @InsertBatchParam(value = Check.NotEmpty, fieldNameZh = "姓名", fieldName = "name"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "性别", fieldName = "gender"),
            @InsertBatchParam(value = Check.DateOrEmpty, fieldNameZh = "出生年月", fieldName = "birthday"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "身份证号码", fieldName = "idcard"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "籍贯", fieldName = "origin"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "界别", fieldName = "industry"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "户籍所在地", fieldName = "household"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "工作所在地", fieldName = "workplace"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "民族", fieldName = "nation"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "政治面貌", fieldName = "political_status"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "学历", fieldName = "education"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "学位", fieldName = "degree"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "工作单位及职务", fieldName = "org_position"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "联系方式", fieldName = "iphone"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "专业技术职称", fieldName = "org_title"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "职级", fieldName = "org_office"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "职业", fieldName = "vocation"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "主要社会职务", fieldName = "social_office"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "重要奖项及荣誉", fieldName = "honor_name"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "担任市级以上党代表的情况", fieldName = "deputy_party"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "担任市级以上人大代表的情况", fieldName = "deputy_npc"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "担任市级以上政协委员的情况", fieldName = "deputy_cppcc"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "备注", fieldName = "remark"),
            @InsertBatchParam(value = Check.IsEnum, fieldNameZh = "是否留任", fieldName = "is_reelect", express = "com.wzxc.busi.en.IsReelect"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "青联职务", fieldName = "league_office"),
    })
    @PostMapping(value = "/batch")
    public BusiResult insertBatch(@RequestParam("file") MultipartFile file) throws Exception {
        Method method = ReflectUtils.getAccessibleMethodByName(this, "insertBatch", 1);
        Annotation annotation = ReflectUtils.getAnntationByMethod(method, "InsertBatchParams");
        InsertBatchParam[] insertBatchParams = ((InsertBatchParams) annotation).value();
        if (file.isEmpty()) {
            return BusiResult.error("上传失败，失败原因：文件未空");
        }
        // 获取表格中的中文名称
        List<String> fieldNameZhList = ExcelUtils.exportTitleListFromExcel(file);
        // 获取字段名称
        List<MyEntry<String, Integer>> fieldNameList = ExcelUtils.getFieldNameList(fieldNameZhList, insertBatchParams);
        // 获取数据
        List<Map<String, Object>> contentList = ExcelUtils.exportContentListFromExcel(file, fieldNameList, insertBatchParams);
        if(contentList == null || contentList.size() == 0){
            return BusiResult.success("导入成功");
        }
        for(Map<String, Object> map : contentList){
            LeagueCommissinor leaueCommissinor = (LeagueCommissinor) MapDataUtil.getObjectFromMap(map, LeagueCommissinor.class);
            leaueCommissinor.setCreater(JwtFilter.getUserId());
            leagueCommissinorService.insertLeagueCommissinor(leaueCommissinor);
            LeagueElect leagueElect = (LeagueElect) MapDataUtil.getObjectFromMap(map, LeagueElect.class);
            leagueElect.setCommisinorId(leaueCommissinor.getId());
            leagueElect.setYear("2021");
            leagueElect.setCommisinorName(leaueCommissinor.getName());
            leagueElectService.insertLeagueElect(leagueElect);
        }

        return BusiResult.success("批导入成功");
    }


}
