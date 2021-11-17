package com.wzxc.camunda.listener;

import com.wzxc.webservice.shiro.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * 指定受理人
 */
@Slf4j
public class DelegateListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("assignee", JwtFilter.getUserId());
    }
}
