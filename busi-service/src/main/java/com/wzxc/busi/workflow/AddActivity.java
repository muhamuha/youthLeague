package com.wzxc.busi.workflow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueActivity;
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
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/workflow/activity/add")
@Api(tags="活动新增流程")
public class AddActivity {

    private static final String processKey = "Process_youthLeague_890";

    // 草稿箱任务key
    private static final String manuscriptTaskKey = "Activity_0t6e3hz";
    // 审批任务key
    private static final String approvalTaskKey = "Activity_0ltowbc";

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
            @ApiImplicitParam(name = "activityBegin", value = "活动开始时间", required = true, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "activityEnd", value = "活动结束时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "activityType", value = "活动类型（字典表）", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "address", value = "活动地点", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "content", value = "活动内容或者行程安排", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "hostman", value = "主办人id", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "nickname", value = "活动别名", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "rangeIndustry", value = "行业范围", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "rangeRegion", value = "地区范围", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "signBegin", value = "签到开始时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "signEnd", value = "签到结束时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "lat", value = "活动地点的纬度", required = false, paramType = "query", dataType="Double"),
            @ApiImplicitParam(name = "lon", value = "活动地点的经度", required = false, paramType = "query", dataType="Double"),
            @ApiImplicitParam(name = "isSign", value = "是否打卡", required = true, paramType = "query", dataType="Boolean"),
            @ApiImplicitParam(name = "signRange", value = "打卡范围（单位：米）", required = false, paramType = "query", dataType="Integer"),
            @ApiImplicitParam(name = "score", value = "积分", required = false, paramType = "query", dataType="Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/begin")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.activityType", msg = "缺少活动类型（activityType）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.hostman", msg = "缺少主办人id（hostman）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.nickname", msg = "缺少活动别名（nickname）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.activityBegin", msg = "缺少活动开始时间（activityBegin）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.address", msg = "缺少活动地点（address）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.isSign", msg = "缺少是否打卡（isSign）"),
    })
    synchronized public BusiResult begin(@ApiIgnore @RequestBody LeagueActivity leagueActivity){
        // 判断是否打卡
        if(leagueActivity.getIsSign() && (leagueActivity.getLon() == null ||
                        leagueActivity.getLat() == null ||
                        leagueActivity.getSignBegin() == null ||
                        leagueActivity.getSignEnd() == null ||
                        leagueActivity.getSignRange() == null)){
            return BusiResult.error("缺少打卡必填参数");
        }
        // 创建流程
        String employeeCode = JwtFilter.getUserId();
        leagueActivity.setCreater(employeeCode);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(leagueActivity);
        Map<String, Object> variables = JsonUtils.parseObject(jsonObject);
        Map<String, Object> var = new HashMap<>();
        var.put("leagueActivity", variables);
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
            @ApiImplicitParam(name = "activityBegin", value = "活动开始时间", required = true, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "activityEnd", value = "活动结束时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "activityType", value = "活动类型（字典表）", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "address", value = "活动地点", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "content", value = "活动内容或者行程安排", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "hostman", value = "主办人id", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "nickname", value = "活动别名", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "rangeIndustry", value = "行业范围", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "rangeRegion", value = "地区范围", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "signBegin", value = "签到开始时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "signEnd", value = "签到结束时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "lat", value = "活动地点的纬度", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "lon", value = "活动地点的经度", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "creater", value = "创建人", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "isSign", value = "是否打卡", required = true, paramType = "query", dataType="Boolean"),
            @ApiImplicitParam(name = "signRange", value = "打卡范围（单位：米）", required = false, paramType = "query", dataType="Integer"),
            @ApiImplicitParam(name = "score", value = "积分", required = false, paramType = "query", dataType="Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.activityType", msg = "缺少活动类型（activityType）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.hostman", msg = "缺少主办人id（hostman）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.nickname", msg = "缺少活动别名（nickname）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.activityBegin", msg = "缺少活动开始时间（activityBegin）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.address", msg = "缺少活动地点（address）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.isSign", msg = "缺少是否打卡（isSign）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.creater", msg = "缺少创建人（creater）"),
    })
    @PostMapping("/manuscript/{id}/update")
    public BusiResult updateManuscript(@PathVariable("id") String id, @ApiIgnore @MultiRequestBody LeagueActivity leagueActivity){
        // 判断是否打卡
        if(leagueActivity.getIsSign() && (leagueActivity.getLon() == null ||
                leagueActivity.getLat() == null ||
                leagueActivity.getSignBegin() == null ||
                leagueActivity.getSignEnd() == null ||
                leagueActivity.getSignRange() == null)){
            return BusiResult.error("缺少打卡必填参数");
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(leagueActivity);
        Map<String, Object> variables = JsonUtils.parseObject(jsonObject);
        Map<String, Object> var = new HashMap<>();
        var.put("leagueActivity", variables);
        taskService.setVariables(id, var);
        return BusiResult.success();
    }

    /**
     * 删除草稿
     * @param id：任务id
     * @return
     */
    @ApiOperation(value = "删除草稿", notes = "删除草稿", httpMethod = "GET")
    @ApiImplicitParams({})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/manuscript/{id}/delete")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "id", msg = "缺少草稿id"),
    })
    public BusiResult deleteManuscript(@PathVariable("id") String id){
        String processInstanceId = taskService.createTaskQuery().taskId(id).singleResult().getProcessInstanceId();
        runtimeService.deleteProcessInstance(processInstanceId, "添加活动流程 - 草稿删除");
        return BusiResult.success("删除成功");
    }

    /**
     * 草稿箱发起审批申请
     * @param id：任务id
     * @param variables
     * @return
     */
    @ApiOperation(value = "提交草稿", notes = "提交草稿", httpMethod = "GET")
    @ApiImplicitParams({})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
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
            @ApiResponse(code = 13000, message = "OK"),
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
    @ApiOperation(value = "审批活动", notes = "审批活动", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isApproval", value = "是否通过审批", required = true, paramType = "query", dataType="boolean"),
            @ApiImplicitParam(name = "comment", value = "审批内容", required = false, paramType = "query", dataType="String"),
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
            @ApiResponse(code = 13000, message = "OK"),
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
        List<LeagueActivity> historicVariableInstances = new ArrayList<>();
        for(HistoricProcessInstance historicProcessInstance : approvaledList){
            Map<String, Object> variables = new HashMap<>();
            HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .variableName("leagueActivity")
                    .singleResult();
            JSONObject o = (JSONObject) JSONObject.toJSON(historicVariableInstance.getValue());
            LeagueActivity leagueActivity = JSONObject.toJavaObject(o, LeagueActivity.class);
            historicVariableInstances.add(leagueActivity);
        }
        resultMap.put("list", historicVariableInstances);
        resultMap.put("count", count);
        return BusiResult.success("查询成功", resultMap);
    }
}
