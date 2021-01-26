package com.chudichen.chufile.controller.install;

import com.chudichen.chufile.BaseWebTest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author chudichen
 * @date 2021-01-26
 */
public class InstallControllerTest extends BaseWebTest {

    @Test
    public void installTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/install")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .param("siteName", "chu-file")
                .param("username", "root")
                .param("password", "root")
                .param("domain", "http://127.0.0.1:13000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}