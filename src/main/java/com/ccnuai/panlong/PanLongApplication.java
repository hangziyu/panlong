package com.ccnuai.panlong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ccnuai.panlong.mapper")
public class PanLongApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanLongApplication.class, args);
    }

}
