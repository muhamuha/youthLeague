package com.wzxc.common.core.dao;

import lombok.Data;

import java.util.List;

/**
 * 通用批导入对象
 */
@Data
public class InsertBatchCommon {

    public List<String> fieldList; // 字段列表

    public List<List<String>> contentList; // 内容列表
}
