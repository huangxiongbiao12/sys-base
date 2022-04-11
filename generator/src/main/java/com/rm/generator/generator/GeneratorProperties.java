package com.rm.generator.generator;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
        prefix = "rm.generator"
)
@Component
public class GeneratorProperties {

    public static enum Type {
        ALL,
        ENTITY,
        DAO,
        SERVICE,
        CONTROLLER;
    }

    public static enum DATEType {
        LocalDateTime,
        Date;
    }

    /*生成代码数据库配置*/
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String tableName = "%";
    /*代码保存路径配置*/
    private String sourcePath="src/main/java";
    private String basePackage;
    private String jooqPackage; //默认在base后加jooq  未设置时
    /*去除前缀*/
    private String prefix[] = null;
    /*代码生成范围 默认全部*/
    private Type[] type = new Type[]{Type.ALL};
    // 时间使用类型 默认 LocalDateTime
    private DATEType dateType = DATEType.LocalDateTime;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String[] getPrefix() {
        return prefix;
    }

    public void setPrefix(String[] prefix) {
        this.prefix = prefix;
    }

    public Type[] getType() {
        return type;
    }

    public void setType(Type[] type) {
        this.type = type;
    }

    public DATEType getDateType() {
        return dateType;
    }

    public void setDateType(DATEType dateType) {
        this.dateType = dateType;
    }

    public String getJooqPackage() {
        return jooqPackage;
    }

    public void setJooqPackage(String jooqPackage) {
        this.jooqPackage = jooqPackage;
    }
}
