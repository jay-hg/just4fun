package com.acai.just4fun.controller;

import com.acai.just4fun.dto.EmployeeDTO;
import com.acai.just4fun.dto.HeroPropertiesDTO;
import com.acai.just4fun.type.ExcelConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @PostMapping("/testValid")
    public String testValidated(@Validated EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }
        return "hello";
    }

    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {
        List<EmployeeDTO> empList = null;

        try {
            empList = ExcelConvertUtil.generateDtoList(file, EmployeeDTO.class);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        for (EmployeeDTO dto : empList) {
            System.out.println(dto);
        }
        return "上传成功";
    }

    @PostMapping("/uploadExcel2")
    public String uploadExcel2(@RequestParam("file") MultipartFile file) {
        List<HeroPropertiesDTO> heroPropertiesDTOList = null;

        try {
            heroPropertiesDTOList = ExcelConvertUtil.generateDtoList(file, HeroPropertiesDTO.class);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        for (HeroPropertiesDTO dto : heroPropertiesDTOList) {
            System.out.println(dto);
        }
        return "上传成功";
    }
}
