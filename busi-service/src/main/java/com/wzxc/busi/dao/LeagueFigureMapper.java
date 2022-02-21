package com.wzxc.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzxc.busi.vo.LeagueElect;

import java.util.List;
import java.util.Map;

public interface LeagueFigureMapper extends BaseMapper<LeagueElect> {

    List<Map<String, Double>> queryGender();

    List<Map<String, Double>> queryAge();

    List<Map<String, Double>> queryEducation();

    List<Map<String, Double>> queryDegree();

    List<Map<String, Double>> queryOrigin();

    List<Map<String, Double>> queryNation();

    List<Map<String, Double>> queryPoliticalStatus();

    List<Map<String, Double>> queryIndustry();

    List<Map<String, Double>> queryVocation();

    long count();
}
