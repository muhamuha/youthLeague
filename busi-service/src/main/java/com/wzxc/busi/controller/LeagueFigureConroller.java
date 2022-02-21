package com.wzxc.busi.controller;

import com.wzxc.busi.service.impl.LeagueFigureServiceImpl;
import com.wzxc.busi.vo.LeagueGoodman;
import com.wzxc.common.core.domain.BusiResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/leagueFigure")
@Api(tags="画像类")
public class LeagueFigureConroller {

    @Autowired
    private LeagueFigureServiceImpl leagueFigureService;

    /**
     * 青联画像【请填写功能名称】
     */
    @ApiOperation(value = "青联画像", notes = "青联画像", httpMethod = "GET")
    @ApiImplicitParams({

    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK"),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @GetMapping("/youthLeague")
    public BusiResult youthLeague() {
        Map<String, Object> resultMap = new HashMap<>();

        // 性别分布
        List<Map<String, Double>> genderMap = leagueFigureService.queryGender();

        // 年龄分布
        List<Map<String, Double>> ageMap = leagueFigureService.queryAge();

        // 学历分布
        List<Map<String, Double>> educationMap = leagueFigureService.queryEducation();

        // 学位分布
        List<Map<String, Double>> degreeMap = leagueFigureService.queryDegree();

        // 籍贯分布
        List<Map<String, Double>> originMap = leagueFigureService.queryOrigin();

        // 民族分布
        List<Map<String, Double>> nationMap = leagueFigureService.queryNation();

        // 政治面貌分布
        List<Map<String, Double>> politicalStatusMap = leagueFigureService.queryPoliticalStatus();

        // 界别分布
        List<Map<String, Double>> industryMap = leagueFigureService.queryIndustry();

        // 职业分布
        List<Map<String, Double>> vocationMap = leagueFigureService.queryVocation();

        // 委员总数
        long count = leagueFigureService.count();

        resultMap.put("gender", genderMap);
        resultMap.put("age", ageMap);
        resultMap.put("education", educationMap);
        resultMap.put("degree", degreeMap);
        resultMap.put("origin", originMap);
        resultMap.put("nation", nationMap);
        resultMap.put("politicalStatus", politicalStatusMap);
        resultMap.put("industry", industryMap);
        resultMap.put("vocation", vocationMap);
        resultMap.put("total", count);
        return BusiResult.success("查询成功", resultMap);
    }
}
