package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.busi.service.impl.LeagueAnswerServiceImpl;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueAnswer;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeaguePublish;
import com.wzxc.webservice.shiro.JwtFilter;
import com.wzxc.webservice.shiro.JwtUtil;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeaguePublishServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2022-01-07
 */
@RestController
@CrossOrigin
@RequestMapping("/leaguePublish")
@Api(tags="发帖类")
public class LeaguePublishController extends BaseController {

    @Autowired
    private LeaguePublishServiceImpl leaguePublishService;
    @Autowired
    private LeagueAnswerServiceImpl leagueAnswerService;
    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询帖子列表", notes = "查询帖子列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "createrName", value = "发布者", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "title", value = "标题", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeaguePublish.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeaguePublish leaguePublish) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        startPage();
        List<LeaguePublish> list = leaguePublishService.selectLeaguePublishList(leaguePublish);
        buildTableInfo(list, resultMap);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
    * 查询详情【请填写功能名称】
    *
    * @param id
    * @return
    */
    @ApiOperation(value = "查询帖子详情", notes = "查询帖子详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeaguePublish.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();

        LeaguePublish leaguePublish = leaguePublishService.getById(id);
        if(leaguePublish == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", leaguePublish);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增帖子", notes = "新增帖子", httpMethod = "POST")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "content", value = "内容", required = true, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "title", value = "标题", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leaguePublish.title", msg = "缺少标题"),
            @CheckParam(value = Check.NotNull, argName = "leaguePublish.content", msg = "缺少内容"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore LeaguePublish leaguePublish) {
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());
        leaguePublish.setCreaterId(leagueCommissinor.getId());
        leaguePublish.setCreaterName(leagueCommissinor.getName());

        int isSuccess = leaguePublishService.insertLeaguePublish(leaguePublish);

        if(isSuccess == 0){
            return BusiResult.error("新增失败");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "删除贴子（逻辑删除）", notes = "删除贴子（逻辑删除）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/remove/{id}")
    public BusiResult remove(@PathVariable Long id) {
        boolean isSuccess = leaguePublishService.logicDelete(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

    //////////////// 发言相关 ////////////////

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增发言", notes = "新增发言", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "发言内容", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "publishId", value = "帖子id", required = true, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueAnswer.publishId", msg = "缺少帖子id"),
            @CheckParam(value = Check.NotNull, argName = "leagueAnswer.content", msg = "缺少发言内容"),
    })
    @PostMapping("/answer/add")
    public BusiResult addAnswer(@RequestBody @ApiIgnore LeagueAnswer leagueAnswer) {
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());
        leagueAnswer.setAnswerId(leagueCommissinor.getId());
        leagueAnswer.setAnswerName(leagueCommissinor.getName());

        int isSuccess = leagueAnswerService.insertLeagueAnswer(leagueAnswer);

        if(isSuccess == 0){
            return BusiResult.error("新增失败");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询发言列表", notes = "查询发言列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "answerName", value = "回复人姓名", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "publishId", value = "帖子id", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeaguePublish.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/answer/list")
    public BusiResult listAnswer(@RequestBody @ApiIgnore LeagueAnswer leagueAnswer) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        startPage();
        List<LeagueAnswer> list = leagueAnswerService.selectLeagueAnswerList(leagueAnswer);
        buildTableInfo(list, resultMap);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "删除发言（物理删除）", notes = "删除发言（物理删除）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/answer/remove/{id}")
    public BusiResult removeAnswer(@PathVariable Long id) {
        boolean isSuccess = leagueAnswerService.removeById(id);

        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

}
