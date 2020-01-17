package com.acai.just4fun;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadExcel2 {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void step() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 正常上传文件
     */
    @Test
    public void testUploadExcel() {
        String result = null;
        try {
            ClassPathResource resource = new ClassPathResource("files/英雄属性表.xlsx");
            MockMultipartFile file = new MockMultipartFile("file", resource.getInputStream());
            result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .multipart("/emp/uploadExcel2")
                            .file(file)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        Assert.assertEquals("上传成功",result);
    }

    /**
     * group传空字符串
     */
    @Test
    public void testUploadExcel2() {
        String result = null;
        try {
            ClassPathResource resource = new ClassPathResource("files/英雄属性表(属性值超限).xlsx");
            MockMultipartFile file = new MockMultipartFile("file", resource.getInputStream());
            result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .multipart("/emp/uploadExcel2")
                            .file(file)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        Assert.assertEquals("属性值不能超过100",result);
    }
}
