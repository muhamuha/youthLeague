package com.wzxc.busi.workflow.serivce;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wzxc.busi.controller.LeagueCommissinorController;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.core.domain.BusiResult;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteCommissinorTask implements JavaDelegate {

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        try{
            LeagueCommissinor leagueCommissinor = JSONObject.parseObject(JSONObject.toJSONString(delegateExecution.getVariable("leagueCommissinor")), LeagueCommissinor.class);
            leagueCommissinorService.removeLogic(leagueCommissinor.getEmployeeCode());
        } catch (Exception e){
            runtimeService.setVariable(processInstanceId, "deleteErrorMessage", e.getMessage());
            runtimeService.setVariable(processInstanceId, "deleteError", true);
            throw new Exception();
        }
    }
}
