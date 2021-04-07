package com.wzxc.zzdpush.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class HhlZzdPushHisProcess{

    /** 主键 */
    private Integer id;

    /** 工作通知id */
    private String pushId;

    /** 0：推送
        1：修改
        2：撤销 */
    private Integer type;

    /** 0：成功
        1：失败 */
    private Integer status;

    private Date createTime;

    private String pushReturn;

    private String content;
}
