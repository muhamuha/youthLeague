package com.wzxc.zzdpush.vo;

import lombok.Data;

import java.util.Date;

@Data
public class HhlZzdPushUser {

    private String id;

    private String username;

    private String password;

    private Date createTime;

    private Integer status;

    private String nick;
}
