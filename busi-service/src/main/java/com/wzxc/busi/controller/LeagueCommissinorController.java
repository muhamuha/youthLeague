package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueCommissinor;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.camunda.bpm.engine.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
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
public class LeagueCommissinorController extends BaseController {

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

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
                @ApiImplicitParam(name = "industryId", value = "所在行业（字典表）", required = false, paramType = "query", dataType="Long"),
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
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
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
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
                        @ApiImplicitParam(name = "address", value = "家庭住址", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "birthday", value = "出生日期", required = false, paramType = "query", dataType="Date"),
                        @ApiImplicitParam(name = "campus", value = "毕业院校", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "company", value = "所在公司", required = false, paramType = "query", dataType="String"),
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
                        @ApiImplicitParam(name = "idcard", value = "身份证号", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "industryId", value = "所在行业（字典表）", required = false, paramType = "query", dataType="Long"),
                        @ApiImplicitParam(name = "iphone", value = "手机号码", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "joinDate", value = "入委时间", required = false, paramType = "query", dataType="Date"),
                        @ApiImplicitParam(name = "leagueOffice", value = "青联职务", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "leaveDate", value = "出委时间", required = false, paramType = "query", dataType="Date"),
                        @ApiImplicitParam(name = "leaveReason", value = "离开原因", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "location", value = "所在地", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query", dataType="String"),
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
                        @ApiImplicitParam(name = "employeeCode", value = "浙政钉code", required = true, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "instanceId", value = "流程id", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.employeeCode", msg = "缺少浙政钉code（employeeCode）"),
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.name", msg = "缺少姓名字段（name）"),
            @CheckParam(value = Check.NotNull, argName = "instanceId", msg = "缺少实例id"),
    })
    @PostMapping("/add/{instanceId}")
    synchronized public BusiResult add(@PathVariable("instanceId") String instanceId, @RequestBody @ApiIgnore LeagueCommissinor leagueCommissinor) {
        // 判断委员是否存在
        String employeeCode = leagueCommissinor.getEmployeeCode();
        long leagueCount = leagueCommissinorService.leagueCommissinorCount(employeeCode);
        if(leagueCount > 0){
            return BusiResult.error("新增失败，失败原因：该委员已存在");
        }
        // 判断对应的实例id是否已经完结且审批通过
        long count = historyService.createHistoricProcessInstanceQuery().completed().processInstanceBusinessKey(employeeCode).processInstanceId(instanceId).count();
        if(count == 0){
            return BusiResult.error("新增失败，失败原因：未找到工单信息");
        }
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
                        @ApiImplicitParam(name = "industryId", value = "所在行业（字典表）", required = false, paramType = "query", dataType="Long"),
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
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "删除记录（物理删除）", notes = "删除记录（物理删除）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/remove/{id}")
    public BusiResult remove(@PathVariable Long id) {
        boolean isSuccess = leagueCommissinorService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
