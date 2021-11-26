package com.wzxc.busi.workflow.serivce;

import com.alibaba.fastjson.JSONObject;
import com.wzxc.busi.controller.LeagueCommissinorController;
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
public class AddCommissinorTask implements JavaDelegate {

    @Autowired
    private LeagueCommissinorController leagueCommissinorController;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        try{
            LeagueCommissinor leagueCommissinor = JSONObject.parseObject(JSONObject.toJSONString(delegateExecution.getVariable("leagueCommissinor")), LeagueCommissinor.class);
            BusiResult re = leagueCommissinorController.add(leagueCommissinor);
            if(re.get(BusiResult.CODE_TAG) != BusiResult.Type.SUCCESS){
                log.error(re.get(BusiResult.MSG_TAG).toString());
            }
        } catch (Exception e){
            runtimeService.setVariable(processInstanceId, "addErrorMessage", e.getMessage());
            runtimeService.setVariable(processInstanceId, "addError", true);
            throw new Exception();
        }

    }
}
