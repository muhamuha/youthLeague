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
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class PushLogAspect {

    @Autowired
    private HhlZzdPushHisServiceImpl hhlZzdPushHisService;

    @Autowired
    private HhlZzdPushHisProcessServiceImpl hhlZzdPushHisProcessService;

    @Pointcut("@annotation(com.wzxc.zzdpush.annotation.PushLog)")
    public void pushLog() {
    }

    @AfterReturning(returning = "rt", pointcut = "pushLog()")
    public AjaxResult after(JoinPoint joinPoint, AjaxResult rt) throws Throwable {
        if((Integer) (rt.get("code")) == AjaxResult.Type.SUCCESS.value()){
            HhlZzdPushHis hhlZzdPushLog = new HhlZzdPushHis();
            HhlZzdPushHisProcess hhlZzdPushHisProcess = new HhlZzdPushHisProcess();

            String uuid = com.wzxc.common.utils.uuid.UUID.randomUUID(true).toString();
            hhlZzdPushLog.setId(uuid);
            hhlZzdPushLog.setPushTime(new Date());
            hhlZzdPushLog.setCreateTime(new Date());
            // 推送内容
            TemplateContent templateContent = (TemplateContent)(joinPoint.getArgs()[0]);
            hhlZzdPushLog.setContent(JSON.toJSONString(templateContent));
            // 获取抄送者的详细信息列表
            String[] emcodes = templateContent.getEmcodes().split(",");
            JSONArray ja1 = JSONObject.parseObject(JodaUtil.queryListEmployeeByCodes(JSONArray.parseArray(JSON.toJSONString(emcodes)))).getJSONObject("content").getJSONArray("data");
            hhlZzdPushLog.setCopyFor(ja1.toJSONString());
            // 浙政钉返回内容
            JSONObject pushReturn = (JSONObject) rt.get("data");
            // 判断是否发送成功
            if(!(boolean) pushReturn.get("success")){
                hhlZzdPushHisProcess.setStatus(1);
            } else{
                hhlZzdPushHisProcess.setStatus(0);
                hhlZzdPushLog.setStatus(0);
                // 获取messageId
                String messageId = pushReturn.getJSONObject("content").getString("msgId");
                hhlZzdPushLog.setMessageId(messageId);
                // 创建者
                hhlZzdPushLog.setCreator("root");
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
                hhlZzdPushLog.setContentSimple(contentSimple.toString());
                // 插入状态日志
                hhlZzdPushHisService.insertHhlZzdPushHis(hhlZzdPushLog);
            }
            // 插入过程日志
            hhlZzdPushHisProcess.setPushId(uuid);
            hhlZzdPushHisProcess.setType(0);
            hhlZzdPushHisProcess.setPushReturn(JSON.toJSONString(pushReturn));
            hhlZzdPushHisProcess.setContent(JSON.toJSONString(templateContent));
            hhlZzdPushHisProcessService.insertHhlZzdPushHisProcess(hhlZzdPushHisProcess);
        }
        return rt;
    }

}
