package com.wzxc.zzdpush.aspect;

import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.core.domain.AjaxResult;
import com.wzxc.zzdpush.service.impl.HhlZzdPushHisProcessServiceImpl;
import com.wzxc.zzdpush.service.impl.HhlZzdPushHisServiceImpl;
import com.wzxc.zzdpush.vo.HhlZzdPushHis;
import com.wzxc.zzdpush.vo.HhlZzdPushHisProcess;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class RevokeLogAspect {

    @Autowired
    private HhlZzdPushHisServiceImpl hhlZzdPushHisService;

    @Autowired
    private HhlZzdPushHisProcessServiceImpl hhlZzdPushHisProcessService;

    @Pointcut("@annotation(com.wzxc.zzdpush.annotation.RevokeLog)")
    public void revokeLog() {
    }

    @AfterReturning(returning = "rt", pointcut = "revokeLog()")
    public AjaxResult after(JoinPoint joinPoint, AjaxResult rt) throws Throwable {
        if((Integer) (rt.get("code")) == AjaxResult.Type.SUCCESS.value()){
            String messageId = (String) (joinPoint.getArgs()[0]);
            HhlZzdPushHis hhlZzdPushhis = new HhlZzdPushHis();
            HhlZzdPushHisProcess hhlZzdPushHisProcess = new HhlZzdPushHisProcess();
            // 根据messageId查询日志
            HhlZzdPushHis queryHis = hhlZzdPushHisService.selectHhlZzdPushHisByMessageId(messageId);
            if(((JSONObject) rt.get("data")).getBoolean("success")){
                hhlZzdPushHisProcess.setStatus(0);
                // 修改日志状态
                hhlZzdPushhis.setStatus(1);
                hhlZzdPushhis.setId(queryHis.getId());
                hhlZzdPushHisService.updateHhlZzdPushHis(hhlZzdPushhis);
            } else{
                hhlZzdPushHisProcess.setStatus(1);
            }
            // 插入日志过程记录
            hhlZzdPushHisProcess.setPushId(queryHis.getId());
            hhlZzdPushHisProcess.setType(2);
            hhlZzdPushHisProcess.setPushReturn(((JSONObject) rt.get("data")).toJSONString());
            hhlZzdPushHisProcessService.insertHhlZzdPushHisProcess(hhlZzdPushHisProcess);
        }
        return rt;
    }
}
