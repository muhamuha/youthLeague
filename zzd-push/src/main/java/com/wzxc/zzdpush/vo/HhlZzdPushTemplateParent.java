package com.wzxc.zzdpush.vo;

import lombok.Data;

import java.util.List;

@Data
public class HhlZzdPushTemplateParent extends HhlZzdPushTemplate {

    /** 子结点（指标） */
    private List<HhlZzdPushTemplate> sonList;

    private String templateNameSearch;

    private String contentSearch;

    private Integer parentIdSearch;
}
