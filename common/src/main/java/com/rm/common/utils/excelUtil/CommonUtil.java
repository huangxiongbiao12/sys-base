package com.rm.common.utils.excelUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/4/18 0018.
 */
public class CommonUtil {

    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static <T> void excelExport(HttpServletResponse response, Map<String, String> filedName, String tmpPath, Collection<T> list, String sheetName) {
        FileInputStream is = null;
        OutputStream out = null;
        OutputStream os = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
            String cDate = formatter.format(new Date());
            String uuid = CommonUtil.randomUUID();
            String path = tmpPath + "//" + cDate + "//" + uuid;
            File filePath = new File(path);
            // 目录不存在的情况下，创建目录。
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            String fileName = sheetName + "_" + cDate + ".xls";
            out = new FileOutputStream(filePath + "/" + fileName);
            ExcelUtil.exportExcel(filedName, list, out, sheetName);
            //附件下载导出
            File f = new File(filePath + "/" + fileName);
            if (f.exists()) {
                response.setContentType("application/octet-stream;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));//此处需要设置下载文件的默认名称,只有设置了请求头才能弹出下载框
                is = new FileInputStream(f);
                os = response.getOutputStream();
                byte[] bt = new byte[1024 * 10];
                int len = -1;
                while ((len = is.read(bt)) != -1) {
                    os.write(bt, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导入Excel
     *
     * @return
     * @throws
     * @author liuwei
     * @date 2019/1/16 13:43
     */
    public static String validateExcel(MultipartFile excel, String[] header, String[] columns, Map<String, Object> cellTypeMap) throws Exception {
        Workbook workbook = ExcelUtil.createWorkbook(excel.getInputStream(), excel.getOriginalFilename());
        //第一个sheet页
        Sheet sheetAt = workbook.getSheetAt(0);
        //总行数
        int rows = sheetAt.getPhysicalNumberOfRows();
        //表格第一行即表头所在行
        Row row = sheetAt.getRow(0);
        short cellNum = row.getLastCellNum();
        //校验表头列数即字段数
        if (cellNum != header.length) {
            return "上传的Excel表头列数与模板不匹配（期望列数：" + header.length + "，实际列数：" + cellNum + "）";
        }
        //校验表头列是否与模板一致
        for (int i = 0; i < cellNum; i++) {
            String cell = row.getCell(i).getStringCellValue();
            if (!StringUtils.equals(cell, header[i])) {
                return "上传的Excel表头字段【" + cell + "】与模板不匹配（期望：" + header[i] + "，实际：" + cell + "）";
            }
        }
        //校验单元格数据格式
        StringBuilder errorInfo = new StringBuilder();
        for (int i = 1; i < rows; i++) {
            Row r = sheetAt.getRow(i);
            //校验是否整行为空
            int blankNum = 0;
            for (int j = 0; j < cellNum; j++) {
                Cell cell = r.getCell(j);
                if (cell == null || (cell.getCellTypeEnum().compareTo(CellType.BLANK) == 0)) {
                    blankNum++;
                }
            }
            if (blankNum >= cellNum) {
                //说明整行都为空，直接跳过不解析
                continue;
            }
            for (int j = 0; j < cellNum; j++) {
                CellType expectedType = (CellType) cellTypeMap.get(columns[j]);
                Cell cell = r.getCell(j);
                CellType actualType;
                if (cell != null) {
                    actualType = cell.getCellTypeEnum();
//                    System.out.println("第"+(i+1)+"行第"+(j+1)+"列 -> " + cell.toString() + "," + actualType.name());
                } else {
                    actualType = CellType.BLANK;
//                    System.out.println("第"+(i+1)+"行第"+(j+1)+"列 -> " + ""+","+actualType.name());
                }
                //compareTo:类型匹配成功结果为0，匹配失败为-1
                if (actualType.compareTo(expectedType) < 0) {
                    String expectedFormat = ExcelCellType.getDesc(expectedType.name());
                    String actualFormat = ExcelCellType.getDesc(actualType.name());
                    String message = "格式不正确（期望：" + expectedFormat + "，实际：" + actualFormat + "）";
                    appendErrorInfo(errorInfo, i + 1, j + 1, row.getCell(j).getStringCellValue(),
                            message, "，");
                }
            }
        }
        if (StringUtils.isNotEmpty(errorInfo)) {
            return errorInfo.toString().substring(0, errorInfo.length() - 1);
        }

        return null;
    }

    /**
     * 解析Excel表格数据
     *
     * @return
     * @throws
     * @author liuwei
     * @date 2019/1/16 16:35
     */
    public static List<Map<String, Object>> parseExcelData(MultipartFile excel, String[] columns) throws Exception {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Workbook workbook = ExcelUtil.createWorkbook(excel.getInputStream(), excel.getOriginalFilename());
        //获取第一个sheet页
        Sheet sheetAt = workbook.getSheetAt(0);
        //得到总行数
        int rows = sheetAt.getPhysicalNumberOfRows();
        //数据内容从Excel表格第二行开始
        for (int i = 1; i < rows; i++) {
            //当前行
            Row r = sheetAt.getRow(i);
            short cellNum = r.getLastCellNum();
            //校验是否整行为空
            int blankNum = 0;
            for (int j = 0; j < cellNum; j++) {
                Cell cell = r.getCell(j);
                if (cell == null || (cell.getCellTypeEnum().compareTo(CellType.BLANK) == 0)) {
                    blankNum++;
                }
            }
            if (blankNum >= cellNum) {
                //说明整行都为空，直接跳过不解析
                continue;
            }
            Map<String, Object> data = new LinkedHashMap<>();
            for (int j = 0; j < columns.length; j++) {
                String fieldName = columns[j];
                Cell cell = r.getCell(j);
                String cellContent = "";
                if (cell != null) {
                    cellContent = cell.toString();
                    if (cell.getCellTypeEnum().compareTo(CellType.NUMERIC) == 0) {
                        Boolean isCellDateFormatted = HSSFDateUtil.isCellDateFormatted(cell);
                        if (isCellDateFormatted) {
                            cellContent = DateFormatUtils.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), "yyyy-MM-dd HH:mm:ss");
                        }
                    }
                }
//                System.out.println(fieldName + " -> " + cellContent);
                data.put(fieldName, cellContent);
            }
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 拼接错误提示信息
     *
     * @return
     * @throws
     * @author liuwei
     * @date 2019/1/16 12:01
     */
    public static void appendErrorInfo(StringBuilder errorInfo, Integer rowNum, Integer colNum, String headerName, String message, String comma) {
        errorInfo.append("第" + rowNum + "行第" + colNum + "列" + "【" + headerName + "】" + message + comma);
    }

    /**
     * 导出表格直接下载不备份
     **/
    public static <T> void excelExportNoBackup(HttpServletResponse response, Map<String, String> filedName, String tmpPath, Collection<T> list, String sheetName) {
        FileInputStream is = null;
        OutputStream out = null;
        OutputStream os = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
            String cDate = formatter.format(new Date());
            String uuid = CommonUtil.randomUUID();
            String path = tmpPath + "//" + cDate + "//" + uuid;
            String fileName = sheetName + "_" + cDate + ".xls";

            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));//此处需要设置下载文件的默认名称,只有设置了请求头才能弹出下载框
            ExcelUtil.exportExcel(filedName, list, response.getOutputStream(), sheetName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (response.getOutputStream() != null) {
                    response.getOutputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
