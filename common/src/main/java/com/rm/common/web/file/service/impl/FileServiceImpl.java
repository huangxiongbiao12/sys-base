package com.rm.common.web.file.service.impl;


import com.rm.common.utils.DateUtils;
import com.rm.common.web.file.service.FileService;
import com.rm.common.web.response.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private String basePath;

    @Override
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String rPath = getFilePath(fileName);
        String absolutePath = basePath + rPath;
        OutputStream os = null;
        InputStream is = null;
        try {
            File saveFile = new File(absolutePath);
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
            is = file.getInputStream();
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
        return rPath;
    }

    /**
     * 日期加随机数
     *
     * @param fileName
     * @return
     */
    private String getFilePath(String fileName) {
        String date = DateUtils.format(LocalDateTime.now(), "yyyyMMdd");
        return "/" + date + "/" + UUID.randomUUID() + "." + fileName;
    }

}
