package com.wzxc.busi;

import com.wzxc.mybatisplusgenerator.gen.CodeGenContrllor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class BusiApplicationTests {

    @Autowired
    private CodeGenContrllor codeGenContrllor;

    @Test
    void gen(){
        codeGenContrllor.gen(BusiApplication.class);
    }
}
