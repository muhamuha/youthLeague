package com.wzxc.zzdpush.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.AjaxResult;
import com.wzxc.common.core.page.TableDataInfo;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.MapDataUtil;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.zzdpush.service.IHhlZzdIndicatorService;
import com.wzxc.zzdpush.service.IHhlZzdKpiService;
import com.wzxc.zzdpush.service.IHhlZzdPushTemplateService;
import com.wzxc.zzdpush.utils.IndicatorUtil;
import com.wzxc.zzdpush.vo.HhlZzdIndicator;
import com.wzxc.zzdpush.vo.HhlZzdKpi;
import com.wzxc.zzdpush.vo.HhlZzdPushTemplate;
import com.wzxc.zzdpush.vo.HhlZzdPushTemplateParent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.Hlookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Controller
 * 
 * @author muhamuha
 * @date 2021-02-02
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/template")
@Api(tags="浙政钉模板消息类")
public class HhlZzdPushTemplateController extends BaseController {

    @Autowired
    private IHhlZzdPushTemplateService hhlZzdPushTemplateService;

    @Autowired
    private IHhlZzdKpiService hhlZzdKpiService;

    @Autowired
    private IHhlZzdIndicatorService hhlZzdIndicatorService;

    private IndicatorUtil indicatorUtil = new IndicatorUtil();

    /**
     * 查询【请填写功能名称】列表
     */
    @ApiOperation(value = "查询模板（不传默认所有）", notes = "查询模板（不传默认所有）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "指标主键", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "templateName",value = "指标名称", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "templateId",value = "指标id", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "parentId",value = "大类id", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "content",value = "内容", required = false, paramType = "query", dataType="String")
    })
    @PostMapping("/list")
    public TableDataInfo list(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "templateName", required = false) String templateName,
                              @RequestParam(value = "content", required = false) String content,
                              @RequestParam(value = "parentId", required = false) Integer parentId) throws IllegalAccessException {
        HhlZzdPushTemplate hhlZzdPushTemplate = new HhlZzdPushTemplate();
        hhlZzdPushTemplate.setId(id);
        hhlZzdPushTemplate.setTemplateName(templateName);
        hhlZzdPushTemplate.setParentId(parentId);
        hhlZzdPushTemplate.setContent(content);
        List<Map<String, Object>> list = hhlZzdPushTemplateService.selectHhlZzdPushTemplateList(hhlZzdPushTemplate);
        // 获取指标详情列表
        HhlZzdKpi hhlZzdKpi = new HhlZzdKpi();
        hhlZzdKpi.setStatus(1);
        List<HhlZzdKpi> hhlZzdKpis = hhlZzdKpiService.selectHhlZzdKpiList(hhlZzdKpi);
        // 获取指标参数列表（需要调用城市大脑数据源）
        HhlZzdIndicator hhlZzdIndicator = new HhlZzdIndicator();
        List<HhlZzdIndicator> hhlZzdIndicators = hhlZzdIndicatorService.selectHhlZzdIndicatorList(hhlZzdIndicator);
        // 将模板内的通配符替换成具体参数
        for(Map<String, Object> parentEle : list){
            String contentP = (String) parentEle.get("contentTemplate");
            parentEle.put("content", indicatorUtil.repalceIndicators(contentP, hhlZzdKpis, hhlZzdIndicators));
            List<HhlZzdPushTemplate> sonList = (List<HhlZzdPushTemplate>)parentEle.get("sonList");
            for(HhlZzdPushTemplate listEle : sonList){
                if(listEle.getIsKpi() == 0){ // 不是指标，跳过
                    continue;
                }
                String eleContent = (String) listEle.getContentTemplate();
                listEle.setContent(indicatorUtil.repalceIndicators(eleContent, hhlZzdKpis, hhlZzdIndicators));
            }
        }
        return getDataTable(list);
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @ApiOperation(value = "添加模板", notes = "添加模板", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateName",value = "指标名称", required = true, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "parentId",value = "大类id", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "content",value = "内容", required = true, paramType = "query", dataType="String")
    })
    @PostMapping("/add")
    public AjaxResult addSave(@RequestParam(value = "templateName") String templateName, @RequestParam(value = "parentId") Integer parentId,
                              @RequestParam(value = "content") String content)
    {
        HhlZzdPushTemplate hhlZzdPushTemplate = new HhlZzdPushTemplate();
        hhlZzdPushTemplate.setTemplateName(templateName);
        hhlZzdPushTemplate.setParentId(parentId);
        hhlZzdPushTemplate.setContent(content);
        return toAjax(hhlZzdPushTemplateService.insertHhlZzdPushTemplate(hhlZzdPushTemplate));
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改模板", notes = "修改模板", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "指标id", required = true, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "templateName",value = "指标名称", required = false, paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "parentId",value = "大类id", required = false, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "content",value = "内容", required = false, paramType = "query", dataType="String")
    })
    public AjaxResult editSave(@RequestParam(value = "id") Integer id, @RequestParam(value = "templateName", required = false) String templateName,
                               @RequestParam(value = "parentId", required = false) Integer parentId, @RequestParam(value = "content", required = false) String content)
    {
        HhlZzdPushTemplate hhlZzdPushTemplate = new HhlZzdPushTemplate();
        hhlZzdPushTemplate.setId(id);
        hhlZzdPushTemplate.setTemplateName(templateName);
        hhlZzdPushTemplate.setParentId(parentId);
        hhlZzdPushTemplate.setContent(content);
        return toAjax(hhlZzdPushTemplateService.updateHhlZzdPushTemplate(hhlZzdPushTemplate));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PostMapping( "/remove")
    @ApiOperation(value = "删除模板", notes = "删除模板", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "指标id（多个指标用逗号隔开）", required = true, paramType = "query", dataType="String"),
    })
    public AjaxResult remove(@RequestParam(value = "ids") String ids)
    {
        return toAjax(hhlZzdPushTemplateService.deleteHhlZzdPushTemplateByIds(ids));
    }
}
