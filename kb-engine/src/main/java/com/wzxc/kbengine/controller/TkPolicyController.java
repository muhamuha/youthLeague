package com.wzxc.kbengine.controller;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.validate.Check;
import com.wzxc.kbengine.service.impl.TkPolicyRepServiceImpl;
import com.wzxc.kbengine.vo.PolicyBaseInfoRep;
import com.wzxc.kbengine.vo.TkPolicyRep;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/tkPolicy")
@Slf4j
@Api(tags="知识库政策绑定任务操作接口")
public class TkPolicyController {

    @Autowired
    private TkPolicyRepServiceImpl tkPolicyRepService;

    @ApiOperation(value = "政策关联任务", notes = "政策关联", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tkBaseInfoId",value = "任务id", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "policyBaseInfoIds",value = "政策id（数组）", required = true, paramType = "query", dataType="long[]"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "tkPolicy.tkBaseInfoId", msg = "任务id不能为空"),
            @CheckParam(value = Check.IsArrayAndNotEmpty, argName = "tkPolicy.policyBaseInfoIds", msg = "政策id（数组）不能为空"),
    })
    @PostMapping("/tkPolicies/add")
    public KbengineResult add(@RequestBody @ApiIgnore Map<String, Object> tkPolicy){
        Long tkBaseInfoId = Long.valueOf(String.valueOf(tkPolicy.get("tkBaseInfoId")));
        // 清空与任务之间的关联
        tkPolicyRepService.deleteTkPolicyRepByTkId(tkBaseInfoId);
        // 添加与任务之间的关联
        if(tkPolicy.get("policyBaseInfoIds") != null){
            for(Object policyBaseInfoId : (List<Object>) tkPolicy.get("policyBaseInfoIds")){
                TkPolicyRep tkPolicyRep = new TkPolicyRep();
                tkPolicyRep.setPolicyRepId(Long.valueOf(String.valueOf(policyBaseInfoId)));
                tkPolicyRep.setTkBaseInfoId(tkBaseInfoId);
                tkPolicyRepService.insertTkPolicyRep(tkPolicyRep);
            }
        }
        return KbengineResult.success("关联成功");
    }

    @ApiOperation(value = "删除关联", notes = "删除关联", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tkBaseInfoId",value = "任务id", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "policyBaseInfoIds",value = "政策id（数组）", required = true, paramType = "query", dataType="long[]"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "tkPolicy.tkBaseInfoId", msg = "任务id不能为空"),
            @CheckParam(value = Check.IsArrayAndNotEmpty, argName = "tkPolicy.policyBaseInfoIds", msg = "政策id（数组）不能为空"),
    })
    @PostMapping("/tkPolicies/delete")
    public KbengineResult delete(@RequestBody @ApiIgnore Map<String, Object> tkPolicy){
        Long tkBaseInfoId = Long.valueOf(String.valueOf(tkPolicy.get("tkBaseInfoId")));
        List<Object> policyBaseInfoIds = (List<Object>) tkPolicy.get("policyBaseInfoIds");
        // 清空与任务之间的关联
        tkPolicyRepService.deleteTkPolicyRepByPolicyId(tkBaseInfoId, policyBaseInfoIds);
        return KbengineResult.success("删除成功");
    }
}
