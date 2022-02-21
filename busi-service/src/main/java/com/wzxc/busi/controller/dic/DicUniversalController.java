package com.wzxc.busi.controller.dic;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.DicUniversal;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.DicUniversalServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-12-01
 */
@RestController
@CrossOrigin
@RequestMapping("/dicUniversal")
@Api(tags="字典表（专业技术职称、职级、职业、界别、社团职位、界别职位）")
public class DicUniversalController extends BaseController {

    @Autowired
    private DicUniversalServiceImpl dicUniversalService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "dicKey", value = "字典类型（专业技术职称、职级、职业、界别、社团职位、界别职位）", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = DicUniversal.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore DicUniversal dicUniversal) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        dicUniversal.setType("youth-league");

        startPage();
        List<DicUniversal> list = dicUniversalService.selectDicUniversalList(dicUniversal);
        buildTableInfo(list, resultMap);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "dicValue", value = "字典值", required = true, paramType = "query", dataType="String"),
                @ApiImplicitParam(name = "weight", value = "权重", required = false, paramType = "query", dataType="Integer"),
                @ApiImplicitParam(name = "dicKey", value = "字典类型（专业技术职称、职级、职业、界别、社团职位、界别职位）", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "dicUniversal.dicValue", msg = "缺少value值"),
            @CheckParam(value = Check.NotNull, argName = "dicUniversal.dicKey", msg = "缺少key值"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore DicUniversal dicUniversal) {
        dicUniversal.setType("youth-league");

        int isSuccess = dicUniversalService.insertDicUniversal(dicUniversal);
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
                    @ApiImplicitParam(name = "dicValue", value = "", required = false, paramType = "query", dataType="String"),
                    @ApiImplicitParam(name = "weight", value = "", required = false, paramType = "query", dataType="Integer"),
                    @ApiImplicitParam(name = "dicKey", value = "", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "dicUniversal.id", msg = "缺少系统主键（id）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore DicUniversal dicUniversal) {
        dicUniversal.setType("youth-league");

        int isSuccess = dicUniversalService.updateDicUniversal(dicUniversal);
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
        boolean isSuccess = dicUniversalService.remove(Wrappers.<DicUniversal>lambdaQuery()
                .eq(DicUniversal::getId, id)
                .eq(DicUniversal::getType, "youth-league"));
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
