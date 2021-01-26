package com.chudichen.chufile;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChuFileApplication.class)
public abstract class BaseWebTest implements WebMvcConfigurer {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    protected static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
    /**
     * 这个方法在每个方法执行之前都会执行一遍
     */
    @Before
    public void setup() {
        // 初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8));
    }

}
