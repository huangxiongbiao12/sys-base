package com.rm.generator.jooq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
public class Paging<T> extends PageRequest {

    private int total;

    // 数据列表
    private List<T> val;

    public Paging(int page, int size) {
        super(page, size, Sort.unsorted());
    }

    public Paging(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public Paging(PageRequest page) {
        super(page.getPageNumber(), page.getPageSize(), page.getSort());
    }

    public static Paging of(PageRequest page) {
        return new Paging(page);
    }

    public static Paging of(int page, int size) {
        return new Paging(page, size, Sort.unsorted());
    }

    public int getTotalPage() {
        return (int) Math.ceil((double) total / getPageSize());
    }

}
