package com.wzxc.kbengine.en.policyBaseInfoRep;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.constant.ValidateConstants;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.validate.FilterUtil;

public class FileObjectList {

    public String handle(Object value) {
        String valueStr = value.toString();
        if(StringUtils.isEmpty(value.toString())){
            return valueStr;
        }
        JSONArray fileObjectList = new JSONArray();
        try{
            String[] fileList = valueStr.split(",");
            for(String file : fileList){
                JSONObject fileObject = new JSONObject();
                String fileName = file.split("\\$")[0];
                String fileUrl = file.split("\\$")[1];
                fileObject.put("fileName", fileName);
                fileObject.put("fileUrl", fileUrl);
                fileObjectList.add(fileObject);
            }
            return fileObjectList.toJSONString();
        } catch (Exception e){
            return ValidateConstants.FILTER_FAIL;
        }
    }
}
