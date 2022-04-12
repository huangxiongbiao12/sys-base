package com.rm.generator.jooq;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BasicEntity {

    @ExcelIgnore
    @Id
    @Column(columnDefinition = "varchar(32) comment '主键'")
    private String id;

    /**
     * 创建时间
     */
    @Column(columnDefinition = "datetime comment '创建时间'")
    private LocalDateTime createDate;

}
