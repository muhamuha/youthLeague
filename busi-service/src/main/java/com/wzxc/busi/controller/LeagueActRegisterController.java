package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wzxc.busi.service.impl.LeagueActivityServiceImpl;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueActivity;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueActRegister;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueActRegisterServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-11-24
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueActRegister")
@Api(tags = "活动签到类")
public class LeagueActRegisterController extends BaseController {

    @Autowired
    private LeagueActRegisterServiceImpl leagueActRegisterService;

    @Autowired
    private LeagueActivityServiceImpl leagueActivityService;

    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询报名列表", notes = "查询报名列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "activityId", value = "活动id（该活动的报名信息）", required = false, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "commissinorId", value = "委员id（该委员的报名信息）", required = false, paramType = "query", dataType = "Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueActRegister.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeagueActRegister leagueActRegister) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<LeagueActRegister> list = leagueActRegisterService.selectLeagueActRegisterList(leagueActRegister);
        buildTableInfo(list, resultMap);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "活动报名", notes = "活动报名", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "commissinorId", value = "委员id", required = true, paramType = "query", dataType = "Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "activityId", msg = "缺少活动id字段（activityId）"),
            @CheckParam(value = Check.NotNull, argName = "commissinorId", msg = "缺少委员id字段（commissinorId）"),
    })
    @PostMapping("/add")
    public BusiResult add(Long activityId, Long commissinorId) {
        if(activityId == null || commissinorId == null){
            return BusiResult.error("新增失败，失败原因：缺少必填参数");
        }
        LeagueActRegister leagueActRegister = new LeagueActRegister();
        leagueActRegister.setActivityId(activityId);
        leagueActRegister.setCommissinorId(commissinorId);
        int isSuccess = leagueActRegisterService.insertLeagueActRegister(leagueActRegister);
        if (isSuccess == 0) {
            return BusiResult.error("新增失败");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "取消报名", notes = "取消报名", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "commissinorId", value = "委员id", required = true, paramType = "query", dataType = "long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/remove/{activityId}/{commissinorId}")
    public BusiResult remove(@PathVariable Long activityId, @PathVariable Long commissinorId) {
        boolean isSuccess = leagueActRegisterService.remove(Wrappers.<LeagueActRegister>lambdaQuery()
                .eq(LeagueActRegister::getActivityId, activityId)
                .eq(LeagueActRegister::getCommissinorId, commissinorId));
        if (!isSuccess) {
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

    /**
     * 签到
     */
    @ApiOperation(value = "签到", notes = "签到", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityId", value = "活动id", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "commissinorId", value = "委员id", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = true, paramType = "query", dataType = "float"),
            @ApiImplicitParam(name = "lon", value = "经度", required = true, paramType = "query", dataType = "float"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/sign")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "activityId", msg = "缺少活动id字段（activityId）"),
            @CheckParam(value = Check.NotNull, argName = "commissinorId", msg = "缺少委员id字段（commissinorId）"),
            @CheckParam(value = Check.NotNull, argName = "lat", msg = "缺少纬度（lat）"),
            @CheckParam(value = Check.NotNull, argName = "lon", msg = "缺少经度（lon）"),
    })
    public BusiResult sign(Long activityId, Long commissinorId, float lat, float lon){
        // 获取活动信息
        LeagueActivity leagueActivity = leagueActivityService.getOne(Wrappers.<LeagueActivity>lambdaQuery().eq(LeagueActivity::getId, activityId));
        Integer signRange = leagueActivity.getSignRange();
        GlobalCoordinates source = new GlobalCoordinates(leagueActivity.getLat(), leagueActivity.getLon());
        GlobalCoordinates target = new GlobalCoordinates(lat, lon);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target);
        double distance = geoCurve.getEllipsoidalDistance();
        if(distance > signRange){
            return BusiResult.error("签到失败，失败原因：超出打卡范围");
        }
        LeagueActRegister leagueActRegister = new LeagueActRegister();
        leagueActRegister.setActivityId(activityId);
        leagueActRegister.setCommissinorId(commissinorId);
        leagueActRegister.setSignNotifyTime(new Date());
        int success = leagueActRegisterService.updateLeagueActRegister(leagueActRegister);
        if(success == 0){
            return BusiResult.error("签到失败，失败原因：数据库操作失败");
        }
        return BusiResult.success("签到成功");
    }

    /**
     * 发送活动通知
     * @param activityId
     * @return
     */
    @ApiOperation(value = "发送活动通知", notes = "发送活动通知", httpMethod = "GET")
    @ApiImplicitParams({})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/sign/{activityId}")
    public BusiResult postNotify(Long activityId){
        LeagueActivity activity = leagueActivityService.getOne(Wrappers.<LeagueActivity>lambdaQuery().eq(LeagueActivity::getId, activityId));
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(activity.getCreater());

        // 构建模板
        StringBuilder content = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        content.append(leagueCommissinor.getName()).append("发起了新的青联活动，你要参加吗？").append("\n")
                .append("活动名称：").append(activity.getNickname()).append("\n")
                .append("活动地点：").append(activity.getAddress())
                .append("活动时间：").append(simpleDateFormat.format(activity.getActivityBegin()));
        if(activity.getActivityEnd() != null){
            content.append(" ~ ").append(simpleDateFormat.format(activity.getActivityEnd()));
        }

        JSONObject statusInfo = new JSONObject();
        statusInfo.put("text", "待报名");
        statusInfo.put("bgcolor", "#FF9F00");
        statusInfo.put("allcolor", "rgba(31, 31, 31)");

        JSONArray btnList = new JSONArray();
        JSONObject reject = new JSONObject();
        JSONObject agree = new JSONObject();
        reject.put("title", "拒绝");
        reject.put("action_url", "xxx");
        agree.put("title", "同意");
        agree.put("action_url", "xxx");
        btnList.add(reject);
        btnList.add(agree);

        JSONObject actionCard = new JSONObject();
        actionCard.put("title", "青联活动通知");
        actionCard.put("markdown", content.toString());
        actionCard.put("statusInfo", statusInfo);

        JSONObject msg = new JSONObject();
        msg.put("msgtype", "text");
        msg.put("action_card", actionCard);
        msg.put("btn_orientation", "1");
        msg.put("btn_json_list", btnList);

        // 获取所有委员的浙政钉code
        return null;
    }

}
