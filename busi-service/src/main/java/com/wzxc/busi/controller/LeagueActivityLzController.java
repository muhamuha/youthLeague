package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wzxc.busi.en.lz.LzType;
import com.wzxc.busi.service.impl.LeagueActRegisterServiceImpl;
import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueActRegister;
import com.wzxc.busi.vo.LeagueActivity;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueActivityLz;
import com.wzxc.webservice.shiro.JwtFilter;
import com.wzxc.webservice.shiro.JwtUtil;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueActivityLzServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-01
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueActivityLz")
@Api(tags="履职类")
public class LeagueActivityLzController extends BaseController {

    @Autowired
    private LeagueActivityLzServiceImpl leagueActivityLzService;
    @Autowired
    private LeagueActRegisterServiceImpl leagueActRegisterService;
    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询履职列表", notes = "查询履职列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "lzType", value = "履职类型（1：活动履职，2：我的荣誉，不传默认活动履职）", required = false, paramType = "query", dataType="Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueActivityLz.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeagueActivityLz leagueActivityLz) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        // 查询用户id
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());

        // 查询列表数据
        startPage();
        if(leagueActivityLz.getLzType() == LzType.ACTIVITY.getValue()){
            List<LeagueActRegister> list = leagueActRegisterService.myLzList(leagueCommissinor.getId());
            buildTableInfo(list, resultMap);
        } else if(leagueActivityLz.getLzType() == LzType.OTHER.getValue()){
            LeagueActivityLz lz = new LeagueActivityLz();
            lz.setCommissinorId(leagueCommissinor.getId());
            List<LeagueActivityLz> list = leagueActivityLzService.selectLeagueActivityLzList(lz);
            buildTableInfo(list, resultMap);
        }
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
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "lzType", value = "履职类型（1：活动履职，2：我的荣誉，不传默认活动履职）", required = false, paramType = "query", dataType="Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueActivityLz.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail")
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "id", msg = "缺少主键id"),
    })
    public Object getById(@RequestParam Long id, @RequestParam(required = false) Integer lzType) {
        Map<String, Object> resultMap = new HashMap<>();

        if(lzType == null || lzType == LzType.ACTIVITY.getValue()){
            LeagueActRegister leagueActRegister = leagueActRegisterService.queryOneById(id);
            resultMap.put("data", leagueActRegister);
        } else if(lzType == LzType.OTHER.getValue()){
            LeagueActivityLz leagueActivityLz = leagueActivityLzService.queryOneById(id);
            resultMap.put("data", leagueActivityLz);
        }
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增我的荣誉", notes = "新增我的荣誉", httpMethod = "POST")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "honorName", value = "荣誉名称", required = true, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "honorType", value = "荣誉类型", required = true, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "honorTime", value = "荣誉时间", required = true, paramType = "query", dataType="Date"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueActivityLz.honorName", msg = "缺少荣誉名称（honorName）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivityLz.honorType", msg = "缺少荣誉类型（honorType）"),
            @CheckParam(value = Check.NotNull, argName = "leagueActivityLz.honorTime", msg = "缺少荣誉时间（honorTime）"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore LeagueActivityLz leagueActivityLz) {
        // 获取用户id
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());

        leagueActivityLz.setCreater(JwtFilter.getUserId());
        leagueActivityLz.setCommissinorId(leagueCommissinor.getId());
        leagueActivityLz.setScore(getScoreByHonorType(leagueActivityLz.getHonorType()));

        int isSuccess = leagueActivityLzService.insertLeagueActivityLz(leagueActivityLz);
        if(isSuccess == 0){
            return BusiResult.error("新增失败");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 通过荣誉类型获取履职分
     *
     * @param honorType
     * @return
     */
    private int getScoreByHonorType(String honorType){
        int score = 0;
        switch (honorType){
            case "国家": score = 6; break;
            case "省级": score = 4; break;
            case "市级": score = 2; break;
        }
        return score;
    }

    /**
     * 修改记录【请填写功能名称】
     */
    @ApiOperation(value = "修改其他荣誉", notes = "修改其他荣誉", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "honorName", value = "荣誉名称", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "honorType", value = "荣誉类型", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "honorTime", value = "荣誉时间", required = false, paramType = "query", dataType="Date"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueActivityLz.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore LeagueActivityLz leagueActivityLz) {
        if(StringUtils.isNotEmpty(leagueActivityLz.getHonorType())){
            leagueActivityLz.setScore(getScoreByHonorType(leagueActivityLz.getHonorType()));
        }

        int isSuccess = leagueActivityLzService.updateLeagueActivityLz(leagueActivityLz);
        if(isSuccess == 0){
            return BusiResult.error("修改失败");
        }
        return BusiResult.success("修改成功");
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "删除其他荣誉（物理删除）", notes = "删除其他荣誉（物理删除）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/remove/{id}")
    public BusiResult remove(@PathVariable Long id) {
        boolean isSuccess = leagueActivityLzService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

    /**
     * 我的履职总积分【请填写功能名称】
     */
    @ApiOperation(value = "我的履职总积分", notes = "我的履职总积分", httpMethod = "GET")
    @ApiImplicitParams({})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/my/score")
    public BusiResult myScoreTotal(){
        Map<String, Object> resultMap = new HashMap<>();

        // 获取用户id
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());

        List<Map<String, Object>> list = leagueActivityLzService.scoreTotal(leagueCommissinor.getId());
        if (list.size() > 0){
            resultMap.put("data", list.get(0));
        }
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 履职总积分排行榜【请填写功能名称】
     */
    @ApiOperation(value = "履职总积分排行榜", notes = "履职总积分排行榜", httpMethod = "GET")
    @ApiImplicitParams({})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/list/score")
    public BusiResult scoreTotalsList() throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        startPage();
        List<Map<String, Object>> list = leagueActivityLzService.scoreTotal(null);
        buildTableInfo(list, resultMap);

        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 履职画像管理【请填写功能名称】
     */
    @ApiOperation(value = "履职画像管理", notes = "履职画像管理", httpMethod = "GET")
    @ApiImplicitParams({})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/lz/list/{year}")
    public BusiResult lzList(@PathVariable("year") String year) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        // 获取用户id
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());

        // 查询履职信息
        List<LeagueActRegister> list = leagueActRegisterService.myLzList(leagueCommissinor.getId());
        // 查询荣誉信息
        LeagueActivityLz a = new LeagueActivityLz();
        a.setCommissinorId(leagueCommissinor.getId());
        List<LeagueActivityLz> LeagueActivityLzList = leagueActivityLzService.selectLeagueActivityLzList(a);

        resultMap.put("lzList", list);
        resultMap.put("honorList", LeagueActivityLzList);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 所有履职年份【请填写功能名称】
     */
    @ApiOperation(value = "所有履职年份", notes = "所有履职年份", httpMethod = "GET")
    @ApiImplicitParams({})
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/lz/year")
    public BusiResult lzYear() throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        // 获取用户id
        LeagueCommissinor leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());

        List<String> list = leagueActivityLzService.yearList();
        resultMap.put("data", list);
        return BusiResult.success("查询成功", resultMap);
    }

}
