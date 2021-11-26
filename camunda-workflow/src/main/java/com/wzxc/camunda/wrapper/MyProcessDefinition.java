package com.wzxc.camunda.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.camunda.JsonUtils;
import com.wzxc.common.utils.camunda.RequestPathUtils;
import com.wzxc.common.utils.http.HttpsUtils;
import com.wzxc.webservice.shiro.JwtFilter;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.webapp.impl.security.auth.AuthenticationService;
import org.camunda.bpm.webapp.impl.security.auth.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class MyProcessDefinition {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;

    /**
     * 通过表单开启流程
     */
    public ProcessInstance submitByForm(String id, Map<String, Object> variables) {
        return submitByForm(id, null, variables);
    }
    public ProcessInstance submitByForm(String id, String bussinessKey, Map<String, Object> variables) {
        AuthenticationService authenticationService = new AuthenticationService();
        // 获取当前流程引擎的名称
        String engineName = ProcessEngines.getDefaultProcessEngine().getName();
        // 用户无密码登录
        UserAuthentication authentication = (UserAuthentication) authenticationService
                .createAuthenticate(engineName, JwtFilter.getUserId(), null, null);
//        log.info("userId --- " + JwtFilter.getUserId());
        // 设置当前用户为操作人
        identityService.setAuthenticatedUserId(authentication.getName());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(id, bussinessKey, variables);
        return processInstance;
    }

}
