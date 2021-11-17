package com.wzxc.camunda.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendTaskDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("Message")
                .processInstanceBusinessKey("MessageKey")
                .correlate();
    }
}
