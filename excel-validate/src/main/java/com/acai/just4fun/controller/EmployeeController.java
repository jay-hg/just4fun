package com.acai.just4fun.controller;

import com.acai.just4fun.dto.BasicExcelDTO;
import com.acai.just4fun.dto.EmployeeDTO;
import com.acai.just4fun.dto.HeroPropertiesDTO;
import com.acai.just4fun.enums.excel.EmployeeExcelEnum;
import com.acai.just4fun.enums.excel.HeroPropertiesExcelEnum;
import com.acai.just4fun.enums.HeroTypeEnum;
import com.acai.just4fun.type.TypeHandlerRegistry;
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

import javax.validation.*;
import java.io.IOException;
import java.io.InputStream;
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
        if (!BasicExcelDTO.class.isAssignableFrom(clazz)) {
            throw new IllegalStateException("dto应实现BasicExcelDTO");
        }
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
                int idx = 0;

                if (clazz == EmployeeDTO.class) {
                    idx = EmployeeExcelEnum.getIndex(field.getName());
                } else {
                    idx = HeroPropertiesExcelEnum.getIndex(field.getName());
                }
                Cell cell = row.getCell(idx);

                //填值
                if (cell != null) {
                    Object value = typeHandlerRegistry.getMappingTypeHandler(field.getType()).getResult(row, idx);
                    field.set(dto,value);
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
