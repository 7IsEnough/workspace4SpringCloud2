package com.clearlove;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.clearlove.mapper")
@SpringBootApplication
public class MpDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(MpDemoApplication.class, args);
  }
}
