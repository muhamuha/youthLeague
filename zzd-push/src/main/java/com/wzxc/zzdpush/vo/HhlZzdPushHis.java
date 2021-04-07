package com.wzxc.zzdpush.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wzxc.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class HhlZzdPushHis implements Serializable {

    /** $column.columnComment */
    @ApiModelProperty(value = "主键")
    private String id;

    /** $column.columnComment */
    @ApiModelProperty(value = "消息主体")
    private String content;

    /** 推送时间 */
    @ApiModelProperty(value = "推送时间")
    private Date pushTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 推送消息的id(浙政钉接口返回) */
    @ApiModelProperty(value = "消息主键")
    private String messageId;

    /** 创建人 */
    @NotBlank(message = "创建者必填")
    @ApiModelProperty(value = "创建者")
    private String creator;

    /** 抄送人 */
    @ApiModelProperty(value = "抄送人")
    private String copyFor;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String contentSimple;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date pushDateBegin;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date pushDateEnd;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String keyword;
}
