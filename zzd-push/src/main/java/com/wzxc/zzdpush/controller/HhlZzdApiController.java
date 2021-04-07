package com.wzxc.zzdpush.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.AjaxResult;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.zzdpush.annotation.ChangeLog;
import com.wzxc.zzdpush.annotation.PushLog;
import com.wzxc.zzdpush.annotation.RevokeLog;
import com.wzxc.zzdpush.service.impl.HhlZzdPushHisServiceImpl;
import com.wzxc.zzdpush.utils.JodaUtil;
import com.wzxc.zzdpush.vo.HhlZzdPushHis;
import com.wzxc.zzdpush.vo.TemplateContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/zzdApi")
@Slf4j
@Api(tags="浙政钉Api封装类")
public class HhlZzdApiController extends BaseController {

    @Autowired
    private HhlZzdPushHisServiceImpl hhlZzdPushHisService;

    /**
     * 发送工作通知 - oa
     */
    @PushLog
    @RequestMapping(value = "/send")
    @ApiOperation(value = "工作通知发送", notes = "工作通知发送", httpMethod = "POST")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "type",value = "消息类型（默认oa）", required = false, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "head",value = "消息头部信息", required = true, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "head.text",value = "消息头部标题", required = true, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "head.bgcolor",value = "抬头字体颜色", required = false, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "body",value = "消息体", required = true, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "body.title",value = "消息体标题", required = false, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "body.form.key",value = "消息体内容（键值）", required = true, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "body.form.value",value = "消息体内容（值）", required = true, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "body.author",value = "作者", required = false, paramType = "query", dataType="TemplateContent"),
//            @ApiImplicitParam(name = "emcodes",value = "接收者emcodes，多个用半角逗号隔开", required = true, paramType = "query", dataType="TemplateContent"),
    })
    public AjaxResult sendTemplateOA(@Valid @RequestBody TemplateContent templateContent){
        // 根据员工code获取accoundId
        String[] encodeList = templateContent.getEmcodes().split(",");
        JSONObject accountsObject = JSONObject.parseObject(JodaUtil.queryListEmployeeAccountIds(encodeList));
        if(!(boolean) accountsObject.get("success")){
            return AjaxResult.error("发送失败");
        }
        JSONArray accountArray = accountsObject.getJSONObject("content").getJSONArray("data");
        List<String> accountList = new ArrayList<>();
        for(int count = 0; count < accountArray.size(); count++){
            accountList.add(accountArray.getJSONObject(count).get("accountId").toString());
        }
        String receiverIds = String.join(",", accountList);
        JSONObject msg = new JSONObject();
        msg.put("msgtype", templateContent.getType());
        JSONObject contentObject = new JSONObject();
        contentObject.put("head", templateContent.getHead());
        contentObject.put("body", templateContent.getBody());
        msg.put(templateContent.getType(), contentObject);
        String content = msg.toJSONString();
        JSONObject sendM = JSONObject.parseObject(JodaUtil.sendWorkNotification(content, receiverIds));
        if(!(boolean) sendM.get("success")){
            return AjaxResult.error(sendM.getString("errorMsg"), sendM);
        }
        return AjaxResult.success("发送成功", sendM);
    }

    /**
     * 获取组织下一层级的组织详情和员工详情
     */
    @RequestMapping(value = "/org/sub")
    @ApiOperation(value = "获取下一层的组织详情和员工详情", notes = "获取下一层的组织详情和员工详情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationCode",value = "组织code（第一层用root）", required = true, paramType = "query", dataType="string"),
    })
    public AjaxResult queryOrgSonList(@RequestParam(value = "organizationCode") String organizationCode){
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        if(organizationCode.equals("root")){
            jsonObject = com.alibaba.fastjson.JSONObject.parseObject(JodaUtil.queryAuthScopesV2());
            // 判断是否调用成功
            Object o = jsonObject.get("success");
            if((Boolean) jsonObject.get("success")){
                try{
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.getJSONObject("content").get("deptVisibleScopes").toString());
                    String result = JodaUtil.queryListOrganizationsByCodes(jsonArray);
                    resultMap.put("org", JSONObject.parseObject(result).getJSONObject("content").getJSONArray("data"));
                    return AjaxResult.success(resultMap);
                } catch(Exception e){
                    return error("发生异常");
                }
            }

        } else{
            jsonObject = com.alibaba.fastjson.JSONObject.parseObject(JodaUtil.querySubOrganizationCodes(organizationCode));
            // 判断是否调用成功
            if((Boolean) jsonObject.get("success") && jsonObject.getJSONObject("content").get("data") != null){
                try{
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.getJSONObject("content").get("data").toString());
                    String result = JodaUtil.queryListOrganizationsByCodes(jsonArray);
                    resultMap.put("org", JSONObject.parseObject(result).getJSONObject("content").getJSONArray("data"));
                } catch(Exception e){
                    log.error("发生异常", e);
                    return error("发生异常");
                }
            }
            // 获取同级的人员信息列表
            try{
                JSONArray jsonArrayEmc = new JSONArray();
                JSONArray jsonArrayEmm = new JSONArray();
                String pageNo = "0";
                int total = 0;
                // 判断是否超过100条，超过的话需要多次查询
                do{
                    pageNo = String.valueOf(Integer.valueOf(pageNo) + 1);
                    jsonObject = JSONObject.parseObject(JodaUtil.queryPageOrganizationEmployeeCodes(organizationCode, pageNo));
                    if((Boolean) jsonObject.get("success") && jsonObject.getJSONObject("content").get("data") != null){
                        total = Integer.valueOf(jsonObject.getJSONObject("content").getString("totalSize"));
                        // 通过人员code列表 获取人员详情列表
                        String result = JodaUtil.queryListEmployeeByCodes(jsonObject.getJSONObject("content").getJSONArray("data"));
                        jsonArrayEmm.addAll(JSONObject.parseObject(result).getJSONObject("content").getJSONArray("data"));
                    }

                } while(total - 100 * Integer.valueOf(pageNo) > 0);
                resultMap.put("em", jsonArrayEmm);
                return AjaxResult.success(resultMap);

            } catch(Exception e){
                log.error("发生异常", e);
                return error("发生异常");
            }
        }
        return error("操作失败");
    }

    /**
     * 通过关键字查询组织下面所有人员信息列表
     */
    @PostMapping("/em/keyword")
    @ApiOperation(value = "通过关键字查询组织下面人员信息列表（前100名）", notes = "通过关键字查询组织下面人员信息列表（前100名）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名字", required = true, paramType = "query", dataType="string"),
    })
    public AjaxResult queryEmMessageByKeyword(@RequestParam(value = "name") String name){
        try{
            Map<String, Object> resultMap = new HashMap<>();
            // 查询根目录下面的所有组织
            JSONArray orgList = JSONObject.parseObject(JodaUtil.queryAuthScopesV2()).getJSONObject("content").getJSONArray("deptVisibleScopes");
            Set<String> orgCodeSet = new HashSet<>(); // 用来存放组织code
            JSONArray emCodeList = new JSONArray();
            JSONArray emMessageList = new JSONArray(); // 人员信息列表
            for(Object organizationCode : orgList.toArray()){
                JSONObject r1 = JSONObject.parseObject(JodaUtil.pageSearchEmployee(organizationCode.toString(), name, "1"));
                JSONArray emList = r1.getJSONObject("content").getJSONArray("data");
                if(emList != null){
                    // 查询每个人员对应的组织code
                    for(int index = 0; index < emList.size(); index++){
                        JSONObject r2 = JSONObject.parseObject(JodaUtil.listEmployeePositionsByEmployeeCode(emList.getJSONObject(index).getString("employeeCode"), null));
                        JSONArray posList = r2.getJSONObject("content").getJSONArray("data");
                        if(posList != null){ // 添加组织code
                            for(int i = 0; i < posList.size(); i++){
                                orgCodeSet.add(posList.getJSONObject(i).getString("organizationCode"));
                            }
                        }
                        emList.getJSONObject(index).put("posList", posList == null ? new ArrayList<>() : posList);
                    }
                    JodaUtil.listOrgEmployeePositionsByCodes(organizationCode.toString(), emCodeList);
                    emMessageList.addAll(emList);
                }
                // 根据组织code列表查询组织详细信息
                if(orgCodeSet.size() > 0){
                    JSONArray orgMessageList = new JSONArray();
                    // 单个遍历
                    for(String orgCode : orgCodeSet){
                        JSONArray ja = new JSONArray();
                        ja.add(orgCode);
                        JSONObject r3 = JSONObject.parseObject(JodaUtil.queryListOrganizationsByCodes(ja));
                        if(r3.getBoolean("success")){
                            orgMessageList.addAll(r3.getJSONObject("content").getJSONArray("data"));
                        }
                    }

//                    JSONArray orgMessageList = r3.getJSONObject("content").getJSONArray("data");
                    Map<String, Object> orgMessageMap = new HashMap<>();
                    for(int j = 0; j < orgMessageList.size(); j++){
                        orgMessageMap.put(orgMessageList.getJSONObject(j).getString("organizationCode"), orgMessageList.getJSONObject(j));
                    }
                    Iterator<Object> iterator = emMessageList.iterator();
                    while(iterator.hasNext()){
                        JSONObject emMessage = (JSONObject) iterator.next();
                        JSONArray posList = emMessage.getJSONArray("posList");
                        String orgCode = posList.getJSONObject(0).getString("organizationCode");
                        if(orgMessageMap.get(orgCode) == null){
                            iterator.remove();
                        } else{
                            posList.getJSONObject(0).put("organizationDetail", orgMessageMap.get(orgCode));
                        }
                    }
//                    for(int n = 0; n < emMessageList.size(); n++){
//                        JSONArray posList = emMessageList.getJSONObject(n).getJSONArray("posList");
//                        for(int m = 0; m < posList.size(); m++){
//                            String orgCode = posList.getJSONObject(m).getString("organizationCode");
//                            posList.getJSONObject(m).put("organizationDetail", orgMessageMap.get(orgCode));
//                        }
//                    }
                    orgCodeSet.clear();
                }
            }
            resultMap.put("emMessage", emMessageList);
            return AjaxResult.success(resultMap);

        } catch (Exception e){
            log.error("发生异常", e);
            return error("查询失败");
        }
    }

    /**
     * 工作通知撤回
     */
    @RevokeLog
    @PostMapping("/revoke")
    @ApiOperation(value = "工作通知撤回", notes = "工作通知撤回", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizMsgId",value = "消息id", required = true, paramType = "query", dataType="string"),
    })
    public AjaxResult revokeWorkNotification(@RequestParam(value = "bizMsgId") String bizMsgId){
        JSONObject j = JSONObject.parseObject(JodaUtil.revokeWorkNotification(bizMsgId));
        if(!(boolean) j.get("success")){
            return AjaxResult.error("撤销失败", j);
        }
        return AjaxResult.success("撤销成功", j);
    }

    /**
     * 工作通知修改
     */
    @ChangeLog
    @PostMapping("/change")
    @ApiOperation(value = "工作通知修改", notes = "工作通知修改", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "消息类型（默认oa）", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "head",value = "消息头部信息", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "head.text",value = "消息头部标题", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "head.bgcolor",value = "抬头字体颜色", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "body",value = "消息体", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "body.title",value = "消息体标题", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "body.form.key",value = "消息体内容（键值）", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "body.form.value",value = "消息体内容（值）", required = true, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "body.author",value = "作者", required = false, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "bizMsgId",value = "消息id", required = true, paramType = "query", dataType="string"),
    })
    public AjaxResult changeWorkNotification(@RequestBody @ApiIgnore TemplateContent templateContent){
        if(StringUtils.isEmpty(templateContent.getBizMsgId())){
            return error("缺少必要参数");
        }
        // 根据messageId查询日志
        HhlZzdPushHis queryHis = hhlZzdPushHisService.selectHhlZzdPushHisByMessageId(templateContent.getBizMsgId());
        // 根据员工code获取accountId
        JSONArray encodes = JSONArray.parseArray(queryHis.getCopyFor());
        String[] encodeList = new String[encodes.size()];
        for(int index = 0; index < encodes.size(); index++){
            encodeList[index] = encodes.getJSONObject(index).getString("employeeCode");
        }
        JSONObject accountsObject = JSONObject.parseObject(JodaUtil.queryListEmployeeAccountIds(encodeList));
        if(!(boolean) accountsObject.get("success")){
            return AjaxResult.error("修改失败");
        }
        JSONArray accountArray = accountsObject.getJSONObject("content").getJSONArray("data");
        List<String> accountList = new ArrayList<>();
        for(int count = 0; count < accountArray.size(); count++){
            accountList.add(accountArray.getJSONObject(count).get("accountId").toString());
        }
        // 信息内容
        JSONObject msg = new JSONObject();
        msg.put("msgtype", templateContent.getType());
        JSONObject contentObject = new JSONObject();
        contentObject.put("head", templateContent.getHead());
        contentObject.put("body", templateContent.getBody());
        msg.put(templateContent.getType(), contentObject);
        // 发送信息
        JSONObject j = JSONObject.parseObject(JodaUtil.changeWorkNotification(templateContent.getType(), msg.toJSONString(), templateContent.getBizMsgId(), accountList.toArray(new String[accountList.size()])));
        if(!(boolean) j.get("success")){
            return AjaxResult.error("修改失败", j);
        }
        return AjaxResult.success("修改成功", j);
    }
}
