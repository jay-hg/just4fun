package com.acai.just4fun.controller;

import com.acai.just4fun.dto.EmployeeDTO;
import com.acai.just4fun.enums.EmployeeExcelEnum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

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
            empList = generateEmpList(file);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        for (EmployeeDTO dto : empList) {
            System.out.println(dto);
        }
        return "上传成功";
    }

    private List<EmployeeDTO> generateEmpList(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }

        InputStream is = null;
        Workbook wb = null;
        try {
            is = file.getInputStream();
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            logger.error("uploadExcel io异常,e={}", e);
        }
        Sheet sheet = wb.getSheetAt(0);

        List<EmployeeDTO> empList = new ArrayList<>(sheet.getLastRowNum());
        Class clazz = EmployeeDTO.class;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            Row row = sheet.getRow(r);

            //利用反射构造dto对象
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Cell cell = row.getCell(EmployeeExcelEnum.getIndex(field.getName()));

                //填值
                if (cell != null) {
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            Double dNum = cell.getNumericCellValue();
                            try {
                                field.set(employeeDTO, dNum.intValue());
                            } catch (IllegalAccessException e) {
                                logger.error("文件上传 填充double数值,e={}", e);
                            }
                            break;
                        case STRING:
                            String str = cell.getStringCellValue();
                            try {
                                field.set(employeeDTO, str);
                            } catch (IllegalAccessException e) {
                                logger.error("文件上传 填充String字段,e={}", e);
                            }
                            break;
                        default:
                            //暂不支持其他类型
                    }
                }
            }

            empList.add(employeeDTO);
        }

        //校验
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        if (empList != null && empList.size() > 0) {
            empList.forEach(employeeDTO -> {
                Set<ConstraintViolation<EmployeeDTO>> constraintViolations = validator.validate(employeeDTO);
                if (constraintViolations.size() > 0) {
                    ConstraintViolation<EmployeeDTO> constraintViolation = constraintViolations.iterator().next();
                    throw new IllegalArgumentException(constraintViolation.getMessage());
                }
            });
        }
        /*Set<ConstraintViolation<List<EmployeeDTO>>> constraintViolations = validator.validate(empList);
        if (constraintViolations.size() > 0) {
            ConstraintViolation<List<EmployeeDTO>> constraintViolation = constraintViolations.iterator().next();
            throw new IllegalArgumentException(constraintViolation.getMessage());
        }*/
        return empList;
    }

}
