package com.service;

import com.entity.FileTask;
import com.exception.PostException;
import com.util.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by libby on 2017/6/14.
 */

@Service
public class TaskService {
    //文件上传下载
    public static final List<FileTask> files = new ArrayList<>();
    public void addLecture(byte[] bytes,String originalFilename) {
        String ext = FilenameUtils.getExtension(originalFilename);
        String baseName = FilenameUtils.getBaseName(originalFilename);
        Path path = Paths.get(baseName + "-" + "BO" + "." + ext);
        FileUtils.copy(bytes, path);
        FileTask fileTask=new FileTask();
        fileTask.setId(files.size()+1);
        fileTask.setFileName(path.toString());
        files.add(fileTask);
    }

    public void addFile(byte[] bytes,String originalFilename) {
        String ext = FilenameUtils.getExtension(originalFilename);
        String baseName = FilenameUtils.getBaseName(originalFilename);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Path path = Paths.get(baseName + "-" + sf.format(new Date()) + "." + ext);
        FileUtils.copy(bytes, path);
    }



    public FileTask getFileTask(int fileId) {
        FileTask fileTask = null;
        for (FileTask ft : files) {
            if (ft.getId() == fileId) {
                fileTask = ft;
            }
        }
        return fileTask;
    }

    public void getExecption(String message) {
        try(InputStream stream = new FileInputStream(message)) {
        } catch (IOException e) {
            throw new PostException("文件读取异常，请重新输入!" + e.getMessage());
        }
    }

}
