package com.wzxc.busi.workflow.serivce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.busi.controller.LeagueActivityController;
import com.wzxc.busi.controller.LeagueCommissinorController;
import com.wzxc.busi.vo.LeagueActivity;
import com.wzxc.common.core.domain.BusiResult;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddAcitivityTask implements JavaDelegate {

    @Autowired
    private LeagueActivityController leagueActivityController;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        try{
            JSONObject o = JSONObject.parseObject(JSON.toJSONStringWithDateFormat(delegateExecution.getVariable("leagueActivity"), "yyyy-MM-dd HH:mm:ss"));
            LeagueActivity leagueActivity = JSONObject.toJavaObject(o, LeagueActivity.class);
            BusiResult re = leagueActivityController.add(leagueActivity);
            if(!re.get(BusiResult.CODE_TAG).equals(BusiResult.Type.SUCCESS)){
                log.info(re.get(BusiResult.MSG_TAG).toString());
            }
        } catch (Exception e){
            runtimeService.setVariable(processInstanceId, "addErrorMessage", e.getMessage());
            runtimeService.setVariable(processInstanceId, "addError", true);
            throw new Exception();
        }
    }
}
