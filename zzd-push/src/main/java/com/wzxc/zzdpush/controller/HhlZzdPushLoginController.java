package com.wzxc.zzdpush.controller;

import com.wzxc.common.core.domain.AjaxResult;
import com.wzxc.zzdpush.service.IHhlZzdPushUserService;
import com.wzxc.zzdpush.service.impl.HhlZzdPushUserServiceImpl;
import com.wzxc.zzdpush.utils.JwtUtil;
import com.wzxc.zzdpush.vo.HhlZzdPushUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/login")
@Slf4j
@Api(tags="浙政钉登录接口类")
public class HhlZzdPushLoginController {

    @Autowired
    private HhlZzdPushUserServiceImpl hhlZzdPushUserService;

    @PostMapping("/")
    @ApiOperation(value = "模板消息系统登录", notes = "浙政钉模板消息系统登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "password",value = "密码", required = true, paramType = "query", dataType="String")
    })
    public AjaxResult login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        Map<String, Object> resultMap = new HashMap<>();
        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        // 将token添加到header中
        HhlZzdPushUser hhlZzdPushUser = hhlZzdPushUserService.selectUserByUsername(username);
        String token = JwtUtil.sign(hhlZzdPushUser.getId());
        resultMap.put("token", token);
        resultMap.put("userDetail", hhlZzdPushUser);
        return AjaxResult.success("登录成功", resultMap);
    }
}
