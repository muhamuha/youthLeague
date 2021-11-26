package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueActivity;
import com.wzxc.webservice.shiro.JwtFilter;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueActivityServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-10-25
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueActivity")
@Api(tags="活动管理类")
public class LeagueActivityController extends BaseController {

    @Autowired
    private LeagueActivityServiceImpl leagueActivityService;

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询未报名列表（用户端）", notes = "查询未报名列表（用户端）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueActivity.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/regist/list")
    public BusiResult registList() throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        // 查询用户信息
        String employeeCode = JwtFilter.getUserId();
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(employeeCode);
        if(leagueCommissinor == null){
            return BusiResult.success("查询失败：失败原因：未找到该用户");
        }
        // 查询用户合法的报名活动列表
        startPage();
        List<LeagueActivity> list = leagueActivityService.selectRegisterLeagueActivityList(leagueCommissinor);
        buildTableInfo(list, resultMap);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
    * 查询详情【请填写功能名称】
    *
    * @param id
    * @return
    */
    @ApiOperation(value = "查询详情", notes = "查询详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueActivity.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        LeagueActivity leagueActivity = leagueActivityService.getById(id);
        if(leagueActivity == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", leagueActivity);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    synchronized public BusiResult add(LeagueActivity leagueActivity)
    {
        int isSuccess = leagueActivityService.insertLeagueActivity(leagueActivity);
        if(isSuccess == 0){
            return BusiResult.error("新增失败");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 修改记录【请填写功能名称】
     */
    @ApiOperation(value = "修改记录", notes = "修改记录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="Integer"),
            @ApiImplicitParam(name = "activityBegin", value = "活动开始时间", required = true, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "activityEnd", value = "", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "activityType", value = "活动类型（字典表）", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "address", value = "活动地点", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "content", value = "活动内容或者行程安排", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "hostman", value = "主办人id", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "nickname", value = "活动别名", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "rangeIndustry", value = "行业范围", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "rangeRegion", value = "地区范围", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "signBegin", value = "签到开始时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "signEnd", value = "签到结束时间", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "lat", value = "活动地点的纬度", required = false, paramType = "query", dataType="Double"),
            @ApiImplicitParam(name = "lon", value = "活动地点的经度", required = false, paramType = "query", dataType="Double"),
            @ApiImplicitParam(name = "isSign", value = "是否打卡", required = false, paramType = "query", dataType="Boolean"),
            @ApiImplicitParam(name = "signRange", value = "打卡范围（单位：米）", required = false, paramType = "query", dataType="Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.id", msg = "缺少系统主键（id）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.activityType", msg = "缺少活动类型（activityType）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.hostman", msg = "缺少主办人id（hostman）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.nickname", msg = "缺少活动别名（nickname）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.activityBegin", msg = "缺少活动开始时间（activityBegin）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivity.address", msg = "缺少活动地点（address）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore LeagueActivity leagueActivity) {
        // 判断是否打卡
        if(leagueActivity.getIsSign() && (leagueActivity.getLon() == null ||
                leagueActivity.getLat() == null ||
                leagueActivity.getSignBegin() == null ||
                leagueActivity.getSignEnd() == null ||
                leagueActivity.getSignRange() == null)){
            return BusiResult.error("缺少打卡必填参数");
        }
        int isSuccess = leagueActivityService.updateLeagueActivity(leagueActivity);
        if(isSuccess == 0){
            return BusiResult.error("修改失败");
        }
        return BusiResult.success("修改成功");
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "删除记录（物理删除）", notes = "删除记录（物理删除）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/remove/{id}")
    public BusiResult remove(@PathVariable Long id) {
        boolean isSuccess = leagueActivityService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
