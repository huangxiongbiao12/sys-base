package com.rm.common.jooq;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicEntity {

    @ExcelIgnore
    private String id;
}
