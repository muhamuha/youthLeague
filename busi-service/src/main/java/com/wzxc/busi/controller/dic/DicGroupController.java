package com.wzxc.busi.controller.dic;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.annotation.InsertBatchParam;
import com.wzxc.common.annotation.InsertBatchParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.BusiResult;
import com.wzxc.common.core.domain.MyEntry;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.MapDataUtil;
import com.wzxc.common.utils.file.ExcelUtils;
import com.wzxc.common.utils.reflect.ReflectUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.busi.vo.DicGroup;
import io.swagger.annotations.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wzxc.busi.service.impl.DicGroupServiceImpl;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 前端控制器 【请填写功能名称】
 * 
 * @author MUHAMUHA
 * @date 2021-11-30
 */
@RestController
@CrossOrigin
@RequestMapping("/dicGroup")
@Api(tags="字典类（社团）")
public class DicGroupController extends BaseController {

    @Autowired
    private DicGroupServiceImpl dicGroupService;

    /**
     * 查询列表【请填写功能名称】
     */
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数（默认第1页）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数（默认10条）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "groupName", value = "青联团体名称", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = DicGroup.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/list")
    public BusiResult list(@RequestBody @ApiIgnore DicGroup dicGroup) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<DicGroup> list = dicGroupService.selectDicGroupList(dicGroup);
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
            @ApiResponse(code = 13000, message = "OK", response = DicGroup.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/detail/{id}")
    public Object getById(@PathVariable Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        DicGroup dicGroup = dicGroupService.getById(id);
        if(dicGroup == null){
            return BusiResult.error("查询失败");
        }
        resultMap.put("data", dicGroup);
        return BusiResult.success("查询成功", resultMap);
    }

    /**
     * 新增记录【请填写功能名称】
     */
    @ApiOperation(value = "新增记录", notes = "新增记录", httpMethod = "POST")
    @ApiImplicitParams({
                        @ApiImplicitParam(name = "groupName", value = "青联团体名称", required = true, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "groupType", value = "组织类型：	1. 社会团体	2. 基金会	3. 社会服务机构	4. 人民团体	5. 其他", required = true, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "isRegister", value = "是否民政局注册：	1. 是	2. 否	3. 未知", required = false, paramType = "query", dataType="Integer"),
                        @ApiImplicitParam(name = "upperGroup", value = "主管业主单位", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
                    @CheckParam(value = Check.NotNull, argName = "dicGroup.groupName", msg = "缺少青联团体名称字段（group_name）"),
                    @CheckParam(value = Check.NotNull, argName = "dicGroup.groupType", msg = "缺少组织类型：	1. 社会团体	2. 基金会	3. 社会服务机构	4. 人民团体	5. 其他字段（group_type）"),
    })
    @PostMapping("/add")
    public BusiResult add(@RequestBody @ApiIgnore DicGroup dicGroup)
    {
        int isSuccess = dicGroupService.insertDicGroup(dicGroup);
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
                        @ApiImplicitParam(name = "groupName", value = "青联团体名称", required = true, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "groupType", value = "组织类型：	1. 社会团体	2. 基金会	3. 社会服务机构	4. 人民团体	5. 其他", required = true, paramType = "query", dataType="String"),
                        @ApiImplicitParam(name = "isRegister", value = "是否民政局注册：	1. 是	2. 否	3. 未知", required = false, paramType = "query", dataType="Integer"),
                        @ApiImplicitParam(name = "upperGroup", value = "主管业主单位", required = false, paramType = "query", dataType="String"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "dicGroup.id", msg = "缺少系统主键（id）"),
                    @CheckParam(value = Check.NotNull, argName = "dicGroup.groupName", msg = "缺少青联团体名称字段（group_name）"),
                    @CheckParam(value = Check.NotNull, argName = "dicGroup.groupType", msg = "缺少组织类型：	1. 社会团体	2. 基金会	3. 社会服务机构	4. 人民团体	5. 其他字段（group_type）"),
    })
    @PostMapping("/edit")
    public BusiResult edit(@RequestBody @ApiIgnore DicGroup dicGroup) {
        int isSuccess = dicGroupService.updateDicGroup(dicGroup);
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
        boolean isSuccess = dicGroupService.removeById(id);
        if(!isSuccess){
            return BusiResult.error("删除失败");
        }
        return BusiResult.success("删除成功");
    }

    /**
     * 通过接口 - 批导入
     */
    @ApiOperation(value = "批导入-接口导入", notes = "批导入-接口导入", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "导入数据", required = true, paramType = "query", dataType="Object"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @InsertBatchParams({
            @InsertBatchParam(value = Check.Any, fieldNameZh = "市青联团体会员名称", fieldName = "group_name"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "组织类型", fieldName = "group_type"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "是否在民政注册", fieldName = "is_register"),
            @InsertBatchParam(value = Check.Any, fieldNameZh = "业务主管单位", fieldName = "upper_group"),
    })
    @PostMapping(value = "/batch")
    public BusiResult insertBatch(@RequestParam("file") MultipartFile file) throws Exception {
        Method method = ReflectUtils.getAccessibleMethodByName(this, "insertBatch", 1);
        Annotation annotation = ReflectUtils.getAnntationByMethod(method, "InsertBatchParams");
        InsertBatchParam[] insertBatchParams = ((InsertBatchParams) annotation).value();
        if (file.isEmpty()) {
            return BusiResult.error("上传失败，失败原因：文件未空");
        }
        // 获取表格中的中文名称
        List<String> fieldNameZhList = ExcelUtils.exportTitleListFromExcel(file);
        // 获取字段名称
        List<MyEntry<String, Integer>> fieldNameList = ExcelUtils.getFieldNameList(fieldNameZhList, insertBatchParams);
        // 获取数据
        List<Map<String, Object>> contentList = ExcelUtils.exportContentListFromExcel(file, fieldNameList, insertBatchParams);
        if(contentList == null || contentList.size() == 0){
            return BusiResult.success("导入成功");
        }
        for(Map<String, Object> map : contentList){
            Object dicGroup = MapDataUtil.getObjectFromMap(map, DicGroup.class);
            dicGroupService.save((DicGroup) dicGroup);
        }

        return BusiResult.success("批导入成功");
    }


}
