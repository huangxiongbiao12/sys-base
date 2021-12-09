package com.rm.common.jooq;

import lombok.Getter;

@Getter
public enum Compare {

    EQ("eq", " = "),
    IN("in", " in "),
    NOT_EQ("ne", " != "),
    GE("ge", " >= "),
    LE("le", " <= "),
    GT("gt", " > "),
    LT("lt", " < "),
    LIKE("like", " LIKE "),
    ;

    private String code;
    private String value;

    Compare(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValue(String key) {
        Compare[] compares = Compare.values();
        for (Compare compare : compares) {
            if (compare.code.equals(key)) {
                return compare.value;
            }
        }
        return " = ";
    }

}
