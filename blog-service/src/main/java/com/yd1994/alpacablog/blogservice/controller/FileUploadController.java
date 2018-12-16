package com.yd1994.alpacablog.blogservice.controller;

import com.yd1994.alpacablog.blogservice.dto.FileInfo;
import com.yd1994.alpacablog.blogservice.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Random;

@RestController
@RequestMapping("/images")
public class FileUploadController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${system.file.filePath.image}")
    private String filePath;

    @Autowired
    private IFileService fileService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) throws Exception {
        FileInfo fileInfo = this.fileService.get(id, 0);
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + fileInfo.getPath()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> uploadImg(@RequestParam("image") MultipartFile file, Principal principal) throws Exception {
        if (file.isEmpty()) {
            ResponseEntity.badRequest().body("未发现文件");
        }
        String basePath = this.filePath;
        String path = null;
        String name = null;
        if (StringUtils.isEmpty(basePath)) {
            log.error("文件上传失败：upload filePath is Empty, can not save file.");
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统错误：请联系管理员");
        }
        if (basePath.lastIndexOf('/') != basePath.length() - 1) {
            basePath += '/';
        }
        try {
            // 图片文件类型
            String contentType = file.getContentType();
            // 图片名字
            String fileName = file.getOriginalFilename();

            name = fileName + "." + contentType;

            String time = String.valueOf(System.currentTimeMillis());
            String randomStr = String.valueOf(new Random().nextInt(1000));

            path = basePath + time + randomStr + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()){
                // 新建文件夹
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);// 文件写入

        } catch (IOException e) {
            log.error("文件上传失败：" + e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统错误：请联系管理员");
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(name);
        fileInfo.setPath(path);
        fileInfo.setType(0);
        this.fileService.add(fileInfo, principal.getName());

        URI location = null;
        try {
            location = new URI("/images/" + fileInfo.getId());
        } catch (URISyntaxException e) {
            log.error("文件上传失败：创建uri出错。" + e.toString());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统错误：请联系管理员");
        }
        return ResponseEntity.created(location).build();
    }

}
