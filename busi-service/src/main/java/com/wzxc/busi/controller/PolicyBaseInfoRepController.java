package com.wzxc.busi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.busi.service.impl.PolicyBaseInfoRepServiceImpl;
import com.wzxc.busi.vo.PolicyBaseInfoRep;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.StringUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/policyBaseInfo")
@Slf4j
@Api(tags="知识库政策操作接口")
public class PolicyBaseInfoRepController extends BaseController {

    @Autowired
    private PolicyBaseInfoRepServiceImpl policyBaseInfoRepService;



    @ApiOperation(value = "查询政策列表", notes = "查询政策列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyId",value = "政策主键", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "unitName",value = "发布部门", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policyType",value = "发布层级（0：国家 1：省级 2：市级 3：区县）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "publishTimeBegin",value = "发布时间（from，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "publishTimeEnd",value = "发送时间（to，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "keyword",value = "关键字（名称或文号）", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "drafter",value = "拟稿人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "policySystem",value = "领域（0:党政机关整体智治系统,1:数字政府系统;2:数字社会系统;3:数字经济系统;4:数字法制系统）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "policyStatus",value = "状态（0：启用 1：停用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "taskId",value = "所属任务id（默认所有任务）", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "tkBaseInfoId",value = "所属任务id（默认所有任务）", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "isValid",value = "是否有效（0：有效 1：无效，默认0有效）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize",value = "页码（默认10）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageNum",value = "页数（默认1）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "tkOnly",value = "是否只显示任务相关（0：都显示，1：只显示，默认都显示）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = PolicyBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/policies/get")
    public KbengineResult list(@RequestBody @ApiIgnore PolicyBaseInfoRep policyBaseInfoRep) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<PolicyBaseInfoRep> policyBaseInfoReps = new ArrayList<>();
        policyBaseInfoReps = policyBaseInfoRepService.selectPolicyBaseInfoRepList(policyBaseInfoRep);
        try{
            for(PolicyBaseInfoRep policyBaseInfoRepEle : policyBaseInfoReps){
                if(StringUtils.isEmpty(policyBaseInfoRepEle.getFileObjectStr())){
                    continue;
                }
                JSONArray fileList = JSONObject.parseArray(policyBaseInfoRepEle.getFileObjectStr());
                List<Map<String, String>> files = new ArrayList<>();
                for(Object fileEle : fileList){
                    Map<String, String> map = new HashMap<>();
                    map.put("fileName", ((JSONObject) fileEle).getString("fileName"));
                    map.put("fileUrl", ((JSONObject) fileEle).getString("fileUrl"));
                    files.add(map);
                }
                policyBaseInfoRepEle.setFileObjectList(files);
            }

        } catch(Exception e){
        }
        buildTableInfo(policyBaseInfoReps, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }


}

