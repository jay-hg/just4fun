package com.acai.just4fun.enums.excel;

/**
 * employee对应的excel每一列与EmployeeDTO字段的对应关系
 */
public enum EmployeeExcelEnum {
    NAME(0,"name"),
    AGE(1,"age"),
    ID_CARD_NO(2,"idCardNo"),
    GROUP(3,"group"),
    ROLE(4,"role");

    private Integer excelCellIndex;
    private String dtoFieldName;

    EmployeeExcelEnum(Integer excelCellIndex, String dtoFieldName) {
        this.excelCellIndex = excelCellIndex;
        this.dtoFieldName = dtoFieldName;
    }

    public static Integer getIndex(String dtoFieldName) {
        for (EmployeeExcelEnum e : EmployeeExcelEnum.values()) {
            if (e.dtoFieldName.equals(dtoFieldName)) {
                return e.excelCellIndex;
            }
        }
        return null;
    }
}
