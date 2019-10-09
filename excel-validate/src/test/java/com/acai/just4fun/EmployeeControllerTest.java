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
public class EmployeeControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void step() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testValid() {
        String result = null;
        try {
            result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/emp/testValid")
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    /**
     * 正常上传文件
     */
    @Test
    public void testUploadExcel() {
        String result = null;
        try {
            ClassPathResource resource = new ClassPathResource("files/三国员工数据表.xlsx");
            MockMultipartFile file = new MockMultipartFile("file", resource.getInputStream());
            result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .multipart("/emp/uploadExcel")
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
            ClassPathResource resource = new ClassPathResource("files/group传空字符串.xlsx");
            MockMultipartFile file = new MockMultipartFile("file", resource.getInputStream());
            result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .multipart("/emp/uploadExcel")
                            .file(file)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        Assert.assertEquals("所属集团不能为空",result);
    }

    /**
     * idCard传空字符串
     */
    @Test
    public void testUploadExcel3() {
        String result = null;
        try {
            ClassPathResource resource = new ClassPathResource("files/idCard传空字符串.xlsx");
            MockMultipartFile file = new MockMultipartFile("file", resource.getInputStream());
            result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .multipart("/emp/uploadExcel")
                            .file(file)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        Assert.assertEquals("[idCardNo]不能为空",result);
    }
}
