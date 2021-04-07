package com.wzxc.zzdpush;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZzdPushApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void fun(){
        String api ="/mozi/organization/listOrganizationsByCodes";
        String ocodes = "[\"GO_b962a45a93f342c8adb272b0397cc91b\"]";
        ExecutableClient executableClient = ExecutableClient.getInstance();
        executableClient.setAccessKey("CityBMessage-QF83wtt3vTdQnIzQ3");
        executableClient.setSecretKey("7gGV92Gs50W7zKVts0Ys4Ni5z4tUZ28l2fQrF3rT");
        executableClient.setDomainName("openplatform-pro.ding.zj.gov.cn");
        executableClient.setProtocal("https");
        executableClient.init();
        PostClient postClient = executableClient.newPostClient(api);
        //设置参数
        for(Object organizationCode : JSONArray.parseArray(ocodes).toArray()){
            postClient.addParameter("organizationCodes", organizationCode.toString());
        }
        postClient.addParameter("tenantId", "196729");
        //调用API
        String apiResult = postClient.post();
        System.out.println(apiResult);
    }

}
