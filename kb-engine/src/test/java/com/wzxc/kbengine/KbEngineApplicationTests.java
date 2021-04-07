package com.wzxc.kbengine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.kbengine.service.impl.PolicyBaseInfoRepServiceImpl;
import com.wzxc.kbengine.service.impl.PolicyFileRepServiceImpl;
import com.wzxc.kbengine.vo.PolicyBaseInfoRep;
import com.wzxc.kbengine.vo.PolicyFileRep;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.security.provider.PolicyFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class KbEngineApplicationTests {

    @Autowired
    private PolicyBaseInfoRepServiceImpl policyBaseInfoRepService;

    @Autowired
    private PolicyFileRepServiceImpl policyFileRepService;

    private int pageNum = 1;
    private int pageSize = 100;
    private long total = 0;

    void fun() throws IOException {
        // 获取所有oa导入的政策文件，且没有同步fileObjectList字段
        PageHelper.startPage(pageNum, pageSize);
        List<PolicyBaseInfoRep> policyBaseInfoReps = policyBaseInfoRepService.selectSynFileFromOa();
        // 判断是否有fileOutId
        boolean haveFileOutId = false;
        for(PolicyBaseInfoRep policy : policyBaseInfoReps){
            if(!StringUtils.isEmpty(policy.getFileOutId())){
                haveFileOutId = true;
                String[] fileOutIds = policy.getFileOutId().split(",");
                // 拼接fileObjectList
                JSONArray fileObjectArray = new JSONArray();
                for(String fileOutId : fileOutIds){
                    if(StringUtils.isEmpty(fileOutId)){
                        continue;
                    }
                    JSONObject o = new JSONObject();
                    String path = fileUrl + fileOutId;
                    String fileName = queryFileNameFromOa(fileOutId);
                    if(StringUtils.isEmpty(fileName)){
                        continue;
                    }
                    o.put("fileName", fileName);
                    o.put("fileUrl", path);
                    fileObjectArray.add(o);
                }
                policy.setFileObjectStr(fileObjectArray.toJSONString());
            }
        }
        // 批量修改附件信息
        if(haveFileOutId){
            policyBaseInfoRepService.updateBatchFileObjectList(policyBaseInfoReps);
        }
        PageInfo<PolicyBaseInfoRep> pageInfos = new PageInfo<>(policyBaseInfoReps);
        total = pageInfos.getTotal();
        if(total > pageNum * pageSize){
            pageNum++;
            fun();
        }
        log.info("完成同步");
    }

    /**
     * 通过oa系统的文件外键获取文件名称
     */
    private final String fileUrl = "http://10.36.198.3/weaver/weaver.file.FileDownloadForBM?uuid=123&fileid=";
    public String queryFileNameFromOa(String id) throws IOException {
        String fileName = "";
        URL url = new URL(fileUrl + id);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
        List<String> stringList = headerFields.get("Content-Disposition");
        if(stringList != null && stringList.size() > 0){
            String disposition = URLDecoder.decode(stringList.get(0));
            try{
                fileName = disposition.split("filename=")[1];
            } catch (Exception e){
                log.error("发生异常啦啦啦 --- " + id);
                fileName = "";
            }

        }
        return fileName;
    }

    void fun1(){
        // 获取所有oa导入的政策文件，且没有同步fileObjectList字段
        PageHelper.startPage(pageNum, pageSize);
        List<PolicyBaseInfoRep> policyBaseInfoReps = policyBaseInfoRepService.selectSynPolicyContentFromOa();
        // 判断是否有fileOutId
        boolean haveContent = false;
        for(PolicyBaseInfoRep policy : policyBaseInfoReps){
            if(!StringUtils.isEmpty(policy.getPolicyContent())){
                haveContent = true;
                String policyContent = policy.getPolicyContent();
                String policyContentSi = Jsoup.parse(policyContent).text();
                policy.setPolicyContentSi(policyContentSi);
                policyBaseInfoRepService.updatePolicyContentSi(policy);
            }
        }
        // 批量修改附件信息
//        if(haveContent){
//            policyBaseInfoRepService.updateBatchPolicyContentList(policyBaseInfoReps);
//        }
        PageInfo<PolicyBaseInfoRep> pageInfos = new PageInfo<>(policyBaseInfoReps);
        total = pageInfos.getTotal();
        if(total > pageNum * pageSize){
            pageNum++;
            fun1();
        }
        log.info("完成同步");
    }

    void fun2(){
        // 获取所有oa导入的政策文件，且没有同步fileObjectList字段
        PageHelper.startPage(pageNum, pageSize);
        // 查询需要同步政策附件信息的政策列表
        List<PolicyBaseInfoRep> policyBaseInfoReps = policyBaseInfoRepService.selectSynPolicyFileFromOa();
        List<PolicyFileRep> policyFileRepList = new ArrayList<>();
        for(PolicyBaseInfoRep policy : policyBaseInfoReps){
            if(!StringUtils.isEmpty(policy.getFileObjectStr())){
                JSONArray jsonArray = JSONArray.parseArray(policy.getFileObjectStr());
                for(Object a : jsonArray){
                    PolicyFileRep policyFileRep = new PolicyFileRep();
                    Long fileid = Long.valueOf(((JSONObject) a).getString("fileUrl").split("fileid=")[1]);
                    policyFileRep.setFileOutId(fileid);
                    policyFileRep.setFileName(((JSONObject) a).getString("fileName"));
                    policyFileRep.setFileUrl(((JSONObject) a).getString("fileUrl"));
                    policyFileRepList.add(policyFileRep);
                }
            }
        }
        policyFileRepService.insertBatch(policyFileRepList);
        PageInfo<PolicyBaseInfoRep> pageInfos = new PageInfo<>(policyBaseInfoReps);
        total = pageInfos.getTotal();
        if(total > pageNum * pageSize){
            pageNum++;
            fun2();
        }
        log.info("完成同步");
    }

}
