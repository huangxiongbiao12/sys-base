package com.rm.common.jooq;

import org.jooq.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("rawtypes")
public class FieldMerger {

    private final List<Field[]> tables = new LinkedList<>();
    private final List<Field> fields = new LinkedList<>();
    private final Set<Field> excludes = new HashSet<>();

    private FieldMerger() {
    }

    /**
     * 添加表字段
     */
    public FieldMerger add(Table... tables) {
        this.tables.addAll(Stream.of(tables).map(Table::fields).collect(Collectors.toList()));
        return this;
    }

    /**
     * 添加字段数组
     */
    public FieldMerger add(Field[]... fields) {
        Collections.addAll(this.tables, fields);
        return this;
    }

    /**
     * 添加字段
     */
    public FieldMerger add(Field... field) {
        Collections.addAll(this.fields, field);
        return this;
    }

    public FieldMerger exclude(Field... field) {
        Collections.addAll(this.excludes, field);
        return this;
    }

    /**
     * 合并传入的字段、字段数组、表字段
     *
     * @return 字段数组
     */
    public Field[] merge() {
        fields.addAll(tables.stream().flatMap(Stream::of)
                .filter(f -> !excludes.contains(f)).collect(Collectors.toList()));
        return fields.toArray(new Field[0]);
    }

    /**
     * 合并传入的字段、字段数组、表字段
     */
    public Record record(DSLContext ctx) {
        if (ctx == null) return null;
        return ctx.newRecord(this.merge());
    }

    /**
     * 合并传入的字段、字段数组、表字段
     */
    public Result<Record> result(DSLContext ctx) {
        if (ctx == null) return null;
        return ctx.newResult(this.merge());
    }

    /**
     * 初始化并填入初始数组字段
     */
    public static FieldMerger of(Field[]... tables) {
        return new FieldMerger().add(tables);
    }

    /**
     * 初始化并填入初始字段列表
     */
    public static FieldMerger of(Field... tables) {
        return new FieldMerger().add(tables);
    }

    /**
     * 初始化并填入初始表字段
     */
    public static FieldMerger of(Table... tables) {
        return new FieldMerger().add(tables);
    }

    /**
     * 初始化
     */
    public static FieldMerger of() {
        return new FieldMerger();
    }
}
