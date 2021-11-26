package com.wzxc.camunda.wrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.wzxc.camunda.utils.ConvertUtils;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.core.page.PageDomain;
import com.wzxc.common.core.page.TableSupport;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.camunda.JsonUtils;
import com.wzxc.common.utils.camunda.RequestPathUtils;
import com.wzxc.common.utils.http.HttpsUtils;
import com.wzxc.common.utils.sql.SqlUtil;
import com.wzxc.common.validate.Check;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.TaskServiceImpl;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.rest.dto.task.TaskQueryDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MyTask extends BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ConvertUtils convertUtils;

    @Autowired
    private TaskService taskService;

    private Integer firstResult = 0, maxResults = 20;
    private RequestPathUtils requestPathUtils = new RequestPathUtils();

    /**
     * 获取任务列表（已认领）
     *
     * @param processkey：流程key
     * @param assignee：受理人
     * @return
     */
    public Map<String, Object> getAssigneeTaskByTaskKeyAndAssignee(String processkey, String taskKey, String assignee) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取总数
        long count = taskService.createTaskQuery().taskAssigned().taskAssignee(assignee).processDefinitionKey(processkey).taskDefinitionKey(taskKey).count();
        // 获取列表
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            firstResult = (pageNum - 1) * pageSize;
            maxResults = pageSize;
        }
        List<Task> tasks = taskService.createTaskQuery().taskAssigned().taskAssignee(assignee).processDefinitionKey(processkey).taskDefinitionKey(taskKey).listPage(firstResult, maxResults);
        List<com.wzxc.camunda.persistence.entity.MyTaskEntity> taskEntities = convertUtils.convertTaskList(tasks);
        resultMap.put("count", count);
        resultMap.put("list", taskEntities);
        return resultMap;
    }

    /**
     * 获取未认领和认领的任务
     *
     * @param processkey：流程key
     * @param candidateUser：用户id
     * @return
     */
    public Map<String, Object> unassignedTaskByProcesskeyAndAssignee(String processkey, String taskKey, String candidateUser) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取总数
        long count = taskService.createTaskQuery()
                .or()
                .taskCandidateUser(candidateUser)
                .taskAssignee(candidateUser)
                .endOr()
                .processDefinitionKey(processkey)
                .taskDefinitionKey(taskKey)
                .count();
        // 获取列表
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            firstResult = (pageNum - 1) * pageSize;
            maxResults = pageSize;
        }
        List<Task> tasks = taskService.createTaskQuery()
                .or()
                .taskCandidateUser(candidateUser)
                .taskAssignee(candidateUser)
                .endOr()
                .processDefinitionKey(processkey)
                .taskDefinitionKey(taskKey)
                .listPage(firstResult, maxResults);
        List<com.wzxc.camunda.persistence.entity.MyTaskEntity> taskEntities = convertUtils.convertTaskList(tasks);
        resultMap.put("count", count);
        resultMap.put("list", taskEntities);
        return resultMap;
    }

    /**
     * 认领任务
     * @param id：任务id
     * @return
     */
    public void claim(String id, String userid){
        taskService.claim(id, userid);
    }

    /**
     * 放弃任务
     * @param id：任务id
     * @return
     */
    public void unClaim(String id){
        taskService.setAssignee(id, null);
    }

    /**
     * 报表形式完成任务
     *
     * @param id：任务id
     * @param variables：报表数据
     * @return
     */
    @PostMapping("/{id}/submit-form")
    public BusiResult completeBySubmitForm(@PathVariable String id, @RequestBody JSONObject variables) {
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject var = JsonUtils.parseVariables(variables);
        JSONObject variable0 = new JSONObject();
        variable0.put("variables", var);
        taskService.complete(id, variable0);
//        JSONObject postResult = HttpsUtils.doPost(requestPathUtils.getRequestUrl(request), variable0);
//        resultMap.put("data", postResult);
        return BusiResult.success("任务完成");
    }

    /**
     * 完成任务
     *
     * @param id：任务id
     * @param variables：报表数据
     * @return
     */
    public void complete(String id, Map<String, Object> variables) {
        Map<String, Object> resultMap = new HashMap<>();
        if(variables != null){
            taskService.complete(id, variables);
        } else{
            taskService.complete(id);
        }
    }

    /**
     * 获取任务绑定的报表
     *
     * @param id：任务id
     * @return
     */
    @GetMapping("/{id}/variables")
    public BusiResult getVariables(@PathVariable String id) {
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject getResult = HttpsUtils.doGet(requestPathUtils.getRequestUrl(request));
        resultMap.put("data", getResult);
        return BusiResult.success("查询成功", resultMap);
    }

}
