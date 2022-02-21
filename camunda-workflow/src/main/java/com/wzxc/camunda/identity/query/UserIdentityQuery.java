package com.wzxc.camunda.identity.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzxc.camunda.persistence.entity.CaAdmin;
import com.wzxc.camunda.persistence.entity.CaGroup;
import com.wzxc.camunda.persistence.entity.CaUser;
import com.wzxc.camunda.service.impl.CaAdminServiceImpl;
import com.wzxc.camunda.service.impl.CaGroupServiceImpl;
import com.wzxc.camunda.service.impl.CaUserServiceImpl;
import com.wzxc.common.exception.user.UserNotExistsException;
import com.wzxc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.authorization.Permission;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.*;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.camunda.bpm.engine.authorization.Permissions.READ;
import static org.camunda.bpm.engine.authorization.Resources.USER;

@Service
public class UserIdentityQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserIdentityQuery.class);

    private static final int pageSize = 20;
    private static final int pageNum = 1;

    private static final String GROUPTYPE_SYTEM = "SYSTEM";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public CaUserServiceImpl caUserService;

    @Autowired
    public CaGroupServiceImpl caGroupService;

    @Autowired
    public CaAdminServiceImpl caAdminService;

    // users ////////////////////////////

    public long findUsersCount() {
        return caUserService.aggragate();
    }

    public List<User> findUsers(UserQueryImpl userQueryImpl) {
        // 判断是否是登录接口 /camunda-flow/v1/camunda/api/admin/auth/user/default/login/admin、/camunda-flow/v1/camunda/api/admin/auth/user/default/login/welcome
        if (isWhiteList()) {
            // 将账号名（username）和userid进行对应
            LambdaQueryWrapper<CaAdmin> queryWrapper = Wrappers.<CaAdmin>lambdaQuery();
            CaAdmin admin = caAdminService.getOne(queryWrapper.eq(CaAdmin::getUsername, userQueryImpl.getId()));
            if (admin == null) {
                return null;
            }
            userQueryImpl.userId(admin.getUserId());
        }
        LambdaQueryWrapper<CaUser> queryWrapper = buildUsersWhereCondition(userQueryImpl);
        int maxResults = userQueryImpl.getMaxResults();
        int firstResult = userQueryImpl.getFirstResult();
        if (maxResults == Integer.MAX_VALUE) {
            PageHelper.startPage(pageNum, pageSize);
        } else {
            PageHelper.offsetPage(firstResult, maxResults);
        }
        List<CaUser> caUsers = new ArrayList<>();
        if (StringUtils.isNotEmpty(userQueryImpl.getGroupId())) {
            caUsers = caUserService.findByGroupId(queryWrapper, userQueryImpl.getGroupId());
        } else {
            caUsers = caUserService.findAll(queryWrapper);
        }
        PageInfo<CaUser> pageInfo = new PageInfo<>(caUsers);
        List<User> users = new ArrayList<>();
        for (CaUser userEntity : pageInfo.getList()) {
            User user = transformUser(userEntity);
            if (isAuthenticatedUser(user) || isAuthorized(READ, USER, user.getId())) {
                users.add(user);
            }
        }
        return users;
    }

    protected LambdaQueryWrapper<CaUser> buildUsersWhereCondition(UserQueryImpl query) {
        LambdaQueryWrapper<CaUser> where = Wrappers.<CaUser>lambdaQuery();
        Class caUserClass = CaUser.class;
        // 获取搜索条件
        if (query != null) {
            if (query.getId() != null) {
                where.eq(CaUser::getEmployeeCode, query.getId());
            }
            if (query.getIds() != null && query.getIds().length > 0) {
                where.in(CaUser::getEmployeeCode, query.getIds());
            }
            if (query.getEmail() != null) {
                where.eq(CaUser::getEmail, query.getEmail());
            }
            if (query.getEmailLike() != null) {
                where.like(CaUser::getEmail, query.getEmailLike());
            }
            if (query.getFirstName() != null) {
                where.eq(CaUser::getEmployeeName, query.getFirstName());
            }
            if (query.getFirstNameLike() != null) {
                where.like(CaUser::getEmployeeName, query.getFirstNameLike());
            }
            if (query.getLastName() != null) {
                where.eq(CaUser::getEmployeeName, query.getLastName());
            }
            if (query.getLastNameLike() != null) {
                where.like(CaUser::getEmployeeName, query.getLastNameLike());
            }
        }
        // 获取排序条件
        List<QueryOrderingProperty> orderingProperties = query.getOrderingProperties();
        if (orderingProperties != null && !orderingProperties.isEmpty()) {
            for (QueryOrderingProperty orderingProperty : orderingProperties) {
                if (orderingProperty.getQueryProperty().equals(UserQueryProperty.USER_ID)) {
                    if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
                        where.orderByAsc(CaUser::getEmployeeCode);
                    } else {
                        where.orderByDesc(CaUser::getEmployeeCode);
                    }
                } else if (orderingProperty.getQueryProperty().equals(UserQueryProperty.FIRST_NAME)) {
                    if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
                        where.orderByAsc(CaUser::getEmployeeName);
                    } else {
                        where.orderByDesc(CaUser::getEmployeeName);
                    }
                } else if (orderingProperty.getQueryProperty().equals(UserQueryProperty.LAST_NAME)) {
                    if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
                        where.orderByAsc(CaUser::getEmployeeName);
                    } else {
                        where.orderByDesc(CaUser::getEmployeeName);
                    }
                } else if (orderingProperty.getQueryProperty().equals(UserQueryProperty.EMAIL)) {
                    if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
                        where.orderByAsc(CaUser::getEmail);
                    } else {
                        where.orderByDesc(CaUser::getEmail);
                    }
                }
            }
        }
        return where;
    }

    protected User transformUser(CaUser userEntity) {
        org.camunda.bpm.engine.impl.persistence.entity.UserEntity user = new org.camunda.bpm.engine.impl.persistence.entity.UserEntity();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstname());
        user.setLastName(userEntity.getLastname());
        user.setPassword(userEntity.getPassword());
        user.setDbPassword(userEntity.getPassword());
        user.setRevision(1);
        return (User) user;
    }

    // groups ////////////////////////////

    public List<Group> findGroups(GroupQueryImpl groupQueryImpl) {
        List<CaGroup> groupEntities;
        LambdaQueryWrapper<CaGroup> queryWrapper = buildGroupsWhereCondition(groupQueryImpl);
        if (StringUtils.isNotEmpty(groupQueryImpl.getUserId())) {
            groupEntities = getListFromIteralbe(caGroupService.findAllByUserId(queryWrapper, groupQueryImpl.getUserId()));
        } else {
            groupEntities = caGroupService.list(queryWrapper);
        }
        List<Group> groups = new ArrayList<Group>();
        for (GroupEntity groupEntity : groupEntities) {
            groups.add(transformGroup(groupEntity));
        }

        return groups;
    }

    protected LambdaQueryWrapper<CaGroup> buildGroupsWhereCondition(GroupQueryImpl query) {
        LambdaQueryWrapper<CaGroup> where = Wrappers.<CaGroup>lambdaQuery();
        if (query != null) {
            if (query.getId() != null) {
                where.eq(CaGroup::getId, query.getId());
            }
            if (query.getIds() != null && query.getIds().length > 0) {
                where.in(CaGroup::getId, query.getIds());
            }
            if (query.getName() != null) {
                where.eq(CaGroup::getName, query.getName());
            }
            if (query.getNameLike() != null) {
                where.like(CaGroup::getName, query.getNameLike());
            }
        }
        List<QueryOrderingProperty> orderingProperties = query.getOrderingProperties();
        if (orderingProperties != null && !orderingProperties.isEmpty()) {
            for (QueryOrderingProperty orderingProperty : orderingProperties) {
                if (orderingProperty.getQueryProperty().equals(GroupQueryProperty.GROUP_ID)) {
                    if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
                        where.orderByAsc(CaGroup::getId);
                    } else {
                        where.orderByDesc(CaGroup::getId);
                    }
                } else if (orderingProperty.getQueryProperty().equals(GroupQueryProperty.NAME)) {
                    if (orderingProperty.getDirection().equals(Direction.ASCENDING)) {
                        where.orderByAsc(CaGroup::getName);
                    } else {
                        where.orderByDesc(CaGroup::getName);
                    }
                }
            }
        }
        return where;
    }

    protected Group transformGroup(GroupEntity groupEntity) {
        Group group = new org.camunda.bpm.engine.impl.persistence.entity.GroupEntity();
        group.setId(groupEntity.getId());
        group.setName(groupEntity.getName());
        group.setType(GROUPTYPE_SYTEM);
        return group;
    }

    public boolean deleteGroup(String id) {
        if (StringUtils.isEmpty(id)) {
            return true;
        }
        LambdaQueryWrapper<CaGroup> where = Wrappers.<CaGroup>lambdaQuery();
        return caGroupService.remove(where.eq(CaGroup::getId, id));
    }

    public boolean insertGroup(Group group) {
        CaGroup caGroup = new CaGroup();
        caGroup.setId(group.getId());
        caGroup.setName(group.getName());
        caGroup.setType(group.getType());
        caGroupService.save(caGroup);
        return false;
    }

    public boolean mergeGroup(Group group) {
        return false;
    }

    // password ////////////////////////////

    public boolean checkPassword(String username, String password) {
        LambdaQueryWrapper<CaAdmin> queryWrapper = Wrappers.<CaAdmin>lambdaQuery();
        queryWrapper.eq(CaAdmin::getUsername, username);
        CaAdmin admin = Optional.ofNullable(caAdminService.getOne(queryWrapper))
                .orElseThrow(() -> new UserNotExistsException());

        return password.equals(admin.getPassword());
    }

    // Utils ////////////////////////////////////////////

    protected boolean isAuthenticatedUser(User user) {
        if (user.getId() == null) {
            return false;
        }
        return user.getId()
                .equals(Context.getCommandContext().getAuthenticatedUserId());
    }

    protected boolean isAuthorized(Permission permission, Resource resource, String resourceId) {
        return Context.getCommandContext().getAuthorizationManager()
                .isAuthorized(permission, resource, resourceId);
    }

    protected static <T> List<T> getListFromIteralbe(Iterable<T> itr) {
        return (List<T>) StreamSupport.stream(itr.spliterator(), false).collect(Collectors.toList());
    }

    protected boolean isWhiteList() {
        String uri = request.getRequestURI();
        if (uri.lastIndexOf("/camunda/api/admin/auth/user/default/login/admin") >= 1
                || uri.lastIndexOf("/camunda/api/admin/auth/user/default/login/welcome") >= 1) {
            return true;
        }
        return false;
    }
}
