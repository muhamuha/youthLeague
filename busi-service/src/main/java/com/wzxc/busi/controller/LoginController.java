package com.wzxc.busi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.exception.user.UserNotExistsException;
import com.wzxc.common.utils.zzd.JodaUtil;
import com.wzxc.webservice.shiro.JwtUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/login")
@Api(tags="登录类")
public class LoginController extends BaseController {

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
        // 生成系统token
        String sysToken = JwtUtil.sign(userMessage.getString("employeeCode"));
        resultMap.put("userMessage", userMessage);
        resultMap.put("sysToken", sysToken);
        return BusiResult.success("登录成功", resultMap);
    }

}
