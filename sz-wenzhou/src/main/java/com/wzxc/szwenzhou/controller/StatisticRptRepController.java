package com.wzxc.szwenzhou.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzxc.common.core.controller.BaseController;
import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.StringUtils;
import com.wzxc.common.utils.http.HttpsUtils;
import com.wzxc.szwenzhou.service.impl.StatisticRptRepServiceImpl;
import com.wzxc.szwenzhou.vo.StatisticRptRep;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Statistic")
@Slf4j
public class StatisticRptRepController extends BaseController {

    @Autowired
    private StatisticRptRepServiceImpl statisticRptRepService;
    @PostMapping("/report/get")
    public KbengineResult list(@RequestBody @ApiIgnore StatisticRptRep statisticRptRep) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        startPage();
        List<StatisticRptRep> statisticRptReps = statisticRptRepService.selectStatisticRptRepList(statisticRptRep);
        buildTableInfo(statisticRptReps, resultMap);
        return KbengineResult.success("查询成功", resultMap);
    }

    @PostMapping("/report/download")
    public KbengineResult download(@RequestBody @ApiIgnore StatisticRptRep statisticRptRep, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        List<StatisticRptRep> statisticRptReps = statisticRptRepService.selectStatisticRptRepList(statisticRptRep);
        HSSFWorkbook workbook = new HSSFWorkbook();
//        XSSFWorkbook workbook = new XSSFWorkbook();
        HSSFSheet sheet;
//		XSSFSheet sheet;
        try {
            sheet = workbook.createSheet(new String("统计通报表".getBytes(), "UTF-8"));
//			XSSFRow row = null;
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue(new String("牵头单位".getBytes(), "UTF-8"));
            row.createCell(1).setCellValue(new String("任务名称".getBytes(), "UTF-8"));
            row.createCell(2).setCellValue(new String("任务层级".getBytes(), "UTF-8"));
            row.createCell(3).setCellValue(new String("任务路径".getBytes(), "UTF-8"));
            row.createCell(4).setCellValue(new String("任务指标数".getBytes(), "UTF-8"));
            row.createCell(5).setCellValue(new String("改革清单数".getBytes(), "UTF-8"));
            row.createCell(6).setCellValue(new String("工作计划数".getBytes(), "UTF-8"));
            row.createCell(7).setCellValue(new String("政策体系数".getBytes(), "UTF-8"));
            row.createCell(8).setCellValue(new String("业务维护员".getBytes(), "UTF-8"));
            row.createCell(9).setCellValue(new String("手机号".getBytes(), "UTF-8"));

            for(int i = 1;i<=statisticRptReps.size();i++){
                row = sheet.createRow(i);
                StatisticRptRep statisticRptInfo = statisticRptReps.get(i-1);
                row.createCell(0).setCellValue(new String((statisticRptInfo.getOrgName()==null?"":statisticRptInfo.getOrgName()).getBytes(), "UTF-8"));
                row.createCell(1).setCellValue(new String((statisticRptInfo.getTaskName()==null?"":statisticRptInfo.getTaskName()).getBytes(), "UTF-8"));
                row.createCell(2).setCellValue(new String((statisticRptInfo.getTaskLevel()==null?"0":statisticRptInfo.getTaskLevel().toString()).getBytes(), "UTF-8"));
                row.createCell(3).setCellValue(new String((statisticRptInfo.getTaskPathName()==null?"":statisticRptInfo.getTaskPathName()).getBytes(), "UTF-8"));
                row.createCell(4).setCellValue(new String((statisticRptInfo.getQuotaCount()==null?"0":statisticRptInfo.getQuotaCount().toString()).getBytes(), "UTF-8"));
                row.createCell(5).setCellValue(new String((statisticRptInfo.getReformCount()==null?"0":statisticRptInfo.getReformCount().toString()).getBytes(), "UTF-8"));
                row.createCell(6).setCellValue(new String((statisticRptInfo.getPlanCount()==null?"0":statisticRptInfo.getPlanCount().toString()).getBytes(), "UTF-8"));
                row.createCell(7).setCellValue(new String((statisticRptInfo.getPolicyCount()==null?"":statisticRptInfo.getPolicyCount().toString()).getBytes(), "UTF-8"));
                row.createCell(8).setCellValue(new String((statisticRptInfo.getFullName()==null?"":statisticRptInfo.getFullName()).getBytes(), "UTF-8"));
                row.createCell(9).setCellValue(new String((statisticRptInfo.getMobile()==null?"":statisticRptInfo.getMobile()).getBytes(), "UTF-8"));
            }

            response.reset(); // 非常重要
            response.setContentType("Content-Type: application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + "统计通报表");
            OutputStream out = response.getOutputStream();
            try {
                workbook.write(out);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                out.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
