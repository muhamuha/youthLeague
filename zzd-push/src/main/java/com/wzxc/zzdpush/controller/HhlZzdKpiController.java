package com.wzxc.zzdpush.controller;

import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.page.TableDataInfo;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.zzdpush.service.IHhlZzdIndicatorService;
import com.wzxc.zzdpush.service.impl.HhlZzdKpiServiceImpl;
import com.wzxc.zzdpush.utils.IndicatorUtil;
import com.wzxc.zzdpush.vo.HhlZzdIndicator;
import com.wzxc.zzdpush.vo.HhlZzdKpi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/zzdKpi")
@Api(tags="浙政钉模板推送指标字典")
public class HhlZzdKpiController extends BaseController {

    @Autowired
    private HhlZzdKpiServiceImpl hhlZzdKpiService;

    @Autowired
    private IHhlZzdIndicatorService hhlZzdIndicatorService;

    private IndicatorUtil indicatorUtil = new IndicatorUtil();

    @PostMapping("/list")
    @ApiOperation(value = "获取指标字典列表", notes = "获取指标字典列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kpiName",value = "指标名称", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "status",value = "状态（0：无效 1：有效）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageSize",value = "页码（默认10）", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "pageNum",value = "页数（默认1）", required = false, paramType = "query", dataType="int")
    })
    public TableDataInfo selectHhlZzdKpiList(@RequestParam(value = "kpiName", required = false) String kpiName, @RequestParam(value = "status", required = false) Integer status){
        startPage();
        HhlZzdKpi hhlZzdKpi = new HhlZzdKpi();
        hhlZzdKpi.setKpiName(kpiName);
        hhlZzdKpi.setStatus(status);
        List<HhlZzdKpi> hhlZzdKpis = hhlZzdKpiService.selectHhlZzdKpiList(hhlZzdKpi);
        // 获取指标参数列表（需要调用城市大脑数据源）
        HhlZzdIndicator hhlZzdIndicator = new HhlZzdIndicator();
        List<HhlZzdIndicator> hhlZzdIndicators = hhlZzdIndicatorService.selectHhlZzdIndicatorList(hhlZzdIndicator);
        // 通过指标名称进行匹配
        for(HhlZzdKpi elementKpi : hhlZzdKpis){
            if(elementKpi.getKpiId() == null || StringUtils.isEmpty(elementKpi.getKpiId())){
                String indicatorValue = indicatorUtil.getIndicatorValue(elementKpi.getKpiName(), null);
                elementKpi.setKpiValue(indicatorValue);
            } else {
                for(HhlZzdIndicator elementIndicator : hhlZzdIndicators){
                    if(elementKpi.getKpiId().equals(elementIndicator.getIndicatorId())){
                        String indicatorValue = indicatorUtil.getIndicatorValue(elementKpi.getKpiName(), elementIndicator);
                        elementKpi.setKpiValue(indicatorValue);
                        break;
                    }
                }
            }
        }
        return getDataTable(hhlZzdKpis);
    }
}
