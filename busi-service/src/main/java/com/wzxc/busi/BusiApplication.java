package com.wzxc.busi;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableSwaggerBootstrapUI
@SpringBootApplication
@ComponentScan(basePackages = {"com.wzxc.*"})
public class BusiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusiApplication.class, args);
    }

}
