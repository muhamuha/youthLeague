package com.wzxc.busi.controller.zzd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.zzd.JodaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/zzd")
@Slf4j
@Api(tags="浙政钉开放接口")
public class SynController {
    
    @PostMapping(value = "/org/list")
    @ApiOperation(value = "获取下一层的组织详情和员工详情", notes = "获取下一层的组织详情和员工详情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationCode",value = "组织code（第一层用root）", required = true, paramType = "query", dataType="string"),
    })
    public BusiResult queryOrgList(@RequestParam(value = "organizationCode") String organizationCode){
        Map<String, Object> resultMap = new HashMap<>();
        JSONArray orgList = queryOrgList1(organizationCode);
        if(orgList != null){
            resultMap.put("data", orgList);
            return BusiResult.success("查询成功", resultMap);
        } else{
            return BusiResult.error("查询失败");
        }
    }

    /**
     * 获取组织下一层级的组织详情和员工详情
     */
    private JSONArray queryOrgList1(String organizationCode){
        JSONObject jsonObject = new JSONObject();
        if(organizationCode.equals("root")){
            jsonObject = JSONObject.parseObject(JodaUtil.queryAuthScopesV2());
            // 判断是否调用成功
            if((Boolean) jsonObject.get("success")){
                try{
                    JSONArray jsonArray = jsonObject.getJSONObject("content").getJSONArray("deptVisibleScopes");
                    String result = JodaUtil.queryListOrganizationsByCodes(jsonArray);
                    return JSONObject.parseObject(result).getJSONObject("content").getJSONArray("data");
                } catch(Exception e){
                    return null;
                }
            }
        } else{
            try{
                String result = "";
                jsonObject = JSONObject.parseObject(JodaUtil.querySubOrganizationCodes(organizationCode));
                // 判断是否调用成功
                if((Boolean) jsonObject.get("success") && jsonObject.getJSONObject("content").get("data") != null){
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.getJSONObject("content").get("data").toString());
                    result = JodaUtil.queryListOrganizationsByCodes(jsonArray);
                    return JSONObject.parseObject(result).getJSONObject("content").getJSONArray("data");
                }
            } catch(Exception e){
                log.error("发生异常", e);
            }
        }
        return null;
    }

    @PostMapping(value = "/employee/list")
    @ApiOperation(value = "获取组织下的员工详情", notes = "获取组织下的员工详情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationCode",value = "组织code", required = true, paramType = "query", dataType="string"),
    })
    public BusiResult queryEmployeeList(@RequestParam(value = "organizationCode") String organizationCode){
        Map<String, Object> resultMap = new HashMap<>();
        JSONArray employeeList = queryEmployeeList1(organizationCode);
        if(employeeList != null){
            resultMap.put("data", employeeList);
            return BusiResult.success(resultMap);
        } else{
            return BusiResult.error("获取失败");
        }
    }

    /**
     * 获取组织下员工详情
     */
    public JSONArray queryEmployeeList1(String organizationCode){
        Map<String, Object> resultMap = new HashMap<>();
        // 获取同级的人员信息列表
        try{
            JSONArray employeeArray = new JSONArray();
            int pageNo = 0, total = 0;
            // 判断是否超过100条，超过的话需要多次查询
            do{
                JSONObject jsonObject = JSONObject.parseObject(JodaUtil.queryPageOrganizationEmployeeCodes(organizationCode, ++pageNo));
                if((Boolean) jsonObject.get("success") && jsonObject.getJSONObject("content").get("data") != null){
                    total = Integer.valueOf(jsonObject.getJSONObject("content").getString("totalSize"));
                    // 通过人员code列表 获取人员详情列表
                    String result = JodaUtil.queryListEmployeeByCodes(jsonObject.getJSONObject("content").getJSONArray("data"));
                    employeeArray.addAll(JSONObject.parseObject(result).getJSONObject("content").getJSONArray("data"));
                }
            } while(total - employeeArray.size() > 0);
            return employeeArray;

        } catch(Exception e){
            log.error("发生异常", e);
            return null;
        }
    }
}
