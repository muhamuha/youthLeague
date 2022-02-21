package com.wzxc.busi.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeagueGoodmanRecommend {

    @TableField("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("parent")
    private String parent;

    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LeagueGoodmanRecommend> childList;

    // 推荐人
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long commissinorId;
    @TableField("goodman_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long goodmanId;
}
