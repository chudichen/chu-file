package com.chudichen.chufile;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChuFileApplication.class)
public abstract class BaseWebTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    /**
     * 这个方法在每个方法执行之前都会执行一遍
     */
    @Before
    public void setup() {
        // 初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
