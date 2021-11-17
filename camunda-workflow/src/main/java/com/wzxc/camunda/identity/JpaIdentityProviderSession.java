package com.wzxc.camunda.identity;

import com.wzxc.camunda.identity.query.UserIdentityQuery;
import com.wzxc.camunda.identity.query.JpaGroupQueryImpl;
import com.wzxc.camunda.identity.query.JpaTenantQueryImpl;
import com.wzxc.camunda.identity.query.JpaUserQueryImpl;
import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.*;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.identity.IdentityOperationResult;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.identity.WritableIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class JpaIdentityProviderSession extends AbstractManager implements ReadOnlyIdentityProvider, WritableIdentityProvider {

    protected Logger LOGGER = LoggerFactory.getLogger(JpaIdentityProviderSession.class);

    protected UserIdentityQuery userIdentityService;

    public JpaIdentityProviderSession(UserIdentityQuery userIdentityService) {
        super();
        this.userIdentityService = userIdentityService;
    }

    // Session Lifecycle //////////////////////////////////

    @Override
    public void flush() {
        // nothing to do
    }

    @Override
    public void close() {
        // nothing to do
    }

    // Users /////////////////////////////////////////////////

    @Override
    public UserEntity findUserById(String userId) {
        return (UserEntity) createUserQuery(org.camunda.bpm.engine.impl.context.Context.getCommandContext())
                .userId(userId)
                .singleResult();
    }

    @Override
    public UserQuery createUserQuery() {
        return new JpaUserQueryImpl(org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration()
                .getCommandExecutorTxRequired());
    }

    @Override
    public UserQuery createUserQuery(CommandContext commandContext) {
        return new JpaUserQueryImpl();
    }

    @Override
    public NativeUserQuery createNativeUserQuery() {
        throw new BadUserRequestException(
                "Native user queries are not supported for the JPA identity service provider.");
    }

    public long findUserCountByQueryCriteria(JpaUserQueryImpl query) {
        return userIdentityService.findUsersCount();
    }

    public List<User> findUserByQueryCriteria(JpaUserQueryImpl query) {
        return userIdentityService.findUsers(query);
    }

    @Override
    public boolean checkPassword(String userId, String password) {
        // prevent a null password
        if (password == null) {
            return false;
        }
        // engine can't work without users
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        boolean isOk = userIdentityService.checkPassword(userId, password);
        LOGGER.debug(String.format("Checking password for %s : %b", userId, isOk));
        return isOk;
    }

    @Override
    public User createNewUser(String s) {
        throw new BadUserRequestException(
                "The operation of create new user is not supported for the JPA identity service provider.");
    }

    @Override
    public IdentityOperationResult saveUser(User user) {
        throw new BadUserRequestException(
                "The operation of save user is not supported for the JPA identity service provider.");
    }

    @Override
    public IdentityOperationResult deleteUser(String s) {
        throw new BadUserRequestException(
                "The operation of delete user is not supported for the JPA identity service provider.");
    }

    @Override
    public IdentityOperationResult unlockUser(String userId) {
        UserEntity user = this.findUserById(userId);
        return user != null ? this.unlockUser(user) : new IdentityOperationResult((Serializable) null, "none");
    }

    protected IdentityOperationResult unlockUser(UserEntity user) {
        if (user.getAttempts() <= 0 && user.getLockExpirationTime() == null) {
            return new IdentityOperationResult(user, "none");
        } else {
            this.getIdentityInfoManager().updateUserLock(user, 0, (Date) null);
            return new IdentityOperationResult(user, "unlock");
        }
    }

    // Groups ///////////////////////////////////////////////

    @Override
    public Group findGroupById(String groupId) {
        return createGroupQuery(org.camunda.bpm.engine.impl.context.Context.getCommandContext())
                .groupId(groupId)
                .singleResult();
    }

    @Override
    public GroupQuery createGroupQuery() {
        return new JpaGroupQueryImpl(org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration()
                .getCommandExecutorTxRequired());
    }

    @Override
    public GroupQuery createGroupQuery(CommandContext commandContext) {
        return new JpaGroupQueryImpl();
    }

    public long findGroupCountByQueryCriteria(JpaGroupQueryImpl query) {
        return userIdentityService.findGroups(query).size();
    }

    public List<Group> findGroupByQueryCriteria(JpaGroupQueryImpl query) {
        return userIdentityService.findGroups(query);
    }

    @Override
    public Group createNewGroup(String groupId) {
        this.checkAuthorization(Permissions.CREATE, Resources.GROUP, (String) null);
        return new GroupEntity(groupId);
    }

    @Override
    public IdentityOperationResult saveGroup(Group group) {
        GroupEntity groupEntity = (GroupEntity) group;
        String operation = null;
        if (groupEntity.getRevision() == 0) {
            operation = "create";
            this.checkAuthorization(Permissions.CREATE, Resources.GROUP, (String) null);
            userIdentityService.insertGroup(groupEntity);
            this.createDefaultAuthorizations(group);
        } else {
            operation = "update";
            this.checkAuthorization(Permissions.UPDATE, Resources.GROUP, group.getId());
            userIdentityService.mergeGroup(groupEntity);
        }

        return new IdentityOperationResult(groupEntity, operation);
    }

    @Override
    public IdentityOperationResult deleteGroup(String id) {
        boolean isSuccess = userIdentityService.deleteGroup(id);
        return new IdentityOperationResult(isSuccess, "delete");
    }

    // Tenants ////////////////////////////////////////////

    @Override
    public TenantQuery createTenantQuery() {
        return new JpaTenantQueryImpl(org.camunda.bpm.engine.impl.context.Context.getProcessEngineConfiguration()
                .getCommandExecutorTxRequired());
    }

    @Override
    public TenantQuery createTenantQuery(CommandContext commandContext) {
        return new JpaTenantQueryImpl();
    }

    @Override
    public Tenant findTenantById(String id) {
        return null;
    }

    @Override
    public Tenant createNewTenant(String s) {
        return null;
    }

    @Override
    public IdentityOperationResult saveTenant(Tenant tenant) {
        return null;
    }

    @Override
    public IdentityOperationResult deleteTenant(String s) {
        return null;
    }

    // 用户与组 ////////////////////////////////////////////

    @Override
    public IdentityOperationResult createMembership(String s, String s1) {
        return null;
    }

    @Override
    public IdentityOperationResult deleteMembership(String s, String s1) {
        return null;
    }

    @Override
    public IdentityOperationResult createTenantUserMembership(String s, String s1) {
        return null;
    }

    @Override
    public IdentityOperationResult createTenantGroupMembership(String s, String s1) {
        return null;
    }

    @Override
    public IdentityOperationResult deleteTenantUserMembership(String s, String s1) {
        return null;
    }

    @Override
    public IdentityOperationResult deleteTenantGroupMembership(String s, String s1) {
        return null;
    }

    // 权限相关接口 ////////////////////////////////////////////

    protected void createDefaultAuthorizations(org.camunda.bpm.engine.impl.persistence.entity.UserEntity userEntity) {
        if (Context.getProcessEngineConfiguration().isAuthorizationEnabled()) {
            this.saveDefaultAuthorizations(this.getResourceAuthorizationProvider().newUser(userEntity));
        }

    }

    protected void createDefaultAuthorizations(Group group) {
        if (this.isAuthorizationEnabled()) {
            this.saveDefaultAuthorizations(this.getResourceAuthorizationProvider().newGroup(group));
        }

    }

    protected void createDefaultAuthorizations(Tenant tenant) {
        if (this.isAuthorizationEnabled()) {
            this.saveDefaultAuthorizations(this.getResourceAuthorizationProvider().newTenant(tenant));
        }

    }
}
