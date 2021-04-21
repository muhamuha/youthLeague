package com.wzxc.szwenzhou.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.http.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/other")
@Slf4j
public class OtherController {

    ////////////////// 调用城脑指标接口 //////////////////
    private static final String url = "https://api-hub.wenzhou.gov.cn/api/v1/oauth2/token?grant_type=client_credentials";
    private static final String quota_url = "https://api-hub.wenzhou.gov.cn/api/v1/data";

    private static final String CLIENT_ID_1 = "15d19735a9f14ff7b9eafb068a1d246a";
    private static final String CLIENT_SECRET_1 = "26c262528e264320ab8e92ddd7a81c84";

    private String brain_token = null;
    private Date brain_lastGetTokenTime = null; // 最近一次获取token的时间
    private static final Long brain_timePoor = 20 * 60 * 1000L; // 20分钟内不需要重新获取token

    private static String getHeader(String client_id, String client_secret) {
        String auth = client_id + ":" + client_secret;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    /**
     * 获取城脑私有token
     */
    public String getAccessToken(){
        String authHeader = getHeader(CLIENT_ID_1, CLIENT_SECRET_1);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", authHeader);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    String result = EntityUtils.toString(resEntity,"UTF-8");

                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用城脑指标接口
     */
    @PostMapping("/brain/quota")
    private KbengineResult queryBrainQuota(@RequestParam(value = "apiCode", required = true) String apiCode){
        if(StringUtils.isEmpty(brain_token) || DateUtils.getNowDate().getTime() - brain_lastGetTokenTime.getTime() >= timePoor){
            synchronized (this){
                if(StringUtils.isEmpty(brain_token) || DateUtils.getNowDate().getTime() - brain_lastGetTokenTime.getTime() >= timePoor){
                    synchronized (this){
                        // 获取token
                        String accessToken = getAccessToken();
                        if(accessToken == null || StringUtils.isEmpty(accessToken)){
                            return KbengineResult.success("获取token失败");
                        }
                        try{
                            accessToken = JSONObject.parseObject(accessToken).getString("access_token");
                        } catch (Exception e){
                            return KbengineResult.success("获取token失败");
                        }
                        this.brain_token = accessToken;
                        this.brain_lastGetTokenTime = DateUtils.getNowDate();
                    }
                }
            }
        }
        // 调用搜索接口
        String searchUrl = quota_url + "/" + apiCode;
        Map<String, String> parameters = new HashMap<String, String>();
        try{
            Object result = HttpsUtils.doPost(searchUrl, parameters, "Authorization", "Bearer " + brain_token);
            return KbengineResult.success("查询成功", result);
        } catch (Exception e){
            log.error("查询异常 --- ", e);
            return KbengineResult.error("查询失败");
        }

    }

    ////////////////// 调用省搜索接口 //////////////////
    private static final String username = "dmx.szhgg"; // 共享平台账号名称
    private static final String jwt_password = "4b9c192a-9818-11eb-936c-0242ac110002"; // jwt密码请向管理员获取
    private static final String sendUrl_token = "https://10.36.208.64/webapi2/get_token";
    private static final String sendUrl_search = "https://10.36.208.64/webapi2";
    private String token = null;
    private Date lastGetTokenTime = null; // 最近一次获取token的时间
    private static final Long timePoor = 20 * 60 * 1000L; // 20分钟内不需要重新获取token

    // 获取访问token，如果有并行访问，请将token存于共享区域，如redis等，避免并行进程相互覆盖
    private static JSONObject getToken(){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("password", jwt_password);
        JSONObject result = HttpsUtils.doPost(sendUrl_token, parameters);
        System.out.println(result);
        return result;
    }

    /**
     * 调用省搜索接口
     */
    @PostMapping("/province/search")
    private KbengineResult queryProvinceSearch(@RequestParam(value = "interfaceName", required = true) String interfaceName, @RequestParam(value = "p_page", required = false) String p_page,
                                               @RequestParam(value = "p_row", required = false) String p_row, @RequestParam(value = "p_key", required = false) String p_key,
                                               @RequestParam(value = "p_level", required = false) String p_level, @RequestParam(value = "p_sort_type", required = false) String p_sort_type,
                                               @RequestParam(value = "p_areacode", required = false) String p_areacode, @RequestParam(value = "p_scope_code", required = false) String p_scope_code,
                                               @RequestParam(value = "powermatters", required = false) String powermatters, @RequestParam(value = "subpowermatters", required = false) String subpowermatters,
                                               @RequestParam(value = "materials", required = false) String materials){
        if(StringUtils.isEmpty(token) || DateUtils.getNowDate().getTime() - lastGetTokenTime.getTime() >= timePoor){
            synchronized (this){
                if(StringUtils.isEmpty(token) || DateUtils.getNowDate().getTime() - lastGetTokenTime.getTime() >= timePoor){
                    synchronized (this){
                        // 获取token
                        JSONObject token = getToken();
                        try{
                            String accessToken = token.getString("token");
                            this.token = accessToken;
                            this.lastGetTokenTime = DateUtils.getNowDate();
                        } catch (Exception e){
                            return KbengineResult.success("获取token失败");
                        }
                    }
                }
            }
        }
        // 调用搜索接口
        String searchUrl = sendUrl_search + "/" + interfaceName;
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("p_page", p_page);
        parameters.put("p_row", p_row);
        parameters.put("p_key", p_key);
        parameters.put("p_level", p_level);
        parameters.put("p_sort_type", p_sort_type);
        parameters.put("p_areacode", p_areacode);
        parameters.put("p_scope_code", p_scope_code);
        parameters.put("powermatters", powermatters);
        parameters.put("subpowermatters", subpowermatters);
        parameters.put("materials", materials);
        try{
            Object result = HttpsUtils.doPost(searchUrl, parameters, "Authorization", "jwt " + token);
            System.out.println(result);
            log.info(result.toString());
            return KbengineResult.success("查询成功", result);
        } catch (Exception e){
            log.error("查询异常 --- ", e);
            return KbengineResult.success("查询失败");
        }

    }

}
