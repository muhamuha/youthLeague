package com.wzxc.zzdpush.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class TemplateContent implements Serializable {

    private String type = "oa";

    @Data
    public class Head{
        private String bgcolor;
        private String text;
    }
    private Head head;

    @Data
    public class Body{
        private String title;
        private String template;
        private List<Map<String, String>> form;
        private String author;
    }
    @NotNull(message = "内容必填")
    private Body body;

    @NotBlank(message = "接收者code必填")
    private String emcodes;

    private String bizMsgId;
}
