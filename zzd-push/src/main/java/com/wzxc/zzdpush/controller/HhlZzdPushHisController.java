package com.wzxc.zzdpush.controller;

import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.page.TableDataInfo;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.zzdpush.service.impl.HhlZzdPushHisServiceImpl;
import com.wzxc.zzdpush.vo.HhlZzdPushHis;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/push/log")
@Slf4j
@Api(tags="浙政钉工作通知日志类")
public class HhlZzdPushHisController extends BaseController {

    @Autowired
    private HhlZzdPushHisServiceImpl hhlZzdPushHisService;

    /**
     * 获取工作通知列表
     * @param creator
     * @param type
     * @param status
     * @return
     */
    @ApiOperation(value = "查询工作通知日志列表", notes = "查询工作通知日志列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creator",value = "创建者（先用root）", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "status",value = "状态（0：有效消息 1：已经被撤销的消息）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pushDateBegin",value = "发送日期（from，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "pushDateEnd",value = "发送日期（to，yyyy-MM-dd）", required = false, paramType = "query", dataType="date"),
            @ApiImplicitParam(name = "keyword",value = "关键字", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "pageSize",value = "页码（默认10）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageNum",value = "页数（默认1）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "isAsc",value = "推送时间的排序方向（asc或者desc）", required = false, paramType = "query", dataType="string")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = HhlZzdPushHis.class)
    })
    @PostMapping("/list")
    public TableDataInfo list(@RequestParam(value = "creator") String creator,
                              @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "pushDateBegin", required = false) Date pushDateBegin,
                              @RequestParam(value = "pushDateEnd", required = false) Date pushDateEnd, @RequestParam(value = "keyword", required = false) String keyword){
        startPage();
        startOrderBy("push_time");
        HhlZzdPushHis hhlZzdPushHis = new HhlZzdPushHis();
        hhlZzdPushHis.setCreator(creator);
        hhlZzdPushHis.setStatus(status);
        hhlZzdPushHis.setPushDateBegin(pushDateBegin);
        hhlZzdPushHis.setPushDateEnd(pushDateEnd);
        if(!StringUtils.isEmpty(keyword)){
            hhlZzdPushHis.setKeyword(keyword.replace(" ", ""));
        }
        List<HhlZzdPushHis> hhlZzdPushHisList = hhlZzdPushHisService.selectHhlZzdPushHisList(hhlZzdPushHis);
        return getDataTable(hhlZzdPushHisList);
    }

}
