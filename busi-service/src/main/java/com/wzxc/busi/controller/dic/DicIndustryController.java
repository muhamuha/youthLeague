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
import com.wzxc.busi.vo.DicIndustry;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.DicIndustryServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-26
 */
@RestController
@CrossOrigin
@RequestMapping("/dicIndustry")
@Api(tags="字典类（行业）")
public class DicIndustryController extends BaseController {

    @Autowired
    private DicIndustryServiceImpl dicIndustryService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "industryName", value = "行业名称", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = DicIndustry.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore DicIndustry dicIndustry) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<DicIndustry> list = dicIndustryService.selectDicIndustryList(dicIndustry);
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
            @ApiResponse(code = 13000, message = "OK", response = DicIndustry.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        DicIndustry dicIndustry = dicIndustryService.getById(id);
        if(dicIndustry == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", dicIndustry);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
                        @ApiImplicitParam(name = "industryName", value = "行业名称", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
                    @CheckParam(value = Check.NotNull, argName = "dicIndustry.industry_name", msg = "缺少行业名称字段（industry_name）"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore DicIndustry dicIndustry)
    {
        int isSuccess = dicIndustryService.insertDicIndustry(dicIndustry);
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
                        @ApiImplicitParam(name = "id", value = "系统主键", required = true, paramType = "query", dataType="Long"),
                        @ApiImplicitParam(name = "industryName", value = "行业名称", required = true, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "dicIndustry.id", msg = "缺少系统主键（id）"),
                    @CheckParam(value = Check.NotNull, argName = "dicIndustry.industry_name", msg = "缺少行业名称字段（industry_name）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore DicIndustry dicIndustry) {
        int isSuccess = dicIndustryService.updateDicIndustry(dicIndustry);
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
        boolean isSuccess = dicIndustryService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }


}
