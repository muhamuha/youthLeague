package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueGatheringGood;
import com.wzxc.webservice.shiro.JwtFilter;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueGatheringGoodServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-07
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueGatheringGood")
@Api(tags="优秀青年聚餐类")
public class LeagueGatheringGoodController extends BaseController {

    @Autowired
    private LeagueGatheringGoodServiceImpl leagueGatheringGoodService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "commissinorId", value = "推荐委员id", required = false, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "gmId", value = "优秀青年id（推荐人）", required = false, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "goodmanId", value = "优秀青年id", required = false, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "activityTitle", value = "活动名称", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueGatheringGood.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeagueGatheringGood leagueGatheringGood) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        startPage();
        List<LeagueGatheringGood> list = leagueGatheringGoodService.selectLeagueGatheringGoodList(leagueGatheringGood);
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
            @ApiResponse(code = 13000, message = "OK", response = LeagueGatheringGood.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();

        LeagueGatheringGood leagueGatheringGood = leagueGatheringGoodService.getById(id);
        if(leagueGatheringGood == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", leagueGatheringGood);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "commissinorId", value = "推荐委员id", required = false, paramType = "query", dataType="Long"),
                @ApiImplicitParam(name = "gId", value = "优秀青年id（推荐人）", required = false, paramType = "query", dataType="Long"),
                @ApiImplicitParam(name = "goodmanId", value = "优秀青年id", required = true, paramType = "query", dataType="Long"),
                @ApiImplicitParam(name = "activityTitle", value = "活动名称", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "content", value = "内容", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueGatheringGood.goodmanId", msg = "缺少优秀青年id"),
            @CheckParam(value = Check.NotNull, argName = "leagueGatheringGood.activityTitle", msg = "缺少活动标题（activityTitle）"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore LeagueGatheringGood leagueGatheringGood) {
        if(leagueGatheringGood.getCommissinorId() == null && leagueGatheringGood.getGmId() == null){
            return BusiResult.error("新增失败，失败原因：缺少推荐人id");
        }
        leagueGatheringGood.setCreater(JwtFilter.getUserId());

        int isSuccess = leagueGatheringGoodService.insertLeagueGatheringGood(leagueGatheringGood);
        if(isSuccess == 0){
            return BusiResult.error("新增失败，数据库插入失败");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 修改记录【请填写功能名称】
     */
    @ApiOperation(value = "修改记录", notes = "修改记录", httpMethod = "POST")
    @ApiImplicitParams({
                    @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="Long"),
                    @ApiImplicitParam(name = "activityTitle", value = "活动名称", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "content", value = "内容", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueGatheringGood.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore LeagueGatheringGood leagueGatheringGood) {
        int isSuccess = leagueGatheringGoodService.updateLeagueGatheringGood(leagueGatheringGood);
        if(isSuccess == 0){
            return BusiResult.error("修改失败");
        }
        return BusiResult.success("修改成功");
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "删除记录（物理删除）", notes = "删除记录（物理删除）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/remove/{id}")
    public BusiResult remove(@PathVariable Long id) {
        boolean isSuccess = leagueGatheringGoodService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
