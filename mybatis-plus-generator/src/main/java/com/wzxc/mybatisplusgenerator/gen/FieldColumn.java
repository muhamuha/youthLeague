package com.wzxc.mybatisplusgenerator.gen;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("information_schema.COLUMNS")
public class FieldColumn {

    @TableField(value = "column_key")
    private String columnKey;

    @TableField(value = "table_schema")
    private String dbName;

    @TableField(value = "table_name")
    private String tableName;

    @TableField(value = "column_name")
    private String fieldName;

    @TableField(value = "column_default")
    private String columnDefault;

    @TableField(value = "is_nullable")
    private String isNullable;

    @TableField(value = "data_type")
    private String dataType;

    @TableField(value = "column_comment")
    private String columnComment;

    @TableField(exist = false)
    private String javaField;

    @TableField(exist = false)
    private String javaType;
}
