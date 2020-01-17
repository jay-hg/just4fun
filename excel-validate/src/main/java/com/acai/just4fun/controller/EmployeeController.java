package com.acai.just4fun.controller;

import com.acai.just4fun.annotation.ExcelCol;
import com.acai.just4fun.dto.EmployeeDTO;
import com.acai.just4fun.dto.HeroPropertiesDTO;
import com.acai.just4fun.type.TypeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
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
            empList = generateDtoList(file, EmployeeDTO.class);
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
            heroPropertiesDTOList = generateDtoList(file, HeroPropertiesDTO.class);
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

    private final static TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    private <T> List<T> generateDtoList(MultipartFile file, Class clazz) throws IllegalAccessException, InstantiationException {
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

        List<T> dtoList = new ArrayList<>(sheet.getLastRowNum());
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            T dto = (T) clazz.newInstance();
            Row row = sheet.getRow(r);

            //利用反射构造dto对象
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
                if (excelCol == null) {
                    continue;
                }
                int idx = excelCol.value();

                //填值
                if (typeHandlerRegistry.hasTypeHandler(field.getType())) {
                    Object value = typeHandlerRegistry.getTypeHandler(field.getType()).getResult(row, idx);
                    field.set(dto, value);
                } else {
                    log.warn("找不到{}对应的type处理器",field.getType());
                }
            }

            dtoList.add(dto);
        }

        //校验
        validParam(dtoList);
        return dtoList;
    }

    private <T> void validParam(List<T> dtoList) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        if (dtoList != null && dtoList.size() > 0) {
            dtoList.forEach(dto -> {
                Set<ConstraintViolation<T>> constraintViolations = validator.validate(dto);
                if (constraintViolations.size() > 0) {
                    ConstraintViolation<T> constraintViolation = constraintViolations.iterator().next();
                    throw new IllegalArgumentException(constraintViolation.getMessage());
                }
            });
        }
    }

}
