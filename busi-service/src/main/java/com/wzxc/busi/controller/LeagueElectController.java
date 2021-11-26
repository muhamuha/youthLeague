package com.wzxc.busi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.LeagueElect;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.LeagueElectServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 *
 * @author MUHAMUHA
 * @date 2021-10-25
 */
@RestController
@CrossOrigin
@RequestMapping("/leagueElect")
@Api(tags = "届次管理类")
public class LeagueElectController extends BaseController {

    @Autowired
    private LeagueElectServiceImpl leagueElectService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "commisinorId", value = "委员id", required = false, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "commisinorName", value = "委员姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "electRegion", value = "当选的地区", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "提名单位", required = false, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "id", value = "主键", required = false, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isReelect", value = "是否连任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isStay", value = "是否留任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "year", value = "当选年份", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "iphone", value = "手机号", required = false, paramType = "query", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueElect.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore LeagueElect leagueElect) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<LeagueElect> list = leagueElectService.selectLeagueElectList(leagueElect);
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
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType = "int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LeagueElect.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        LeagueElect leagueElect = leagueElectService.getById(id);
        if (leagueElect == null) {
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", leagueElect);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commisinorId", value = "委员id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "commisinorName", value = "委员姓名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "electRegion", value = "当选的地区", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "提名单位", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isReelect", value = "是否连任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isStay", value = "是否留任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "year", value = "当选年份", required = true, paramType = "query", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueElect.commisinorName", msg = "缺少委员姓名（commisinorName）"),
            @CheckParam(value = Check.NotNull, argName = "leagueElect.commisinorId", msg = "缺少委员id（commisinorId）"),
            @CheckParam(value = Check.NotNull, argName = "leagueElect.electRegion", msg = "缺少当选地区（electRegion）"),
            @CheckParam(value = Check.NotNull, argName = "leagueElect.groupId", msg = "缺少提名单位（groupId）"),
            @CheckParam(value = Check.NotNull, argName = "leagueElect.year", msg = "缺少当选年份（year）"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore LeagueElect leagueElect) {
        // 判断 年份和委员id 是否已经存在
        LeagueElect elect = new LeagueElect();
        elect.setCommisinorId(leagueElect.getCommisinorId());
        elect.setYear(leagueElect.getYear());
        List<LeagueElect> leagueElects = leagueElectService.selectLeagueElectList(elect);
        if(leagueElects.size() > 0){
            return BusiResult.error("新增失败，原因：所选年份已存在该委员");
        }
        int isSuccess = leagueElectService.insertLeagueElect(leagueElect);
        if (isSuccess == 0) {
            return BusiResult.error("新增失败");
        }
        return BusiResult.success("新增成功");
    }

    /**
     * 批量添加
     * @param leagueElectList
     * @return
     */
    @ApiOperation(value = "新增记录（批量）", notes = "新增记录（批量）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commisinorId", value = "委员id", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "commisinorName", value = "委员姓名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "electRegion", value = "当选的地区", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "提名单位", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isReelect", value = "是否连任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isStay", value = "是否留任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "year", value = "当选年份", required = true, paramType = "query", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/add/batch")
    public BusiResult addBatch(@RequestBody @ApiIgnore List<LeagueElect> leagueElectList) {
        List<String> ids = new ArrayList<>();
        LambdaQueryWrapper<LeagueElect> where = Wrappers.<LeagueElect>lambdaQuery();
        for(int count = 0; count < leagueElectList.size(); count++){
            LeagueElect leagueElect = leagueElectList.get(count);
            // 判断是否缺了必填字段
            if(StringUtils.isEmpty(leagueElect.getCommisinorId().toString())
                    || StringUtils.isEmpty(leagueElect.getCommisinorName())
                    || StringUtils.isEmpty(leagueElect.getElectRegion())
                    || StringUtils.isEmpty(leagueElect.getGroupId().toString())
                    || StringUtils.isEmpty(leagueElect.getYear())){
                return BusiResult.error("新增失败，失败原因：缺少必填字段");
            }
            // 判断 年份和委员id 是否已经存在
            where.eq(LeagueElect::getCommisinorId, leagueElect.getCommisinorId()).eq(LeagueElect::getYear, leagueElect.getYear());
            if(count < leagueElectList.size() - 1){
                where.or();
            }
        }
        int count = leagueElectService.count(where);
        if(count > 0){
            return BusiResult.error("新增失败，失败原因：个别届次已存在");
        }
        List<LeagueElect> leagueElects = leagueElectService.list(where);
        if(leagueElects.size() > 0){
            return BusiResult.error("新增失败，失败原因：所选年份已存在该委员");
        }
        boolean isSuccess = leagueElectService.saveBatch(leagueElectList);
        if (isSuccess == false) {
            return BusiResult.error("新增失败，失败原因：数据库拒绝新增操作");
        }
        return BusiResult.success("新增成功");
    }


    /**
     * 修改记录【请填写功能名称】
     */
    @ApiOperation(value = "修改记录", notes = "修改记录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commisinorId", value = "委员id", required = false, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "commisinorName", value = "委员姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "electRegion", value = "当选的地区", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "提名单位", required = false, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isReelect", value = "是否连任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isStay", value = "是否留任：	0：否	1：是", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "year", value = "当选年份", required = false, paramType = "query", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "leagueElect.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore LeagueElect leagueElect) {
        int isSuccess = leagueElectService.updateLeagueElect(leagueElect);
        if (isSuccess == 0) {
            return BusiResult.error("修改失败");
        }
        return BusiResult.success("修改成功");
    }

    /**
     * 删除记录【请填写功能名称】
     */
    @ApiOperation(value = "删除记录（物理删除）", notes = "删除记录（物理删除）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType = "long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/remove/{id}")
    public BusiResult remove(@PathVariable Long id) {
        boolean isSuccess = leagueElectService.removeById(id);
        if (!isSuccess) {
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
