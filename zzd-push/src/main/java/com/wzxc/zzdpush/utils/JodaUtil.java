package com.wzxc.zzdpush.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 浙政钉接口工具类
 *
 */
@Component
@ConfigurationProperties(prefix = "zzd.config")
public class JodaUtil {

    private static ExecutableClient executableClient;
    @Value("${zzd.config.msgApp}")
    private static String msgApp;
    @Value("${zzd.config.appkey}")
    private static String appkey;
    @Value("${zzd.config.secret}")
    private static String secret;
    @Value("${zzd.config.tenantid}")
    private static String tenantId;
    @Value("${zzd.config.domain-name}")
    private static String domainName;
    @Value("${zzd.config.protocal}")
    private static String protocal;

    public void setAppkey(String appkeySet){
        appkey = appkeySet;
    }
    public void setSecret(String secretSet){
        secret = secretSet;
    }
    public void setTenantId(String tenantIdSet){
        tenantId = tenantIdSet;
    }
    public void setDomainName(String domainNameSet){
        domainName = domainNameSet;
    }
    public void setProtocal(String protocalSet){
        protocal = protocalSet;
    }
    public void setMsgApp(String msgAppSet){
        msgApp = msgAppSet;
    }

    @PostConstruct
    public void build(){
        executableClient = ExecutableClient.getInstance();
        executableClient.setAccessKey(appkey);
        executableClient.setSecretKey(secret);
        executableClient.setDomainName(domainName);
        executableClient.setProtocal(protocal);
        executableClient.init();
    }

    /**
     * 获取通讯录权限范围
     */
    public static String queryAuthScopesV2(){
        String api ="/auth/scopesV2";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 根据组织code获取下一层的组织列表
     */
    public static String querySubOrganizationCodes(String organizationCode){
        String api ="/mozi/organization/pageSubOrganizationCodes";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("organizationCode", organizationCode);
        postClient.addParameter("pageSize", "100");
        postClient.addParameter("status", "A");
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 根据组织 Code 列表查询详情
     */
    public static String queryListOrganizationsByCodes(JSONArray organizationCodes){
        String api ="/mozi/organization/listOrganizationsByCodes";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        for(Object organizationCode : organizationCodes.toArray()){
            postClient.addParameter("organizationCodes", organizationCode.toString());
        }
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 根据组织code 查询人员code列表
     */
    public static String queryPageOrganizationEmployeeCodes(String organizationCode, String pageNo){
        String api ="/mozi/organization/pageOrganizationEmployeeCodes";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("organizationCode", organizationCode);
        postClient.addParameter("returnTotalSize", "true");
        postClient.addParameter("pageNo", pageNo);
        postClient.addParameter("pageSize", "100");
        postClient.addParameter("status", "A");
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 通过人员code列表 获取人员信息列表
     */
    public static String queryListEmployeeByCodes(JSONArray employeeCodes){
        String api ="/mozi/employee/listEmployeesByCodes";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        for(Object employeeCode : employeeCodes.toArray()){
            postClient.addParameter("employeeCodes", employeeCode.toString());
        }
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 根据员工code列表 查询员工账号id列表
     */
    public static String queryListEmployeeAccountIds(String[] emcodes){
        String api ="/mozi/employee/listEmployeeAccountIds";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        for(String emcode : emcodes){
            postClient.addParameter("employeeCodes", emcode);
        }
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 人员信息查询
     */
    public static String pageSearchEmployee(String cascadeOrganizationCode, String nameKeywords, String pageNo){
        String api ="/mozi/fusion/pageSearchEmployee";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("returnTotalSize", "true");
        postClient.addParameter("cascadeOrganizationCode", cascadeOrganizationCode);
        postClient.addParameter("pageNo", pageNo);
        postClient.addParameter("pageSize", "100");
        postClient.addParameter("status", "A");
        postClient.addParameter("nameKeywords", nameKeywords);
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 根据组织 CODE、员⼯ Code 列表， 批量获取员工在该组织的任职
     */
    public static String listOrgEmployeePositionsByCodes(String organizationCode, JSONArray employeeCodes){
        String api ="/mozi/employee/listOrgEmployeePositionsByCodes";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("organizationCode", "organizationCode");
        for(Object employeeCode : employeeCodes){
            postClient.addParameter("employeeCodes", (String) employeeCode);
        }
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 查询人员的任职情况
     */
    public static String listEmployeePositionsByEmployeeCode(String employeeCode, String employeePositionType){
        String api ="/mozi/employee/listEmployeePositionsByEmployeeCode";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("employeePositionStatus", "A");
        postClient.addParameter("employeePositionType", employeePositionType == null ? "EMPLOYEE_POSITION_MAIN" : employeePositionType);
        postClient.addParameter("employeeCode", employeeCode);
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 发送工作通知
     */
    public static String sendWorkNotification(String content, String receiverIds){
        String api = "/message/workNotification";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("receiverIds", receiverIds);
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("msg", content);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 工作通知撤回
     */
    public static String revokeWorkNotification(String bizMsgId){
        String api = "/message/revoke";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("msgType", "workNotification");
        postClient.addParameter("msgApp", msgApp);
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("bizMsgId", bizMsgId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 工作通知修改
     */
    public static String changeWorkNotification(String msgType, String content, String bizMsgId, String... accountIds){
        String api = "/message/changeMessageInfo";
        PostClient postClient = executableClient.newPostClient(api);
        // 设置参数
        postClient.addParameter("msgType", msgType);
        String receiverIds = String.join(",", accountIds);
        postClient.addParameter("accountIds", receiverIds);
        postClient.addParameter("jsonContent", content);
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("bizMsgId", bizMsgId);
        // 调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }
}
