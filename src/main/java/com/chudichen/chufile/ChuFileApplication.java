package com.chudichen.chufile;

import com.chudichen.chufile.model.constant.ChuFileConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author chudichen
 * @date 2020-01-20
 */
@SpringBootApplication
public class ChuFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChuFileApplication.class, args);
        System.out.println(ChuFileConstant.AUDIO_MAX_FILE_SIZE_MB);
    }

}
