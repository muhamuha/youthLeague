package com.wzxc.common.utils.zzd;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.GetClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import com.wzxc.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 浙政钉接口工具类
 *
 */
@Component
@ConfigurationProperties(prefix = "zzd.config")
@Slf4j
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

    // 登录模块 ///////////////////////////

    /**
     * 获取应用免登access_token
     */
    public static String gettoken(){
        String api ="/gettoken.json";
        GetClient getClient = executableClient.newGetClient(api);
        //设置参数
        getClient.addParameter("appkey", appkey);
        getClient.addParameter("appsecret", secret);
        //调用API
        String apiResult = getClient.get();
        return apiResult;
    }

    /**
     * 通过authCode获取access_token
     * @param token
     * @param authCode
     * @return
     */
    public static String gettokenByAuthCode(String token, String authCode){
        String api ="/rpc/oauth2/dingtalk_app_token.json";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("access_token", token);
        postClient.addParameter("auth_code", authCode);
        //调用API
        String apiResult = postClient.post();
        return apiResult;
    }

    /**
     * 通过authCode获取用户信息
     * @param token
     * @param authCode
     * @return
     */
    public static String getUserByAuthCode(String accessToken, String authCode){
        String api ="/rpc/oauth2/getuserinfo_bycode.json";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("access_token", accessToken);
        postClient.addParameter("code", authCode);
        //调用API
        String apiResult = postClient.post();
        return apiResult;
    }

    /**
     * 通过accessToken获取jsapiToken
     * @param accessToken
     * @return
     */
    public static String getJsapiToken(String accessToken){
        String api ="/get_jsapi_token.json";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("accessToken", accessToken);
        //调用API
        String apiResult = postClient.post();
        log.info("getJsapiToken --- " + apiResult);
        return apiResult;
    }

    /**
     * 日程列表查询
     */
    public static String searchScheduleList(String accountId) throws ParseException {
        String api ="/calendar/getSchedule";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("accountId", accountId);
        postClient.addParameter("eventType", "conference");
        JSONObject start = new JSONObject();
        JSONObject end = new JSONObject();
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
        start.put("timeStamp", simpleFormatter.parse(DateUtils.getDate()).getTime());
        end.put("timeStamp", DateUtils.getRelativeDate(1).getTime());
        postClient.addParameter("start", start.toJSONString());
        postClient.addParameter("end", end.toJSONString());
        //调用API
        String apiResult = postClient.post();
        return apiResult;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("aa --- " + simpleFormatter.parse(DateUtils.getDate()).getTime());
        System.out.println("bb --- " + DateUtils.getRelativeDate(1).getTime());
    }


    /**
     * 根据员工code列表 查询员工账号id列表
     */
    public static String queryListEmployeeAccountIds(JSONArray emcodes){
        String api ="/mozi/employee/listEmployeeAccountIds";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        for(Object emcode : emcodes){
            postClient.addParameter("employeeCodes", emcode.toString());
        }
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
        return apiResult;
    }

    /**
     * 根据组织code 查询人员code列表
     */
    public static String queryPageOrganizationEmployeeCodes(String organizationCode, int pageNo){
        String api ="/mozi/organization/pageOrganizationEmployeeCodes";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("organizationCode", organizationCode);
        postClient.addParameter("returnTotalSize", "true");
        postClient.addParameter("pageNo", String.valueOf(pageNo));
        postClient.addParameter("pageSize", "100");
        postClient.addParameter("status", "A");
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
        return apiResult;
    }

    /**
     * 我发起的对外接口(待办)
     */
    public static String instGetList(String accountId){
        String api ="/tc/openapi/3rd/Inst/getList";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("userId", accountId);
        postClient.addParameter("pageSize", "10");
        postClient.addParameter("pageNo", "1");
        //调用API
        String apiResult = postClient.post();
        return apiResult;
    }

    /**
     * 获取人员信息
     */
    public static String pageSearchEmployee(String nameKeywords, String orgCode){
        String api ="/mozi/fusion/pageSearchEmployee";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("nameKeywords", nameKeywords);
        postClient.addParameter("cascadeOrganizationCode", orgCode);
        postClient.addParameter("pageSize", "10");
        postClient.addParameter("pageNo", "1");
        //调用API
        String apiResult = postClient.post();
        return apiResult;
    }

    /**
     * 通过人员accountId列表 获取employeeCode列表
     */
    public static String listGovEmployeeCodesByAccountIds(JSONArray accountIds){
        String api ="/mozi/employee/listGovEmployeeCodesByAccountIds";
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        for(Object accountId : accountIds.toArray()){
            postClient.addParameter("accountIds", accountId.toString());
        }
        postClient.addParameter("tenantId", tenantId);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
    }

    /**
     * 发送工作通知
     */
    public static String sendWorkNotification(String content, JSONArray receiverIds){
        String api = "/message/workNotification";
        PostClient postClient = executableClient.newPostClient(api);
        // 设置参数
        StringBuilder receiverIdsStr = new StringBuilder();
        for(int i = 0; i < receiverIds.size(); i++){
            receiverIdsStr.append(receiverIds.get(i).toString());
            if(i < receiverIds.size() - 1){
                receiverIdsStr.append(",");
            }
        }
        postClient.addParameter("receiverIds", receiverIdsStr.toString());
        postClient.addParameter("tenantId", tenantId);
        postClient.addParameter("msg", content);
        //调用API
        String apiResult = postClient.post();
//        System.out.println(apiResult);
        return apiResult;
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
        return apiResult;
    }


}
