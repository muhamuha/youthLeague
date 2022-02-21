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
import com.wzxc.busi.vo.LeagueIndustry;
import com.wzxc.webservice.shiro.JwtFilter;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueIndustryServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-02
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueIndustry")
@Api(tags="青联界别组管理类")
public class LeagueIndustryController extends BaseController {

    @Autowired
    private LeagueIndustryServiceImpl leagueIndustryService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "name", value = "委员姓名", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "iphone", value = "手机号", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "industryId", value = "界别id", required = false, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "position", value = "职位", required = false, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueIndustry.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeagueIndustry leagueIndustry) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        startPage();
        List<LeagueIndustry> list = leagueIndustryService.selectLeagueIndustryList(leagueIndustry);
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
            @ApiResponse(code = 13000, message = "OK", response = LeagueIndustry.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        LeagueIndustry leagueIndustry = leagueIndustryService.queryOneById(id);
        if(leagueIndustry == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", leagueIndustry);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "commissinorId", value = "委员id", required = false, paramType = "query", dataType="Long"),
                @ApiImplicitParam(name = "position", value = "职位", required = false, paramType = "query", dataType="Long"),
                @ApiImplicitParam(name = "industryId", value = "社团id", required = false, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueIndustry.position", msg = "缺少职位"),
            @CheckParam(value = Check.NotNull, argName = "leagueIndustry.industryId", msg = "缺少界别组id"),
            @CheckParam(value = Check.NotNull, argName = "leagueIndustry.commissinorId", msg = "缺少委员id"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore LeagueIndustry leagueIndustry) {
        leagueIndustry.setCreater(JwtFilter.getUserId());

        int isSuccess = leagueIndustryService.insertLeagueIndustry(leagueIndustry);
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
                    @ApiImplicitParam(name = "id", value = "", required = false, paramType = "query", dataType="Long"),
                    @ApiImplicitParam(name = "commissinorId", value = "委员id", required = true, paramType = "query", dataType="Long"),
                    @ApiImplicitParam(name = "position", value = "职位", required = true, paramType = "query", dataType="Long"),
                    @ApiImplicitParam(name = "industryId", value = "社团id", required = true, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueIndustry.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore LeagueIndustry leagueIndustry) {
        int isSuccess = leagueIndustryService.updateLeagueIndustry(leagueIndustry);
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
        boolean isSuccess = leagueIndustryService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
