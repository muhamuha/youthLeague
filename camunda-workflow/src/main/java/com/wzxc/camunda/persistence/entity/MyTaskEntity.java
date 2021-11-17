package com.wzxc.camunda.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class MyTaskEntity implements Serializable {
    String id;
    String name;
    String assignee;
    String owner;
    Date created;
    Date due;
    String followUp;
    String delegationState;
    String description;
    String executionId;
    String parentTaskId;
    Integer priority;
    String processDefinitionId;
    String processInstanceId;
    String caseExecutionId;
    String caseDefinitionId;
    String caseInstanceId;
    String taskDefinitionKey;
    Boolean suspended;
    String formKey;
    Map<String, Object> camundaFormRef;
}
