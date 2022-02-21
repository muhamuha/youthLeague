package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.wzxc.busi.service.impl.LeagueCommissinorServiceImpl;
import com.wzxc.busi.vo.LeagueCommissinor;
import com.wzxc.busi.vo.LeagueGoodmanRecommend;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueGoodman;
import com.wzxc.webservice.shiro.JwtFilter;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.glassfish.hk2.classmodel.reflect.util.LinkedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueGoodmanServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-07
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueGoodman")
@Api(tags="优秀青年类")
public class LeagueGoodmanController extends BaseController {

    @Autowired
    private LeagueGoodmanServiceImpl leagueGoodmanService;
    @Autowired
    private LeagueCommissinorServiceImpl leagueCommissinorService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "iphone", value = "手机号码", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "commissinorName", value = "推荐委员", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "commissinorId", value = "推荐委员id", required = false, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueGoodman.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeagueGoodman leagueGoodman) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        startPage();
        List<LeagueGoodman> list = leagueGoodmanService.selectLeagueGoodmanList(leagueGoodman);
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
            @ApiResponse(code = 13000, message = "OK", response = LeagueGoodman.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Long id) {
        Map<String, Object> resultMap = new HashMap<>();

        LeagueGoodman leagueGoodman = leagueGoodmanService.queryOneById(id);
        if(leagueGoodman == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", leagueGoodman);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "iphone", value = "手机号码", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "address", value = "家庭住址", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "education", value = "教育情况", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "company", value = "所在公司", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "honor_level", value = "荣誉层级	1. 省级	2. 市级	3. 县级", required = false, paramType = "query", dataType="Integer"),
            @ApiImplicitParam(name = "honor_name", value = "荣誉名称", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "honor_file", value = "荣誉附件地址", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "org_position", value = "政府所在单位和职务", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "picture", value = "个人照片地址", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "workplace", value = "工作所在地", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "industry", value = "所在行业（字典表）", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "updte_time", value = "最新修改时间", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "creater", value = "创建人（浙政钉id）", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "gender", value = "性别", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", required = false, paramType = "query", dataType="Date"),
            @ApiImplicitParam(name = "idcard", value = "身份证号", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "household", value = "户籍", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "origin", value = "籍贯", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "nation", value = "民族", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "political_status", value = "政治面貌", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "degree", value = "学位", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "org_title", value = "职称（字典）", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "org_office", value = "职级（字典）", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "vocation", value = "职业（字典）", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "social_office", value = "社会职务", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deputy_party", value = "市级以上党代表的情况", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deputy_npc", value = "市级以上人大代表情况", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deputy_cppcc", value = "市级以上政协委员的情况", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "location", value = "所在地", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "organization", value = "所在政府单位", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "position", value = "公司职务", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "campus", value = "毕业院校", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "commissinorId", value = "推荐委员id", required = false, paramType = "query", dataType="Long"),
            @ApiImplicitParam(name = "goodmanId", value = "优秀青年id（推荐方）", required = false, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueGoodman.name", msg = "缺少姓名（name）"),
            @CheckParam(value = Check.NotNull, argName = "leagueGoodman.iphone", msg = "缺少手机号（iphone）"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore LeagueGoodman leagueGoodman) {
        // 查询推荐委员id
        LeagueCommissinor leagueCommissinor = null;
        if(leagueGoodman.getCommissinorId() == null && leagueGoodman.getGoodmanId() == null){
            leagueCommissinor = leagueCommissinorService.queryOne(JwtFilter.getUserId());
            Long commissinorId = leagueCommissinor.getId();
            leagueGoodman.setCommissinorId(commissinorId);
        }

        leagueGoodman.setCreater(JwtFilter.getUserId());

        int isSuccess = leagueGoodmanService.insertLeagueGoodman(leagueGoodman);
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
                    @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType="Long"),
                    @ApiImplicitParam(name = "name", value = "姓名", required = true, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "iphone", value = "手机号码", required = true, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "address", value = "家庭住址", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "education", value = "教育情况", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "company", value = "所在公司", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "honor_level", value = "荣誉层级	1. 省级	2. 市级	3. 县级", required = false, paramType = "query", dataType="Integer"),
                    @ApiImplicitParam(name = "honor_name", value = "荣誉名称", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "honor_file", value = "荣誉附件地址", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "org_position", value = "政府所在单位和职务", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "picture", value = "个人照片地址", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "workplace", value = "工作所在地", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "industry", value = "所在行业（字典表）", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "updte_time", value = "最新修改时间", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "creater", value = "创建人（浙政钉id）", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "gender", value = "性别", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "birthday", value = "出生日期", required = false, paramType = "query", dataType="Date"),
                    @ApiImplicitParam(name = "idcard", value = "身份证号", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "household", value = "户籍", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "origin", value = "籍贯", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "nation", value = "民族", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "political_status", value = "政治面貌", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "degree", value = "学位", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "org_title", value = "职称（字典）", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "org_office", value = "职级（字典）", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "vocation", value = "职业（字典）", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "social_office", value = "社会职务", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "deputy_party", value = "市级以上党代表的情况", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "deputy_npc", value = "市级以上人大代表情况", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "deputy_cppcc", value = "市级以上政协委员的情况", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "location", value = "所在地", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "organization", value = "所在政府单位", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "position", value = "公司职务", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "campus", value = "毕业院校", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueGoodman.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore LeagueGoodman leagueGoodman) {
        int isSuccess = leagueGoodmanService.updateLeagueGoodman(leagueGoodman);
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
        boolean isSuccess = leagueGoodmanService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "推荐图谱", notes = "推荐图谱", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recommendId", value = "推荐人主键", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "type", value = "图谱类型（1：委员 -> 优秀青年 2：优秀青年 -> 优秀青年）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/recommend")
    public BusiResult recommend(@RequestParam("recommendId") Long recommendId, @RequestParam(value = "type") Integer type) {
        Map<String, Object> resultMap = new HashMap<>();
        List<LeagueGoodmanRecommend> lg = new ArrayList<>();
        LeagueGoodmanRecommend parent = new LeagueGoodmanRecommend();

        LeagueGoodmanRecommend leagueGoodmanRecommend = new LeagueGoodmanRecommend();
        switch (type){
            case 1: {
                leagueGoodmanRecommend.setCommissinorId(recommendId);

                LeagueCommissinor c = leagueCommissinorService.getById(recommendId);
                parent.setId("commissinor_" + recommendId);
                parent.setName(c.getName());
                break;
            }
            case 2: {
                leagueGoodmanRecommend.setGoodmanId(recommendId);

                LeagueGoodman g = leagueGoodmanService.getById(recommendId);
                parent.setId("goodman_" + recommendId);
                parent.setName(g.getName());
                break;
            }
            default: {
                return BusiResult.success("查询成功", resultMap);
            }
        }
        lg.add(parent);

        List<LeagueGoodmanRecommend> leagueGoodmanRecommendList = leagueGoodmanService.queryRecommendList(leagueGoodmanRecommend);
        Queue<LeagueGoodmanRecommend> queue = new LinkedList<>();
        for(LeagueGoodmanRecommend r : leagueGoodmanRecommendList){
            queue.offer(r);
        }
        while(queue.size() > 0){
            LeagueGoodmanRecommend r = queue.poll();
            if(r.getChildList() != null && r.getChildList().size() > 0){
                for(LeagueGoodmanRecommend e : r.getChildList()){
                    queue.offer(e);
                }
            }
            lg.add(r);
        }

        resultMap.put("data", lg);
        return BusiResult.success("查询成功", resultMap);
    }


}
