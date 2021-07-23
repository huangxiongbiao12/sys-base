package com.rm.common.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.util.StringUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class Generator {


    /*生成代码数据库配置*/
    private static String dbUrl;
    private static String tableName;
    private static String dbDriver;
    private static String dbUser;
    private static String dbPassword;
    /*代码保存路径配置*/
    private static String sourcePath;
    private static String basePackage;
    private static String jooqPackage;
    private static String entityPackage;
    private static String daoPackage;
    private static String servicePackage;
    private static String serviceImplPackage;
    private static String controllerPackage;
    private static String[] prefix;
    // 代码模板的位置
    static String templateDir = "/Users/apple/Desktop/code/github/sys-base/common/src/main/resources/template";

    // 导出excel备注标志
    private final static String EXCEL_FLAG = "excel";


    private static Connection connection = null;
    private static DatabaseMetaData dbmd = null;
    private static ResultSet resultSet = null;
    private static GeneratorProperties generatorProperties;

    // 时间包
    private static final String IMPORT_DATE = "import java.util.Date;\r\nimport com.alibaba.excel.annotation.format.DateTimeFormat;\r\n";
    private static final String IMPORT_LOCALDATETIME = "import java.time.LocalDateTime;\r\nimport com.rm.common.utils.excel.LocalDateTimeConverter;\r\n";
    private static final String IMPORT_BASEENTITY = "import com.rm.common.jooq.BasicEntity;\r\n";
    private static final String IMPORT_DATA = "import lombok.Data;\r\n";
    private static final String IMPORT_EXCEL = "import com.alibaba.excel.annotation.ExcelIgnore;\r\nimport com.alibaba.excel.annotation.ExcelProperty;\r\n";


    /*数据库初始化*/
    private static void init() {
        generatorProperties = GeneratorConfigration.properties();
        /*生成代码数据库配置*/
        dbUser = generatorProperties.getUsername();
        dbPassword = generatorProperties.getPassword();
        dbUrl = generatorProperties.getUrl() + "&user=" + dbUser + "&password=" + dbPassword;
        tableName = generatorProperties.getTableName();
        dbDriver = generatorProperties.getDriverClassName();
        /*代码保存路径配置*/
        sourcePath = generatorProperties.getSourcePath();
        basePackage = generatorProperties.getBasePackage();
        if (StringUtils.hasLength(generatorProperties.getJooqPackage())) {
            jooqPackage = generatorProperties.getJooqPackage();
        } else {
            jooqPackage = basePackage + ".jooq";
        }
        entityPackage = basePackage + ".entity";
        daoPackage = basePackage + ".dao";
        servicePackage = basePackage + ".service";
        serviceImplPackage = servicePackage + ".impl";
        controllerPackage = basePackage + ".controller";
        prefix = generatorProperties.getPrefix();
        try {
            Properties props = new Properties();
//            props.setProperty("user", dbUser);
//            props.setProperty("password", dbPassword);
//            props.setProperty("useUnicode", "true");
//            props.setProperty("characterEncoding", "UTF-8");
            props.setProperty("remarks", "true"); //设置可以获取remarks信息
            props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, props);
            dbmd = connection.getMetaData();
            resultSet = dbmd.getTables(connection.getCatalog(), connection.getSchema(), tableName, new String[]{"TABLE"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*生成代码主方法*/
    public static void generate() {
        init();
        GeneratorProperties.Type[] types = generatorProperties.getType();
        for (GeneratorProperties.Type type : types) {
            if (type == GeneratorProperties.Type.ALL) {
                generateEntity();
                generateDAO();
                generateService();
                generateServiceImpl();
                generateController();
                return;
            }
            switch (type) {
                case ENTITY: {
                    generateEntity();
                }
                break;
                case DAO: {
                    generateDAO();
                }
                break;
                case SERVICE: {
                    generateService();
                    generateServiceImpl();
                }
                break;
                case CONTROLLER: {
                    generateController();
                }
                break;
            }
        }
    }

    /**
     * 生成实体类
     */
    private static void generateEntity() {
        try {
            resultSet.beforeFirst();
            //迭代所有的表
            while (resultSet.next()) {
                //表名
                String tableName = resultSet.getString("TABLE_NAME");
                String tableRemake = resultSet.getString("REMARKS");
                boolean isExport = tableRemake.contains(EXCEL_FLAG);
                //字段信息集合
                ResultSet colrs = dbmd.getColumns(connection.getCatalog(), connection.getSchema(), tableName, "%");
                //创建类文件
                if (getEntitySavePath() == null) break;
                String classFilePath = getEntitySavePath() + getEntityName(tableName) + ".java";
                //文件输出流
                FileWriter fw = new FileWriter(classFilePath);
                PrintWriter pw = new PrintWriter(fw);
                //先写package import文本
                StringBuffer fileBuffer = new StringBuffer();
                if (entityPackage != null && !entityPackage.equals(""))
                    fileBuffer.append("package " + entityPackage + ";\r\n\n");
                fileBuffer.append("import java.io.Serializable;" + "\r\n");
                fileBuffer.append(IMPORT_BASEENTITY);
                fileBuffer.append(IMPORT_DATA);
                // 导出excel注解引入
                if (isExport) {
                    fileBuffer.append(IMPORT_EXCEL);
                }
                //类实体文本
                StringBuffer contentBuffer = new StringBuffer();
                //类名称
                contentBuffer.append("\r\n");
                contentBuffer.append("/**\r\n");
                contentBuffer.append(" *\tCreated by admin on " + new Date() + "\r\n");
                contentBuffer.append(" *\t" + tableRemake + "\r\n");
                contentBuffer.append(" */\r\n");
                contentBuffer.append("@Data\r\n");
                contentBuffer.append("public class " + getEntityName(tableName) + " extends BasicEntity implements Serializable" + "{\r\n");
                //属性文本
                StringBuffer attrBuffer = new StringBuffer();
                //方法文本
                StringBuffer methodBuffer = new StringBuffer("\r\n");

                String primaryKey = null;
                ResultSet pkRSet = dbmd.getPrimaryKeys(null, null, tableName);
                while (pkRSet.next()) {
                    primaryKey = (String) pkRSet.getObject(4);
                }
                int i = 0;
                //写入属性 setter和getter方法
                while (colrs.next()) {
                    addField(isExport, attrBuffer, colrs);
                    if (sqlType2JavaType(colrs.getString("TYPE_NAME")).contains("Date") && i == 0) {
                        if (generatorProperties.getDateType() == GeneratorProperties.DATEType.Date) {
                            fileBuffer.append(IMPORT_DATE);
                        } else {
                            fileBuffer.append(IMPORT_LOCALDATETIME);
                        }
                        i = 1;
                    }
//                    addMethod(methodBuffer, colrs);
                }

                contentBuffer.append(attrBuffer);
//                contentBuffer.append(methodBuffer);
                contentBuffer.append("}\r\n");

                fileBuffer.append(contentBuffer);

                pw.println(fileBuffer.toString());

                pw.flush();
                pw.close();

            }
        } catch (SQLException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 生成dao
     */
    private static void generateDAO() {
        try {
            resultSet.beforeFirst();
            //迭代所有的表  daoPackage tableRemake domainName upperDomainName
            while (resultSet.next()) {
                //表名
                String tableName = resultSet.getString("TABLE_NAME");
                String tableRemake = resultSet.getString("REMARKS");
                String domainName = initialCap(tableName);
                String upperDomainName = tableName.toUpperCase();
                String daoPackage = Generator.daoPackage;
                //创建mapper文件
                if (getDaoSavePath() == null) break;
                String targetFile = getDaoSavePath() + domainName + "Dao.java";

                Properties pro = new Properties();
                pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //可选值："class"--从classpath中读取，"file"--从文件系统中读取
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //如果从文件系统中读取模板，那么属性值为org.apache.velocity.runtime.resource.loader.FileResourceLoader
                pro.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                VelocityEngine ve = new VelocityEngine(pro);

                VelocityContext context = new VelocityContext();
                context.put("daoPackage", daoPackage);
                context.put("entityPackage", entityPackage);
                context.put("tableRemake", tableRemake);
                context.put("domainName", domainName);
                context.put("upperDomainName", upperDomainName);
                context.put("jooqPackage", jooqPackage);
                Template t = ve.getTemplate("template/dao.template", "UTF-8");

                File file = new File(targetFile);
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    continue;
                }

                FileOutputStream outStream = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(outStream,
                        "UTF-8");
                BufferedWriter sw = new BufferedWriter(writer);
                t.merge(context, sw);
                sw.flush();
                sw.close();
                outStream.close();
                System.out.println("成功生成Java文件:" + (targetFile).replaceAll("/", "\\\\"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成service
     */
    private static void generateService() {
        try {
            resultSet.beforeFirst();
            //迭代所有的表  daoPackage tableRemake domainName upperDomainName
            while (resultSet.next()) {
                //表名
                String tableName = resultSet.getString("TABLE_NAME");
                String tableRemake = resultSet.getString("REMARKS");
                String domainName = initialCap(tableName);
                String servicePackage = Generator.servicePackage;
                //创建mapper文件
                if (getServiceSavePath() == null) break;
                String targetFile = getServiceSavePath() + domainName + "Service.java";

                Properties pro = new Properties();
                pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //可选值："class"--从classpath中读取，"file"--从文件系统中读取
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //如果从文件系统中读取模板，那么属性值为org.apache.velocity.runtime.resource.loader.FileResourceLoader
                pro.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                VelocityEngine ve = new VelocityEngine(pro);

                VelocityContext context = new VelocityContext();
                context.put("servicePackage", servicePackage);
                context.put("entityPackage", entityPackage);
                context.put("tableRemake", tableRemake);
                context.put("domainName", domainName);
                Template t = ve.getTemplate("template/service.template", "UTF-8");

                File file = new File(targetFile);
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    continue;
                }

                FileOutputStream outStream = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(outStream,
                        "UTF-8");
                BufferedWriter sw = new BufferedWriter(writer);
                t.merge(context, sw);
                sw.flush();
                sw.close();
                outStream.close();
                System.out.println("成功生成Java文件:" + (targetFile).replaceAll("/", "\\\\"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成serviceImpl
     */
    private static void generateServiceImpl() {
        try {
            resultSet.beforeFirst();
            //迭代所有的表  daoPackage tableRemake domainName upperDomainName
            while (resultSet.next()) {
                //表名
                String tableName = resultSet.getString("TABLE_NAME");
                String tableRemake = resultSet.getString("REMARKS");
                String domainName = initialCap(tableName);
                String servicePackage = Generator.servicePackage;
                //创建mapper文件
                if (getServiceImplSavePath() == null) break;
                String targetFile = getServiceImplSavePath() + domainName + "ServiceImpl.java";

                Properties pro = new Properties();
                pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //可选值："class"--从classpath中读取，"file"--从文件系统中读取
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //如果从文件系统中读取模板，那么属性值为org.apache.velocity.runtime.resource.loader.FileResourceLoader
                pro.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                VelocityEngine ve = new VelocityEngine(pro);

                VelocityContext context = new VelocityContext();
                context.put("servicePackage", servicePackage);
                context.put("entityPackage", entityPackage);
                context.put("tableRemake", tableRemake);
                context.put("domainName", domainName);
                context.put("daoPackage", daoPackage);
                Template t = ve.getTemplate("template/serviceimpl.template", "UTF-8");

                File file = new File(targetFile);
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    continue;
                }
                FileOutputStream outStream = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(outStream,
                        "UTF-8");
                BufferedWriter sw = new BufferedWriter(writer);
                t.merge(context, sw);
                sw.flush();
                sw.close();
                outStream.close();
                System.out.println("成功生成Java文件:" + (targetFile).replaceAll("/", "\\\\"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成controller
     */
    private static void generateController() {
        try {
            resultSet.beforeFirst();
            //迭代所有的表  daoPackage tableRemake domainName upperDomainName
            while (resultSet.next()) {
                //表名
                String tableName = resultSet.getString("TABLE_NAME");
                String tableRemake = resultSet.getString("REMARKS");
                String domainName = initialCap(tableName);
                String servicePackage = Generator.servicePackage;
                //创建mapper文件
                if (getControllerSavePath() == null) break;
                String targetFile = getControllerSavePath() + domainName + "Controller.java";

                Properties pro = new Properties();
                pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //可选值："class"--从classpath中读取，"file"--从文件系统中读取
                pro.setProperty(Velocity.RESOURCE_LOADER, "class");
                //如果从文件系统中读取模板，那么属性值为org.apache.velocity.runtime.resource.loader.FileResourceLoader
                pro.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                VelocityEngine ve = new VelocityEngine(pro);

                VelocityContext context = new VelocityContext();
                context.put("servicePackage", servicePackage);
                context.put("controllerPackage", controllerPackage);
                context.put("entityPackage", entityPackage);
                context.put("tableRemake", tableRemake);
                context.put("domainName", domainName);
                context.put("mapping", tableName.replace("_", "-"));
                context.put("daoPackage", daoPackage);
                context.put("attrName", columnName2AttrName(tableName));
                Template t = null;
                // 生成excel 导出接口 判断
                if (!tableRemake.contains(EXCEL_FLAG)) {
                    t = ve.getTemplate("template/controller.template", "UTF-8");
                } else {
                    if (tableRemake.contains("\n")) {
                        tableRemake = tableRemake.substring(0, tableRemake.indexOf("\n"));
                    }
                    String fileName = tableRemake.trim().replace(EXCEL_FLAG, "");
                    context.put("fileName", fileName);
                    t = ve.getTemplate("template/export_controller.template", "UTF-8");
                }
                File file = new File(targetFile);
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    continue;
                }
                FileOutputStream outStream = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(outStream,
                        "UTF-8");
                BufferedWriter sw = new BufferedWriter(writer);
                t.merge(context, sw);
                sw.flush();
                sw.close();
                outStream.close();
                System.out.println("成功生成Java文件:" + (targetFile).replaceAll("/", "\\\\"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getEntitySavePath() {
        return getSavePath(entityPackage);
    }

    private static String getDaoSavePath() {
        return getSavePath(daoPackage);
    }

    private static String getServiceSavePath() {
        return getSavePath(servicePackage);
    }

    private static String getServiceImplSavePath() {
        return getSavePath(serviceImplPackage);
    }

    private static String getControllerSavePath() {
        return getSavePath(controllerPackage);
    }

    /**
     * 创建文件夹
     *
     * @param savePath
     * @return
     */
    private static String getSavePath(String savePath) {
        String path = null;
        if (savePath == null || savePath.equals("")) {
            //获取当前用户桌面路径
            FileSystemView fsv = FileSystemView.getFileSystemView();
            path = fsv.getHomeDirectory().toString();
        } else {
            path = sourcePath;
        }

        if (!savePath.equals("")) path = path + "/" + savePath.replace(".", "/") + "/";
        System.out.println(path);

        //创建目录
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("创建目录成功！");
            } else {
                System.out.println("创建目录失败！");
                return null;
            }
        }

        return path;
    }

    /**
     * 生成成员属性
     *
     * @param contentBuffer
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static StringBuffer addField(boolean isExport, StringBuffer contentBuffer, ResultSet resultSet) throws SQLException {
        if (contentBuffer == null || resultSet == null) return contentBuffer;
        //先写注释
        String remarks = resultSet.getString("REMARKS");
        contentBuffer.append("\r\n");
        contentBuffer.append("\t/**\r\n");
        contentBuffer.append("\t *\t" + remarks + "\r\n");
        contentBuffer.append("\t */\r\n");
        //写excel注解
        if (isExport) {
            // 备注含有 excel的 写导出注解 否则忽略
            if (remarks.contains(EXCEL_FLAG)) {
                if (remarks.contains("\n")) {
                    remarks = remarks.substring(0, remarks.indexOf("\n"));
                }
                // 字段如果为日期格式需要转换格式
                if (sqlType2JavaType(resultSet.getString("TYPE_NAME")).contains("Date")) {
                    if (generatorProperties.getDateType() != GeneratorProperties.DATEType.Date) {
                        contentBuffer.append("\t@ExcelProperty(value = \"" + remarks.replace(EXCEL_FLAG, "").trim() + "\", converter = LocalDateTimeConverter.class)\r\n");
                    } else {
                        contentBuffer.append("\t@DateTimeFormat(\"yyyy-MM-dd HH:mm:ss\")\r\n");
                        contentBuffer.append("\t@ExcelProperty(\"" + remarks.replace(EXCEL_FLAG, "").trim() + "\")\r\n");
                    }
                } else {
                    contentBuffer.append("\t@ExcelProperty(\"" + remarks.replace(EXCEL_FLAG, "").trim() + "\")\r\n");
                }
            } else {
                contentBuffer.append("\t@ExcelIgnore\r\n");
            }
        }
        //写属性
        contentBuffer.append("\tprivate " + sqlType2JavaType(resultSet.getString("TYPE_NAME")) + " "
                + columnName2AttrName(resultSet.getString("COLUMN_NAME")) + ";\r\n");

        return contentBuffer;
    }

    /**
     * 生成属性setter getter
     *
     * @param contentBuffer
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static StringBuffer addMethod(StringBuffer contentBuffer, ResultSet resultSet) throws SQLException {
        if (contentBuffer == null || resultSet == null) return contentBuffer;
        //属性名
        String attrName = columnName2AttrName(resultSet.getString("COLUMN_NAME"));
        //属性名首字母大写
        String capMethodName = initialCapCommon(attrName);
        //属性类型
        String attrType = sqlType2JavaType(resultSet.getString("TYPE_NAME"));

        contentBuffer.append("\tpublic void set" + capMethodName + "(" + attrType + " " +
                attrName + "){\r\n");
        contentBuffer.append("\t\tthis." + attrName + "=" + attrName + ";\r\n");
        contentBuffer.append("\t}\r\n\r\n");
        contentBuffer.append("\tpublic " + attrType + " get" + capMethodName + "(){\r\n");
        contentBuffer.append("\t\treturn " + attrName + ";\r\n");
        contentBuffer.append("\t}\r\n\r\n");
        return contentBuffer;
    }

    /**
     * 截掉数据库前缀
     *
     * @return
     */
    private static String subPrefix(String tableName) {
        if (prefix == null || prefix.length == 0) {
            return tableName;
        }
        for (String pre : prefix) {
            if (tableName.startsWith(pre)) {
                return tableName.replaceFirst(pre, "");
            }
        }
        return tableName;
    }

    /**
     * 表实体类名
     *
     * @param tableName
     * @return
     */
    private static String getEntityName(String tableName) {
        return initialCap(tableName) + "Entity";
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private static String initialCap(String str) {
        str = subPrefix(str);
        String className = columnName2AttrName(str);
        char[] ch = className.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    private static String colomnName(String str) {
        str = subPrefix(str);
        return columnName2AttrName(str);
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private static String initialCapCommon(String str) {
        String className = columnName2AttrName(str);
        char[] ch = className.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 将数据表字段转化成类的属性名称  （下划线后一字符小写转大写，去掉下划线）
     *
     * @param columnName
     * @return
     */
    private static String columnName2AttrName(String columnName) {
        if (columnName == null) return columnName;
        if (columnName.contains("_")) {
            char[] ch = columnName.toCharArray();
            for (int index = 0; index < ch.length; index++) {
                if (ch[index] == '_' && index > 0 && index < ch.length - 1) {
                    if (ch[index + 1] >= 'a' && ch[index + 1] <= 'z') {
                        ch[index + 1] = (char) (ch[index + 1] - 32);
                    }
                }
            }

            return new String(ch).replace("_", "");
        }
        return columnName;
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    private static String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int")
                || sqlType.equalsIgnoreCase("INT UNSIGNED")
                || sqlType.equalsIgnoreCase("TINYINT UNSIGNED")
                || sqlType.equalsIgnoreCase("SMALLINT UNSIGNED")
                || sqlType.equalsIgnoreCase("MEDIUMINT UNSIGNED")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")
                || sqlType.equalsIgnoreCase("BIGINT UNSIGNED")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("double")
                || sqlType.equalsIgnoreCase("DECIMAL UNSIGNED")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")
                || sqlType.equalsIgnoreCase("json")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")
                || sqlType.equalsIgnoreCase("DATE")) {
            if (generatorProperties.getDateType() == GeneratorProperties.DATEType.Date) {
                return "Date";
            }
            return "LocalDateTime";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }
        System.out.println(sqlType);
        return "";
    }


}
