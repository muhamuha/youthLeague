package com.wzxc.busi.controller.dic;

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
import com.wzxc.busi.vo.DicActivity;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.DicActivityServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-23
 */
@RestController
@CrossOrigin
@RequestMapping("/dicActivity")
@Api(tags="字典类（活动）")
public class DicActivityController extends BaseController {

    @Autowired
    private DicActivityServiceImpl dicActivityService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
                @ApiImplicitParam(name = "score", value = "分数", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "activityName", value = "活动名称", required = false, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "id", value = "主键", required = false, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = DicActivity.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore DicActivity dicActivity) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<DicActivity> list = dicActivityService.selectDicActivityList(dicActivity);
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
            @ApiResponse(code = 13000, message = "OK", response = DicActivity.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        DicActivity dicActivity = dicActivityService.getById(id);
        if(dicActivity == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", dicActivity);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
                        @ApiImplicitParam(name = " score", value = "分数", required = true, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "activityName", value = "活动名称", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
                    @CheckParam(value = Check.NotNull, argName = "dicActivity.score", msg = "缺少分数字段（score）"),
                    @CheckParam(value = Check.NotNull, argName = "dicActivity.activityName", msg = "缺少活动名称字段（activity_name）"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore DicActivity dicActivity)
    {
        int isSuccess = dicActivityService.insertDicActivity(dicActivity);
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
                        @ApiImplicitParam(name = "score", value = "分数", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "activityName", value = "活动名称", required = false, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType="Long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "dicActivity.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore DicActivity dicActivity) {
        int isSuccess = dicActivityService.updateDicActivity(dicActivity);
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
        boolean isSuccess = dicActivityService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
