package com.wzxc.zzdpush.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.zzdpush.vo.HhlZzdIndicator;
import com.wzxc.zzdpush.vo.HhlZzdKpi;

import java.util.*;

public class IndicatorUtil {

    /**
     * 批量替换通配符
     */
    public String repalceIndicators(String content, List<HhlZzdKpi> hhlZzdKpis, List<HhlZzdIndicator> hhlZzdIndicators){
        String[] keyList = StringUtils.getContentInfo(content).split(","); // 获取通配符里面的值
        for(String key : keyList){
            boolean r = false; // 是否已经替换
            boolean f = false; // 是否找到对应的kpi
            for(HhlZzdKpi hhlZzdKpiEle : hhlZzdKpis){
                if(hhlZzdKpiEle.getKpiName().equals(key)){ // 找到匹配的值
                    f = true;
                    String kpiId = hhlZzdKpiEle.getKpiId();
                    for(HhlZzdIndicator hhlZzdIndicatorEle : hhlZzdIndicators){
                        if(hhlZzdIndicatorEle.getIndicatorId().equals(kpiId)){ // 找到匹配的指标id，将通配符替换成参数
                            content = handleIndicatorValue(content, key, hhlZzdIndicatorEle);
                            r = true;
                            break;
                        }
                    }
                    if(r){
                        break;
                    }
                }
                if(f){
                    break;
                }
            }
            if(!r){
                content = handleIndicatorValue(content, key, null);
            }
        }
        return content;
    }

    /**
     * 将字符串中的通配符替换成参数
     * @param eleContent：目标自负床
     * @param key：通配符里面的key
     * @param hhlZzdIndicatorEle：参数
     * @return
     */
    public String handleIndicatorValue(String eleContent, String key, HhlZzdIndicator hhlZzdIndicatorEle) {
        String res = new StringBuilder().append("$${").append(key).append("}").toString();
        String indicatorValue = getIndicatorValue(key, hhlZzdIndicatorEle);
        return eleContent.replace(res, "$$" + indicatorValue + "##");
    }

    /**
     * 根据指标名称匹配，返回指标值
     */
    public String getIndicatorValue(String key, HhlZzdIndicator hhlZzdIndicator){
        if(hhlZzdIndicator == null || StringUtils.isEmpty(hhlZzdIndicator.getIndicatorValue())){
            String replaceStr = "暂无数据";
            if(key.equals("今日日期（例：2021年1月31日24时）")){
                replaceStr = DateUtils.dateHour(-1);
            } else if(key.equals("今日月份（例：2021年1月份）")){
                replaceStr = DateUtils.dateMon(-1);
            } else if(key.equals("今日日期（例：2021年1月31日）")){
                replaceStr = DateUtils.dateDay(-1);
            }
            return replaceStr;
        }
        if(key.contains("环比")){
            if(StringUtils.isEmpty(hhlZzdIndicator.getIndicatorValuePeriod()) || hhlZzdIndicator.getIndicatorValuePeriod().equals("0")){
                return "暂无数据";
            } else{
                double QoQ = (Double.valueOf(hhlZzdIndicator.getIndicatorValue()) - Double.valueOf(hhlZzdIndicator.getIndicatorValuePeriod())) / Double.valueOf(hhlZzdIndicator.getIndicatorValuePeriod());
                double s = Math.round(Math.abs(QoQ) * 1000) / 1000d * 100d;
                return QoQ >= 0 ? "增长" + String.format("%.1f", s) + "%" : "减少" + String.format("%.1f", s) + "%";
            }
        } else if(key.contains("差值")){
            if(StringUtils.isEmpty(hhlZzdIndicator.getIndicatorValuePeriod()) || hhlZzdIndicator.getIndicatorValuePeriod().equals("0")){
                return "暂无数据";
            } else{
                double diff = Double.valueOf(hhlZzdIndicator.getIndicatorValue()) - Double.valueOf(hhlZzdIndicator.getIndicatorValuePeriod());
                if(hhlZzdIndicator.getUnit().equals("人") || hhlZzdIndicator.getUnit().equals("人次") || hhlZzdIndicator.getUnit().equals("次")){
                    return diff >= 0 ? "新增" + Math.round(diff) + hhlZzdIndicator.getUnit() : "减少" + Math.round(diff) + hhlZzdIndicator.getUnit();
                }
                return diff >= 0 ? "新增" + diff + hhlZzdIndicator.getUnit() : "减少" + diff + hhlZzdIndicator.getUnit();
            }
        } else if(key.equals("旅馆内宾来源地前三地区与人次")){
            JSONObject jsonObject = JSONObject.parseObject(hhlZzdIndicator.getIndicatorValue());
            JSONArray jsonArray = jsonObject.getJSONObject("chartData").getJSONArray("data");
            StringBuilder s = new StringBuilder();
            for(int count = 0; count < 3; count++){
                Object o = jsonArray.get(count);
                String name = (String) JSONObject.parseObject(o.toString()).get("name");
                String value = (String) JSONObject.parseObject(o.toString()).get("value");
                s.append(name).append(" ").append(value).append(hhlZzdIndicator.getUnit()).append(",");
            }
            s.delete(s.length() - 1, s.length());
            return s.toString();
        } else if(key.equals("医保支付额前三市辖区与总额")){
            JSONObject jsonObject = JSONObject.parseObject(hhlZzdIndicator.getIndicatorValue());
            JSONArray jsonArray = jsonObject.getJSONObject("chartData").getJSONArray("data");
            Map<String, Double> map1 = new HashMap<>();
            for(Object element : jsonArray){
                String name = ((JSONObject) element).getString("name");
                JSONArray coordinates = ((JSONObject) element).getJSONArray("coordinates");
                map1.put(name, coordinates.getJSONObject(coordinates.size() - 1).getDouble("y"));
            }
            List<Map.Entry<String, Double>> list = new ArrayList<>(map1.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                //升序排序
                public int compare(Map.Entry<String, Double> o1,
                                   Map.Entry<String, Double> o2) {
                    return -o1.getValue().compareTo(o2.getValue());
                }
            });
            StringBuilder s = new StringBuilder();
            for(int i = 0; i < 3; i++){
                String name = list.get(i).getKey();
                Double value = list.get(i).getValue();
                s.append(name).append(" ").append(value).append(hhlZzdIndicator.getUnit()).append(",");
            }
            s.delete(s.length() - 1, s.length());
            return s.toString();
        }
        return hhlZzdIndicator.getIndicatorValue() + hhlZzdIndicator.getUnit();
    }
}
