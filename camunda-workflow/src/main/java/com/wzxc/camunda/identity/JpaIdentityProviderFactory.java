package com.wzxc.camunda.identity;

import com.wzxc.camunda.identity.query.UserIdentityQuery;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

public class JpaIdentityProviderFactory implements SessionFactory {

    protected UserIdentityQuery userIdentityService;

    public JpaIdentityProviderFactory(UserIdentityQuery userIdentityService) {
        super();
        this.userIdentityService = userIdentityService;
    }

    @Override
    public Class<?> getSessionType() {
        return JpaIdentityProviderSession.class;
    }

    @Override
    public Session openSession() {
        return new JpaIdentityProviderSession(userIdentityService);
    }

}
