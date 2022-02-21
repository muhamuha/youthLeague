package com.wzxc.busi.service.impl;

import com.wzxc.busi.dao.LeagueFigureMapper;
import com.wzxc.busi.service.ILeagueFigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LeagueFigureServiceImpl implements ILeagueFigureService {

    @Autowired
    private LeagueFigureMapper leagueFigureMapper;


    @Override
    public List<Map<String, Double>> queryGender() {
        return leagueFigureMapper.queryGender();
    }

    @Override
    public List<Map<String, Double>> queryAge() {
        return leagueFigureMapper.queryAge();
    }

    @Override
    public List<Map<String, Double>> queryEducation() {
        return leagueFigureMapper.queryEducation();
    }

    @Override
    public List<Map<String, Double>> queryDegree() {
        return leagueFigureMapper.queryDegree();
    }

    @Override
    public List<Map<String, Double>> queryOrigin() {
        return leagueFigureMapper.queryOrigin();
    }

    @Override
    public List<Map<String, Double>> queryNation() {
        return leagueFigureMapper.queryNation();
    }

    @Override
    public List<Map<String, Double>> queryPoliticalStatus() {
        return leagueFigureMapper.queryPoliticalStatus();
    }

    @Override
    public List<Map<String, Double>> queryIndustry() {
        return leagueFigureMapper.queryIndustry();
    }

    @Override
    public List<Map<String, Double>> queryVocation() {
        return leagueFigureMapper.queryVocation();
    }

    @Override
    public long count() {
        return leagueFigureMapper.count();
    }

}
