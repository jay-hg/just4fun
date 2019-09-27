package com.acai.just4fun.controller;

import com.acai.just4fun.dto.EmployeeDTO;
import com.acai.just4fun.enums.EmployeeExcelEnum;
import com.acai.just4fun.handler.Handler;
import com.acai.just4fun.handler.HandlerFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
        if (file.isEmpty()) {
            return "文件为空";
        }

        InputStream is = null;
        List<EmployeeDTO> empList = new ArrayList<>();
        try {
            is = file.getInputStream();
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);

            Class clazz = EmployeeDTO.class;
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                Row row = sheet.getRow(r);

                //利用反射构造dto对象
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Cell cell = row.getCell(EmployeeExcelEnum.getIndex(field.getName()));

                    //校验
                    String handleResult = validateCell(field, cell);
                    if (handleResult != null) return handleResult;

                    //填值
                    if (cell != null) {
                        switch (cell.getCellTypeEnum()) {
                            case NUMERIC:
                                Double dNum = cell.getNumericCellValue();
                                field.set(employeeDTO, dNum.intValue());
                                break;
                            case STRING:
                                String str = cell.getStringCellValue();
                                field.set(employeeDTO, str);
                                break;
                            default:
                        }
                    }
                }

                empList.add(employeeDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (EmployeeDTO dto : empList) {
            System.out.println(dto);
        }
        return "上传成功";
    }

    private String validateCell(Field field, Cell cell) throws IllegalAccessException {
        for (Annotation annoattion : field.getDeclaredAnnotations()) {
            Handler handler = HandlerFactory.createHandler(annoattion.annotationType());
            if (handler == null) {
                continue;
            }
            String handleResult = handler.handle(field, cell);
            if (handleResult != null) {
                return handleResult;
            }
        }

        return null;
    }
}
