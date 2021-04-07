package com.wzxc.kbengine.controller;

import com.github.pagehelper.PageInfo;
import com.wzxc.common.annotation.CheckParam;
import com.wzxc.common.annotation.CheckParams;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.ServletUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.validate.Check;
import com.wzxc.kbengine.service.impl.GroupBaseInfoRepServiceImpl;
import com.wzxc.kbengine.service.impl.LabelBaseInfoRepServiceImpl;
import com.wzxc.kbengine.service.impl.LabelGroupRepServiceImpl;
import com.wzxc.kbengine.service.impl.PolicyBaseInfoRepServiceImpl;
import com.wzxc.kbengine.vo.GroupBaseInfoRep;
import com.wzxc.kbengine.vo.LabelBaseInfoRep;
import com.wzxc.kbengine.vo.PolicyBaseInfoRep;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/groupBaseInfo")
@Api(tags="知识库分组操作接口")
@Slf4j
public class GroupBaseInfoRepController extends BaseController {

    @Autowired
    private GroupBaseInfoRepServiceImpl groupBaseInfoRepService;

    @Autowired
    private LabelGroupRepServiceImpl labelGroupRepService;

    @Autowired
    private LabelBaseInfoRepServiceImpl labelBaseInfoRepService;

    @ApiOperation(value = "查询分组列表", notes = "查询分组列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupName",value = "标签名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "creator",value = "创建人", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "createTimeBegin",value = "创建时间（from，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "createTimeEnd",value = "创建时间（to，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "groupType",value = "分组类型（ 1：标签 99：通用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "groupBaseInfoRepId",value = "所属分组id", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "pageSize",value = "页码（默认10）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageNum",value = "页数（默认1）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isPage",value = "是否分页（1：不分页，2：分页，默认分页）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = GroupBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/groups/get")
    public KbengineResult list(@RequestBody @ApiIgnore GroupBaseInfoRep groupBaseInfoRep) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<GroupBaseInfoRep> groupBaseInfoReps = groupBaseInfoRepService.selectGroupBaseInfoRepList(groupBaseInfoRep);
        buildTableInfo(groupBaseInfoReps, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    @ApiOperation(value = "新增分组", notes = "新增分组", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupName",value = "分组名称", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "groupType",value = "分组类型（ 1：标签 99：通用，默认99通用）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isParent",value = "是否是父节点（1：是父节点 2：不是父节点，默认1是父节点）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "parentId",value = "父节点id", required = false, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LabelBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "groupBaseInfoRep.groupName", msg = "缺少分组名称"),
    })
    @PostMapping("/groups/add")
    public KbengineResult add(@RequestBody @ApiIgnore GroupBaseInfoRep groupBaseInfoRep){
        if(groupBaseInfoRep.getIsParent() == null){
            groupBaseInfoRep.setIsParent(1);
        }
        if(groupBaseInfoRep.getIsParent() == 2 && groupBaseInfoRep.getParentId() == null){
            return KbengineResult.error("缺少父节点id");
        }
        // 判断是否已有重复名称分组名称
        if(groupBaseInfoRepService.havaRepeatName(groupBaseInfoRep)){
            return KbengineResult.error("存在重复名称的分组");
        }
        int isSuccess = groupBaseInfoRepService.insertGroupBaseInfoRep(groupBaseInfoRep);
        if(isSuccess > 0){
            // 判断是否需要设置父节点
            if(groupBaseInfoRep.getIsParent() == null || groupBaseInfoRep.getIsParent() == 1){
                long id = groupBaseInfoRep.getId();
                GroupBaseInfoRep groupBaseInfoRep1 = new GroupBaseInfoRep();
                groupBaseInfoRep1.setId((long) id);
                groupBaseInfoRep1.setParentId((long) id);
                groupBaseInfoRepService.updateGroupBaseInfoRep(groupBaseInfoRep1);
            }
            return KbengineResult.success("添加成功");
        } else{
            return KbengineResult.error("添加失败");
        }
    }

    @ApiOperation(value = "修改分组", notes = "修改分组", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="long"),
            @ApiImplicitParam(name = "groupName",value = "分组名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "groupType",value = "分组类型（ 1：标签 99：通用）", required = false, paramType = "query", dataType="int"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LabelBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "groupBaseInfoRep.id", msg = "缺少系统主键"),
    })
    @PostMapping("/groups/update")
    public KbengineResult update(@RequestBody @ApiIgnore GroupBaseInfoRep groupBaseInfoRep){
        // 判断是否已有重复名称分组名称
        if(groupBaseInfoRepService.havaRepeatName(groupBaseInfoRep)){
            return KbengineResult.error("存在重复名称的分组");
        }
        groupBaseInfoRepService.updateGroupBaseInfoRep(groupBaseInfoRep);
        return KbengineResult.success("修改成功");
    }

    @ApiOperation(value = "删除分组", notes = "删除分组", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "系统主键", required = true, paramType = "query", dataType="long"),
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = LabelBaseInfoRep.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @CheckParams({
            @CheckParam(value = Check.NotNull, argName = "groupBaseInfoRep.id", msg = "缺少系统主键"),
    })
    @PostMapping("/groups/delete")
    public KbengineResult delete(@RequestBody @ApiIgnore GroupBaseInfoRep groupBaseInfoRep){
        if(groupBaseInfoRep.getId() == -1){
            return KbengineResult.error("通用分组不能删除");
        }
        // 删除分组
        groupBaseInfoRepService.deleteGroupBaseInfoRepById(groupBaseInfoRep.getId());
        // 删除分组的映射关系
        labelGroupRepService.deleteLabelGroupRepByGroupId(groupBaseInfoRep.getId());
        return KbengineResult.success("删除成功");
    }
}
