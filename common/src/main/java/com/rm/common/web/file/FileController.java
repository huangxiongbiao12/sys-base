package com.rm.common.web.file;

import com.rm.common.web.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    FileService fileService;


    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

}
