package com.wzxc.busi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.exception.user.UserNotExistsException;
import com.wzxc.common.utils.zzd.JodaUtil;
import com.wzxc.webservice.shiro.JwtUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/login")
@Api(tags="登录类")
public class LoginController extends BaseController {

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    @ApiOperation(value = "pc登录", notes = "pc登录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "浙政钉code", required = true, paramType = "query", dataType="string"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/pc/{code}")
    public BusiResult login(@PathVariable String code) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取应用access_token
        String accessToken = Optional.ofNullable(JSONObject.parseObject(JodaUtil.gettoken()).getJSONObject("content").getJSONObject("data").getString("accessToken"))
                .orElseThrow(() -> new UserNotExistsException());
        // 获取用户信息
        JSONObject userMessage = Optional.ofNullable(JSONObject.parseObject(JodaUtil.getUserByAuthCode(accessToken, code)).getJSONObject("content").getJSONObject("data"))
                .orElseThrow(() -> new UserNotExistsException());
        // 判断用户是否是委员
//        log.info("employeeCode --- " + userMessage.getString("employeeCode"));
        int count = leagueCommissinorService.count(Wrappers.<LeagueCommissinor>lambdaQuery()
                .eq(LeagueCommissinor::getEmployeeCode, userMessage.getString("employeeCode"))
                .eq(LeagueCommissinor::getIsDelete, 0));
        if(count > 0){
            // 生成系统token
            String sysToken = JwtUtil.sign(userMessage.getString("employeeCode"));
            resultMap.put("userMessage", userMessage);
            resultMap.put("sysToken", sysToken);
            return BusiResult.success("登录成功", resultMap);
        }
        return BusiResult.error("登录失败，失败原因：未找到该用户", resultMap);
    }

    @ApiOperation(value = "浙里办登录", notes = "浙里办登录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iphone", value = "手机号码", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "idcard", value = "身份证", required = false, paramType = "query", dataType="string")
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @RequestMapping(value = "/zheliban",method = RequestMethod.GET)
    public BusiResult zheLiBanLogin(@RequestParam(value = "iphone") String iphone,
                                    @RequestParam(value = "idcard",required = false) String idcard) {

        Map<String, Object> resultMap = new HashMap<>();
        LeagueCommissinor commissinor = new LeagueCommissinor();
        if ("".equals(idcard)||idcard==null){
            commissinor = leagueCommissinorService.getOne(Wrappers.<LeagueCommissinor>lambdaQuery()
                    .like(LeagueCommissinor::getIphone, iphone)
                    .eq(LeagueCommissinor::getIsDelete, 0));
        }else {
            commissinor = leagueCommissinorService.getOne(Wrappers.<LeagueCommissinor>lambdaQuery()
                    .like(LeagueCommissinor::getIphone, iphone)
                    .eq(LeagueCommissinor::getIdcard, idcard)
                    .eq(LeagueCommissinor::getIsDelete, 0));
        }
        if(commissinor!=null){
            // 生成系统token
            String sysToken = JwtUtil.sign(commissinor.toString());
            resultMap.put("commissinor", commissinor);
            resultMap.put("sysToken", sysToken);
            return BusiResult.success("登录成功", resultMap);
        }
        return BusiResult.error("登录失败，失败原因：未找到该用户", resultMap);
    }

    @ApiOperation(value = "移动端登录", notes = "移动端登录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "浙政钉code", required = true, paramType = "query", dataType="string"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/cellphone/{code}")
    public BusiResult loginCellphone(@PathVariable String code) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取应用access_token
        String accessToken = Optional.ofNullable(JSONObject.parseObject(JodaUtil.gettoken_c()).getJSONObject("content").getJSONObject("data").getString("accessToken"))
                .orElseThrow(() -> new UserNotExistsException());
        // 获取用户信息
        JSONObject userMessage = Optional.ofNullable(JSONObject.parseObject(JodaUtil.getUserByAuthCode_c(accessToken, code)).getJSONObject("content").getJSONObject("data"))
                .orElseThrow(() -> new UserNotExistsException());
        // 判断用户是否是委员
        int count = leagueCommissinorService.count(Wrappers.<LeagueCommissinor>lambdaQuery()
                .eq(LeagueCommissinor::getEmployeeCode, userMessage.getString("employeeCode"))
                .eq(LeagueCommissinor::getIsDelete, 0));
        log.info("employeeCode --- " + userMessage.getString("employeeCode"));
        if(count > 0){
            // 生成系统token
            String sysToken = JwtUtil.sign(userMessage.getString("employeeCode"));
            resultMap.put("userMessage", userMessage);
            resultMap.put("sysToken", sysToken);
            return BusiResult.success("登录成功", resultMap);
        }
        return BusiResult.error("登录失败，失败原因：未找到该用户", resultMap);
    }


    /**
     * 获取浙政钉ticket
     * @return
     */
    @ApiOperation(value = "获取浙政钉ticket", notes = "获取浙政钉ticket", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/jsapiToken")
    public BusiResult getJSAPIToken(){
        Map<String, Object> resultMap = new HashMap<>();
        // 获取应用access_token
        String accessToken = Optional.ofNullable(JSONObject.parseObject(JodaUtil.gettoken()).getJSONObject("content").getJSONObject("data").getString("accessToken"))
                .orElseThrow(() -> new UserNotExistsException());
        // 获取JSAPIToken
        String  jsapiToken = Optional.ofNullable(JSONObject.parseObject(JodaUtil.getJsapiToken(accessToken)).getJSONObject("content").getJSONObject("data").getString("accessToken"))
                .orElseThrow(() -> new UserNotExistsException());
        resultMap.put("ticket", jsapiToken);
        return BusiResult.success("获取成功", resultMap);
    }

}
