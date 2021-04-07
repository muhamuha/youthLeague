package com.wzxc.zzdpush.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.core.domain.AjaxResult;
import com.wzxc.zzdpush.service.impl.HhlZzdPushHisProcessServiceImpl;
import com.wzxc.zzdpush.service.impl.HhlZzdPushHisServiceImpl;
import com.wzxc.zzdpush.utils.JodaUtil;
import com.wzxc.zzdpush.vo.HhlZzdPushHis;
import com.wzxc.zzdpush.vo.HhlZzdPushHisProcess;
import com.wzxc.zzdpush.vo.TemplateContent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
@Slf4j
public class ChangeLogAspect {

    @Autowired
    private HhlZzdPushHisServiceImpl hhlZzdPushHisService;

    @Autowired
    private HhlZzdPushHisProcessServiceImpl hhlZzdPushHisProcessService;

    @Pointcut("@annotation(com.wzxc.zzdpush.annotation.ChangeLog)")
    public void changeLog() {
    }

    @AfterReturning(returning = "rt", pointcut = "changeLog()")
    public AjaxResult after(JoinPoint joinPoint, AjaxResult rt) throws Throwable {
        if((Integer) (rt.get("code")) == AjaxResult.Type.SUCCESS.value()){
            TemplateContent templateContent = (TemplateContent)(joinPoint.getArgs()[0]);
            HhlZzdPushHis hhlZzdPushhis = new HhlZzdPushHis();
            HhlZzdPushHisProcess hhlZzdPushHisProcess = new HhlZzdPushHisProcess();
            // 根据messageId查询日志
            HhlZzdPushHis queryHis = hhlZzdPushHisService.selectHhlZzdPushHisByMessageId(templateContent.getBizMsgId());
            if(((JSONObject) rt.get("data")).getBoolean("success")){
                hhlZzdPushHisProcess.setStatus(0);
                // 修改日志状态
                hhlZzdPushhis.setId(queryHis.getId());
                hhlZzdPushhis.setContent(JSON.toJSONString(templateContent));
                // 获取抄送者的详细信息列表
                JSONArray encodes = JSONArray.parseArray(queryHis.getCopyFor());
                String[] encodeList = new String[encodes.size()];
                for(int index = 0; index < encodes.size(); index++){
                    encodeList[index] = encodes.getJSONObject(index).getString("employeeCode");
                }
                JSONArray ja1 = JSONObject.parseObject(JodaUtil.queryListEmployeeByCodes(JSONArray.parseArray(JSON.toJSONString(encodeList)))).getJSONObject("content").getJSONArray("data");
                hhlZzdPushhis.setCopyFor(ja1.toJSONString());
                // 拼接重要信息（内容 + 抄送人）
                StringBuilder contentSimple = new StringBuilder();
                contentSimple.append(templateContent.getHead().getText())
                        .append(" ")
                        .append(templateContent.getBody().getTitle())
                        .append(" ")
                        .append(templateContent.getBody().getAuthor())
                        .append(" ");
                for(Map<String, String> entry : templateContent.getBody().getForm()){
                    contentSimple.append(entry.get("key")).append(" ").append(entry.get("value")).append(" ");
                }
                for(Object o : ja1){
                    contentSimple.append(((JSONObject) o).getString("employeeName")).append(" ");
                }
                hhlZzdPushhis.setContentSimple(contentSimple.toString());
                hhlZzdPushHisService.updateHhlZzdPushHis(hhlZzdPushhis);
            } else{
                hhlZzdPushHisProcess.setStatus(1);
            }
            // 插入日志过程记录
            hhlZzdPushHisProcess.setPushId(queryHis.getId());
            hhlZzdPushHisProcess.setType(1);
            hhlZzdPushHisProcess.setContent(JSON.toJSONString(templateContent));
            hhlZzdPushHisProcess.setPushReturn(((JSONObject) rt.get("data")).toJSONString());
            hhlZzdPushHisProcessService.insertHhlZzdPushHisProcess(hhlZzdPushHisProcess);
        }
        return rt;
    }

}
