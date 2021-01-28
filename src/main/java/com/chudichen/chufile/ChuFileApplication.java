package com.chudichen.chufile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author chudichen
 * @date 2020-01-20
 */
@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class ChuFileApplication {

    public static void main(String[] args) {
       SpringApplication.run(ChuFileApplication.class, args);
    }
}
