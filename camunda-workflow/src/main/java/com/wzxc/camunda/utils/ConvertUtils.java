package com.wzxc.camunda.utils;

import com.wzxc.camunda.persistence.entity.MyTaskEntity;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConvertUtils {

    @Autowired
    private RuntimeService runtimeService;

    public List<MyTaskEntity> convertTaskList(List<Task> tasks){
        List<MyTaskEntity> taskEntities = new ArrayList<>();
        for(Task task : tasks){
            MyTaskEntity taskEntity = new MyTaskEntity();
            taskEntity.setAssignee(task.getAssignee());
            taskEntity.setCreated(task.getCreateTime());
            taskEntity.setDescription((String) runtimeService.getVariable(task.getExecutionId(), "title"));
            Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
            taskEntity.setCamundaFormRef(variables);
            taskEntity.setExecutionId(task.getExecutionId());
            taskEntity.setProcessDefinitionId(task.getProcessDefinitionId());
            taskEntity.setId(task.getId());
            taskEntity.setName(task.getName());
            taskEntity.setTaskDefinitionKey(task.getTaskDefinitionKey());
            taskEntity.setProcessInstanceId(task.getProcessInstanceId());
            taskEntities.add(taskEntity);
        }
        return taskEntities;
    }
}
