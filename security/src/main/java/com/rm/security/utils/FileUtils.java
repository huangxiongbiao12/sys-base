package com.rm.security.utils;

import com.rm.security.web.response.ResponseEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 文件工具类
 *
 * @author Peter
 */
@Slf4j
public class FileUtils {
    /**
     * 传入文件夹路径，该方法能够实现创建整个路径
     *
     * @param path 文件夹路径，不包含文件名称及后缀名
     */
    public static void isDir(String path) {
        String[] paths = path.split("/");
        String filePath = "";
        for (int i = 0; i < paths.length; i++) {
            if (i == 0) {
                filePath = paths[0];
            } else {
                filePath += "/" + paths[i];
            }
            creatDir(filePath);
        }
    }

    /**
     * 该方法用来判断文件夹是否存在，如果不存在则创建，存在则什么都不做
     *
     * @param filePath
     */
    public static void creatDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * 保存文件
     *
     * @param path
     * @param inputStream
     * @return
     */
    public static void saveFile(String path, InputStream inputStream) {
        OutputStream os = null;
        InputStream is = null;
        try {
            File saveFile = new File(path);
            if (!saveFile.exists()) {
                File pFile = saveFile.getParentFile();
                if (!pFile.exists()) {
                    pFile.mkdirs();
                }
                saveFile.createNewFile();
            }
            //获取输出流
            os = new FileOutputStream(saveFile);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            is = inputStream;
            int temp;
            //一个一个字节的读取并写入
            while ((temp = is.read()) != (-1)) {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();

        } catch (Exception e) {
            log.error("上传文件失败", e);
            ResponseEnum.SYSTEM_ERROR.throwEx("文件上传失败!", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
