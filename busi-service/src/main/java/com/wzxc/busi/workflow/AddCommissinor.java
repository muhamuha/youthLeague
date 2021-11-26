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
import com.wzxc.common.annotation.MultiRequestBody;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.core.page.PageDomain;
import com.wzxc.common.core.page.TableSupport;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.camunda.JsonUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.webservice.shiro.JwtFilter;
import com.wzxc.webservice.shiro.JwtUtil;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/workflow/commissinor/add")
@Api(tags="委员新增流程")
public class AddCommissinor {

    private static final String processKey = "process_888";

    // 草稿箱任务key
    private static final String manuscriptTaskKey = "Activity_1xshv7w";
    // 审批任务key
    private static final String approvalTaskKey = "Activity_1rcpamm";

    @Autowired
    private MyTask myTask;
    @Autowired
    private MyProcessDefinition myProcessDefinition;

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    @ApiOperation(value = "创建草稿", notes = "创建草稿", httpMethod = "POST")
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
            @ApiImplicitParam(name = "employeeCode", value = "浙政钉code", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/begin")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.employeeCode", msg = "缺少浙政钉code"),
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.name", msg = "缺少姓名"),
    })
    synchronized public BusiResult begin(@ApiIgnore @RequestBody LeagueCommissinor leagueCommissinor){
        // 判断该浙政钉code是否已经发起审批
        String employeeCode = leagueCommissinor.getEmployeeCode();
        long count = historyService.createHistoricProcessInstanceQuery().active().processInstanceBusinessKey(employeeCode).count();
//        if(count > 0){
//            return BusiResult.error("流程创建失败，失败原因：已存在该浙政钉工单");
//        }
        // 判断该浙政钉code是否已经存在
        long leagueCount = leagueCommissinorService.leagueCommissinorCount(employeeCode);
        if(leagueCount > 0){
            return BusiResult.error("流程创建失败，失败原因：该用户已存在");
        }
        // 创建流程
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(leagueCommissinor);
        Map<String, Object> variables = JsonUtils.parseObject(jsonObject);
        Map<String, Object> var = new HashMap<>();
        var.put("leagueCommissinor", variables);
        myProcessDefinition.submitByForm(processKey, employeeCode, var);
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
        Map<String, Object> resultMap = myTask.getAssigneeTaskByTaskKeyAndAssignee(processKey, manuscriptTaskKey, assignee);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 修改草稿箱内容
     * @param leagueCommissinor
     * @return
     */
    @ApiOperation(value = "修改草稿", notes = "修改草稿", httpMethod = "POST")
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
            @ApiImplicitParam(name = "creater", value = "创建人", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.employeeCode", msg = "缺少浙政钉code"),
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.name", msg = "缺少姓名"),
            @CheckParam(value = Check.NotNull, argName = "leagueCommissinor.creater", msg = "缺少创建人"),
    })
    @PostMapping("/manuscript/{id}/update")
    public BusiResult updateManuscript(@PathVariable("id") String id, @ApiIgnore @MultiRequestBody LeagueCommissinor leagueCommissinor){
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(leagueCommissinor);
        Map<String, Object> variables = JsonUtils.parseObject(jsonObject);
        Map<String, Object> var = new HashMap<>();
        var.put("leagueCommissinor", variables);
        taskService.setVariables(id, var);
        return BusiResult.success();
    }

    /**
     * 删除草稿
     * @param id：任务id
     * @return
     */
    @ApiOperation(value = "删除草稿", notes = "删除草稿", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "草稿id", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/manuscript/{id}/delete")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "id", msg = "缺少草稿id"),
    })
    public BusiResult deleteManuscript(@PathVariable("id") String id){
        String processInstanceId = taskService.createTaskQuery().taskId(id).singleResult().getProcessInstanceId();
        runtimeService.deleteProcessInstance(processInstanceId, "添加委员流程 - 草稿删除");
        return BusiResult.success("删除成功");
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
        Map<String, Object> unassignedTaskByProcesskeyAndAssignee = myTask.unassignedTaskByProcesskeyAndAssignee(processKey, approvalTaskKey, candidateUser);
        return BusiResult.success("查询成功", unassignedTaskByProcesskeyAndAssignee);
    }

    /**
     * 审批
     * @param id：任务id
     * @return
     */
    @ApiOperation(value = "审批委员", notes = "审批委员", httpMethod = "GET")
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
            @CheckParam(value = Check.NotNull, argName = "isApproval", msg = "缺少“是否通过审批”字段"),
    })
    public BusiResult approval(@PathVariable String id, Boolean isApproval, String comment){
        Map<String, Object> resultMap = new HashMap<>();
        // 认领任务
        Task singleResult = taskService.createTaskQuery().taskId(id).singleResult();
        if(StringUtils.isNotEmpty(singleResult.getAssignee())){
            myTask.unClaim(id);
        }
        myTask.claim(id, JwtFilter.getUserId());
        // 审批操作
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        taskService.setVariable(id, "approval", isApproval);
        taskService.setVariable(id, "approvalPerson", JwtFilter.getUserId());
        if(!isApproval){
            comment = Optional.ofNullable(comment).orElseGet(String::new);
            taskService.setVariable(id, "comment", comment);
        }
        taskService.complete(id);
        return BusiResult.success("审批成功", resultMap);
    }

    /**
     * 获取已审批列表
     * @return
     */
    @ApiOperation(value = "已审批列表", notes = "已审批列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueCommissinor.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/approval/complete/list")
    public BusiResult getApprovaledList(){
        Map<String, Object> resultMap = new HashMap<>();
        int firstResult = 0, maxResults = 20;

        // 查询工单
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            firstResult = (pageNum - 1) * pageSize;
            maxResults = pageSize;
        }
        List<HistoricProcessInstance> approvaledList = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(processKey)
                .variableValueEquals("approvalPerson", JwtFilter.getUserId())
                .completed()
                .listPage(firstResult, maxResults);
        long count = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(processKey)
                .variableValueEquals("approvalPerson", JwtFilter.getUserId())
                .completed()
                .count();
        // 查询工单对应的委员信息
        List<LeagueCommissinor> historicVariableInstances = new ArrayList<>();
        for(HistoricProcessInstance historicProcessInstance : approvaledList){
            Map<String, Object> variables = new HashMap<>();
            HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .variableName("leagueCommissinor")
                    .singleResult();
            JSONObject o = (JSONObject) JSONObject.toJSON(historicVariableInstance.getValue());
            LeagueCommissinor leagueCommissinor = JSONObject.toJavaObject(o, LeagueCommissinor.class);
            historicVariableInstances.add(leagueCommissinor);
        }
        resultMap.put("list", historicVariableInstances);
        resultMap.put("count", count);
        return BusiResult.success("查询成功", resultMap);
    }

}
