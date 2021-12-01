package com.rm.common.web.file.service.impl;


import com.rm.common.jooq.SnowflakeIdWorker;
import com.rm.common.utils.DateUtils;
import com.rm.common.utils.FileUtils;
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
        try {
            File saveFile = new File(absolutePath);
            if (!saveFile.exists()) {
                File pFile = saveFile.getParentFile();
                if (!pFile.exists()) {
                    pFile.mkdirs();
                }
                saveFile.createNewFile();
            }
            file.transferTo(saveFile);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            ResponseEnum.SYSTEM_ERROR.throwEx("文件上传失败!", e);
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
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return "/" + date + "/" + SnowflakeIdWorker.generateId() + suffix;
    }

}
