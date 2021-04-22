package com.wzxc.szwenzhou;

import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.szwenzhou.service.impl.StatisticRptRepServiceImpl;
import com.wzxc.szwenzhou.vo.StatisticRptRep;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private StatisticRptRepServiceImpl statisticRptRepService;

    void download() {
        StatisticRptRep statisticRptRep = new StatisticRptRep();
        statisticRptRep.setTaskSystem(0);
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

            File file = new File("C:\\Users\\ms\\Desktop\\统计通报表.xlsx");
            FileOutputStream out = new FileOutputStream(file);
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
    }

}
