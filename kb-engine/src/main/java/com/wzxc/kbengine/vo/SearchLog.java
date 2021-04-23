package com.wzxc.kbengine.vo;

import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 search_log
 * 
 * @author huanghl
 * @date 2021-04-23
 */
@Data
public class SearchLog
// extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 日志类型
0：搜索浏览量日志
1：搜索次数（关联类型）日志 */
    private Long logType;

    /** 入参 */
    private String paramtersIn;

    /** 搜索的类型
0：综合
1：任务
2：指标
3：工作
4：政策
5：评价
6：问题 */
    private Long searchType;

    private Date createTime;

    /** 创建人（用户） */
    private String creator;

    /** 备用字段1 */
    private String standby1;

    /** 备用字段2 */
    private String standby2;

    /** 返参 */
    private String paramtersOut;

}
