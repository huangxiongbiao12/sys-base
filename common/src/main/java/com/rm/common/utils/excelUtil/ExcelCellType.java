package com.rm.common.utils.excelUtil;

import org.apache.commons.lang3.StringUtils;

public enum ExcelCellType {

    TEXT("STRING", "文本"),
    NUM("NUMERIC", "数字"),
    BLANK("BLANK", "空"),
    FORMULA("FORMULA", "公式"),
    NONE("_NONE", "未知");

    private String value;

    private String desc;

    ExcelCellType (String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据值返回描述
     * @param value
     * @return
     */
    private static ExcelCellType getDescByValue(String value){
        for(ExcelCellType type : ExcelCellType.values()){
            if(StringUtils.equals(type.getValue(), value)){
                return type;
            }
        }
        return null;
    }

    /**
     * 根据值返回描述
     * @param value
     * @return
     */
    public static String getDesc(String value){
        return getDescByValue(value).getDesc();
    }
}
