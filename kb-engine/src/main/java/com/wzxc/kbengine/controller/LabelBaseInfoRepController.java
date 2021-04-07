package com.wzxc.kbengine.controller;

import com.github.pagehelper.PageInfo;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.kbengine.service.impl.LabelBaseInfoRepServiceImpl;
import com.wzxc.kbengine.service.impl.LabelGroupRepServiceImpl;
import com.wzxc.kbengine.service.impl.RefLabelRepServiceImpl;
import com.wzxc.kbengine.vo.LabelBaseInfoRep;
import com.wzxc.kbengine.vo.LabelGroupRep;
import com.wzxc.kbengine.vo.PolicyBaseInfoRep;
import com.wzxc.kbengine.vo.RefLabelRep;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/labelBaseInfo")
@Api(tags="知识库标签操作接口")
@Slf4j
public class LabelBaseInfoRepController extends BaseController {

    @Autowired
    private LabelBaseInfoRepServiceImpl labelBaseInfoRepService;

    @Autowired
    private LabelGroupRepServiceImpl labelGroupRepService;

    @Autowired
    private RefLabelRepServiceImpl refLabelRepService;

    @ApiOperation(value = "查询标签列表（只查询有效标签）", notes = "查询标签列表（只查询有效标签）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelName",value = "标签名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "createTimeBegin",value = "创建时间（from，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "createTimeEnd",value = "创建时间（to，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "labelType",value = "标签类型（1：任务 2：指标 3：政策 99：通用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "groupBaseInfoRepId",value = "所属分组id（-1：通用标签，分组id：分组标签，默认所有标签）", required = false, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "pageSize",value = "页码（默认10）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageNum",value = "页数（默认1）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isPage",value = "是否分页（1：不分页，2：分页，默认分页）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LabelBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/labels/get")
    public KbengineResult list(@RequestBody @ApiIgnore LabelBaseInfoRep labelBaseInfoRep) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<LabelBaseInfoRep> labelBaseInfoReps = new ArrayList<>();
        if(labelBaseInfoRep.getGroupBaseInfoRepId() == null){
            labelBaseInfoReps = labelBaseInfoRepService.selectLabelBaseInfoRepList(labelBaseInfoRep);
        } else if(labelBaseInfoRep.getGroupBaseInfoRepId() == -1){ // 通用标签
            labelBaseInfoReps = labelBaseInfoRepService.selectGeneralLableList(labelBaseInfoRep);
        } else{
            labelBaseInfoReps = labelBaseInfoRepService.selectLabelListByGroupId(labelBaseInfoRep);
        }
        buildTableInfo(labelBaseInfoReps, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    @ApiOperation(value = "新增标签", notes = "新增标签", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelName",value = "标签名称", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "remark",value = "备注", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "groupBaseInfoRepIdList",value = "分组id（数组）", required = false, paramType = "query", dataType="long[]"),
            @ApiImplicitParam(name = "labelType",value = "标签类型（1：任务 2：指标 3：政策 99：通用，默认99通用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "labelStatus",value = "标签状态（1：有效 2：无效 3：冻结，默认1有效）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LabelBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "labelBaseInfoRep.labelName", msg = "缺少标签名称"),
    })
    @PostMapping("/labels/add")
    public KbengineResult add(@RequestBody @ApiIgnore LabelBaseInfoRep labelBaseInfoRep){
        // 判断是否已有重复名称
        if(labelBaseInfoRepService.havaRepeatName(labelBaseInfoRep)){
            return KbengineResult.error("存在重复名称的标签");
        }
        // 如果含有通用标签默认只有通用标签
        if(labelBaseInfoRep.getGroupBaseInfoRepIdList() != null && labelBaseInfoRep.getGroupBaseInfoRepIdList().length > 0){
            if(Arrays.asList(labelBaseInfoRep.getGroupBaseInfoRepIdList()).contains(-1L)){
                labelBaseInfoRep.setGroupBaseInfoRepIdList(new Long[]{-1L});
            }
        }
        int isSuccess = labelBaseInfoRepService.insertLabelBaseInfoRep(labelBaseInfoRep);
        if(isSuccess > 0){
            if(labelBaseInfoRep.getGroupBaseInfoRepIdList() != null && labelBaseInfoRep.getGroupBaseInfoRepIdList().length > 0){ // 添加分组信息
                long id = labelBaseInfoRep.getId();
                for(Long groupId : labelBaseInfoRep.getGroupBaseInfoRepIdList()){
                    LabelGroupRep labelGroupRep = new LabelGroupRep();
                    labelGroupRep.setLabelBaseInfoRepClass(labelBaseInfoRep.getLabelClass());
                    labelGroupRep.setGroupBaseInfoRepId(groupId);
                    labelGroupRepService.insertLabelGroupRep(labelGroupRep);
                }
            }
            return KbengineResult.success("新增成功");
        } else{
            return KbengineResult.error("新增失败");
        }
    }

    @ApiOperation(value = "修改标签", notes = "修改标签", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "labelName",value = "标签名称", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "labelClass",value = "标签类", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "groupBaseInfoRepIdList",value = "分组id（数组）", required = false, paramType = "query", dataType="long[]"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LabelBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "labelBaseInfoRep.id", msg = "缺少系统主键"),
            @CheckParam(value = Check.NotNull, argName = "labelBaseInfoRep.labelName", msg = "缺少标签名称"),
            @CheckParam(value = Check.NotNull, argName = "labelBaseInfoRep.labelClass", msg = "缺少标签类（labelClass）"),
    })
    @PostMapping("/labels/update")
    public KbengineResult update(@RequestBody @ApiIgnore LabelBaseInfoRep labelBaseInfoRep){
        // 根据系统主键查询标签，对比修改的字段
        LabelBaseInfoRep labelBaseInfoRep1 = labelBaseInfoRepService.selectLabelBaseInfoRepById(labelBaseInfoRep.getId());
        Long id = labelBaseInfoRep.getId();
        if(!labelBaseInfoRep1.getLabelName().equals(labelBaseInfoRep.getLabelName())){ // 标签名称改了，需要新增标签，不能修改
            labelBaseInfoRep.setCreator(labelBaseInfoRep.getUpdator());
            labelBaseInfoRepService.insertLabelBaseInfoRep(labelBaseInfoRep);
            // 将上一个标签设置为无效
            LabelBaseInfoRep labelBaseInfoRep2 = new LabelBaseInfoRep();
            labelBaseInfoRep2.setId(id);
            labelBaseInfoRep2.setLabelStatus(2);
            labelBaseInfoRepService.updateLabelBaseInfoRep(labelBaseInfoRep2);
        }
        // 删除以前的分组关系
        labelGroupRepService.clearGroup(labelBaseInfoRep.getLabelClass());
        // 如果含有通用标签默认只有通用标签
        if(labelBaseInfoRep.getGroupBaseInfoRepIdList() != null && labelBaseInfoRep.getGroupBaseInfoRepIdList().length > 0){
            if(Arrays.asList(labelBaseInfoRep.getGroupBaseInfoRepIdList()).contains(-1L)){
                labelBaseInfoRep.setGroupBaseInfoRepIdList(new Long[]{-1L});
            }
        }
        for(Long groupId : labelBaseInfoRep.getGroupBaseInfoRepIdList()){
            LabelGroupRep labelGroupRep = new LabelGroupRep();
            labelGroupRep.setLabelBaseInfoRepClass(labelBaseInfoRep.getLabelClass());
            labelGroupRep.setGroupBaseInfoRepId(groupId);
            labelGroupRepService.insertLabelGroupRep(labelGroupRep);
        }
        return KbengineResult.success("修改成功");
    }

    /**
     * 删除标签
     */
    @ApiOperation(value = "删除标签", notes = "删除标签", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "labelBaseInfoRep.id", msg = "缺少系统主键"),
    })
    @PostMapping("/labels/delete")
    public KbengineResult delete(@RequestBody @ApiIgnore LabelBaseInfoRep labelBaseInfoRep){
        // 判断是否是已经删除的标签
        LabelBaseInfoRep labelBaseInfoRep1 = labelBaseInfoRepService.selectLabelBaseInfoRepById(labelBaseInfoRep.getId());
        if(labelBaseInfoRep1.getLabelStatus() == 1){
            int isSuccess = labelBaseInfoRepService.deleteLabelBaseInfoRepById(labelBaseInfoRep);
            if(isSuccess > 0){
                // 删除对应的指标和分组的关系
                labelGroupRepService.clearGroup(labelBaseInfoRep1.getLabelClass());
                return KbengineResult.success("删除成功");
            } else{
                return KbengineResult.error("删除失败");
            }
        }
        return KbengineResult.success("删除成功");
    }

}
