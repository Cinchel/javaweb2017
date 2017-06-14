package com.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.exception.PostException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

/**
 * Created by libby on 2017/6/14.
 */
public class FileUtils {

    private static final String ROOT = setRootDirectory();
    private static String setRootDirectory() {
        String webapp = System.getProperty("SpringMVCExamples.root");
        String uploadDirectory = webapp + "\\WEB-INF\\jsp\\upload\\";
        Path path = Paths.get(uploadDirectory);
        // 判断路径是否为目录
        if (!Files.isDirectory(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new PostException("创建上传文件目录错误！" + e.getMessage());
            }
        }
        return uploadDirectory;
    }

    public static void copy(byte[] bytes, Path path) {
        try {
            path = getRealPath(path);
            System.out.println(path);
            Files.write(path, bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new PostException("文件写入错误！" + e.getMessage());
        }
    }

    private static Path getRealPath(Path path) {
        return Paths.get(ROOT + path);
    }

    public static ResponseEntity<byte[]> toResponseEntity(Path path) {
        ResponseEntity<byte[]> entity = null;
        try {
            File file = getRealPath(path).toFile();
            // 声明附件名称
            String fileName = URLEncoder.encode(file.getName(), "UTF-8");
            HttpHeaders headers = new HttpHeaders();
            // 声明响应类型为流
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 声明返回文件名称
            headers.setContentDispositionFormData("attachment", fileName);
            // 创建封装文件二进制字节数组、HTTP状态码、HTTP头的响应实体
            entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
            return entity;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new PostException("文件加载错误！" + e.getMessage());
        }
    }
}
