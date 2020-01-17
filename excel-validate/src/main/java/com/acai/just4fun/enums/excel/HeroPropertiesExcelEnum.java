package com.acai.just4fun.enums.excel;

/**
 * heroPropertiesDTO对应的excel每一列与heroPropertiesDTO字段的对应关系
 */
public enum HeroPropertiesExcelEnum {
    NAME(0,"name"),
    HERO_TYPE(1,"heroType"),
    VALUE(2,"value");

    private Integer excelCellIndex;
    private String dtoFieldName;

    HeroPropertiesExcelEnum(Integer excelCellIndex, String dtoFieldName) {
        this.excelCellIndex = excelCellIndex;
        this.dtoFieldName = dtoFieldName;
    }

    public static Integer getIndex(String dtoFieldName) {
        for (HeroPropertiesExcelEnum e : HeroPropertiesExcelEnum.values()) {
            if (e.dtoFieldName.equals(dtoFieldName)) {
                return e.excelCellIndex;
            }
        }
        return null;
    }
}
