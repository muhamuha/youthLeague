package com.wzxc.busi.workflow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.camunda.wrapper.MyProcessDefinition;
import com.wzxc.camunda.wrapper.MyTask;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.camunda.JsonUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.webservice.shiro.JwtFilter;
import com.wzxc.webservice.shiro.JwtUtil;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/workflow/commissinor/add")
@Api(tags="委员新增流程")
public class AddCommissinor {

    private static final String processKey = "process_888";

    @Autowired
    private MyTask myTask;
    @Autowired
    private MyProcessDefinition myProcessDefinition;

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    @ApiOperation(value = "创建草稿", notes = "创建草稿", httpMethod = "GET")
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
    @GetMapping("/begin")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.employeeCode", msg = "缺少浙政钉code"),
    })
    synchronized public BusiResult begin(@Ignore @RequestBody LeagueCommissinor leagueCommissinor){
        // 判断该浙政钉code是否已经发起审批
        String employeeCode = leagueCommissinor.getEmployeeCode();
        long count = historyService.createHistoricProcessInstanceQuery().active().processInstanceBusinessKey(employeeCode).count();
        if(count > 0){
            return BusiResult.error("流程创建失败，失败原因：已存在该浙政钉工单");
        }
        // 判断该浙政钉code是否已经存在
        long leagueCount = leagueCommissinorService.leagueCommissinorCount(employeeCode);
        if(leagueCount > 0){
            return BusiResult.error("流程创建失败，失败原因：该用户已存在");
        }
        // 创建流程
        Map<String, Object> variables = JsonUtils.parseObject(JSON.parseObject(JSON.toJSONString(leagueCommissinor)));
        myProcessDefinition.submitByForm(processKey, employeeCode, variables);
        return BusiResult.success("流程创建成功");
    }

    /**
     * 查询草稿箱列表
     * @param processkey
     * @param assignee
     * @return
     */
    @ApiOperation(value = "查询草稿箱列表", notes = "查询草稿箱列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/manuscript/list")
    public BusiResult getManuscriptList(){
        String assignee = JwtFilter.getUserId();
        Map<String, Object> resultMap = myTask.getAssigneeTaskByProcesskeyAndAssignee(processKey, assignee);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 修改草稿箱内容
     * @param leagueCommissinor
     * @return
     */
    @ApiOperation(value = "修改草稿", notes = "修改草稿", httpMethod = "GET")
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
    @GetMapping("/manuscript/{id}/update")
    public BusiResult updateManuscript(@PathVariable("id") String id, @Ignore @RequestBody LeagueCommissinor leagueCommissinor){
        return BusiResult.success();
    }

    /**
     * 草稿箱发起审批申请
     * @param id：任务id
     * @param variables
     * @return
     */
    @ApiOperation(value = "提交草稿", notes = "提交草稿", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "草稿id", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/{id}/manuscript")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "id", msg = "缺少草稿id"),
    })
    public BusiResult manuscript(@PathVariable String id){
        myTask.complete(id, null);
        return BusiResult.success("发起成功");
    }

    /**
     * 查询审批列表
     * @param processkey
     * @param candidateUser
     * @return
     */
    @ApiOperation(value = "查询审批列表", notes = "查询审批列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/approval/list")
    public BusiResult getApprovalList(){
        String candidateUser = JwtFilter.getUserId();
        Map<String, Object> unassignedTaskByProcesskeyAndAssignee = myTask.getUnassignedTaskByProcesskeyAndAssignee(processKey, candidateUser);
        return BusiResult.success("查询成功", unassignedTaskByProcesskeyAndAssignee);
    }

    /**
     * 审批
     * @param id：任务id
     * @return
     */
    @ApiOperation(value = "查询审批列表", notes = "查询审批列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "审批id", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isApproval", value = "是否通过审批", required = true, paramType = "query", dataType="boolean"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/{id}/approval")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "id", msg = "缺少审批id"),
            @CheckParam(value = Check.NotNull, argName = "isApproval", msg = "是否通过审批"),
    })
    public BusiResult approval(@PathVariable String id, Boolean isApproval){
        Map<String, Object> resultMap = new HashMap<>();
        // 认领任务
        myTask.claim(id, JwtFilter.getUserId());
        // 审批操作
        taskService.setVariable(id, "approval", isApproval);
        taskService.complete(id);
        resultMap.put("isApproval", isApproval);
        return BusiResult.success("审批成功", resultMap);
    }
}
