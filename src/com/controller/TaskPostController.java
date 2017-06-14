package com.controller;

import com.entity.Admin;
import com.entity.FileTask;
import com.exception.PostException;
import com.exception.PostException;
import com.service.TaskService;
import com.util.FileUtils;
import com.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by libby on 2017/6/14.
 */

@Controller
@RequestMapping("/admin/post")
public class TaskPostController {
    @Autowired
    private TaskService taskService;
    //添加任务
    @ResponseBody
    @RequestMapping(value="/addTask",produces = "application/json; charset=utf-8")
    public String addTask(String taskName, String description, HttpSession session) {
        Admin createAdmin=(Admin)session.getAttribute("user");

        System.out.print("-- "+taskName+"   --"+description+"  --"+"  --"+createAdmin.getUserName());
        return Json.writeStatus(1,"添加成功");
    }

    //文件上传

    @PostMapping("/fileupload")
    public String upload(MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            String fileName = null;
            long fileSize = 0;
            String path = null;
            try {
                fileName = file.getOriginalFilename();
                fileSize = file.getSize();
                // 获取web工程根目录绝对路径
                path = request.getServletContext().getRealPath("/");
                path = path + "WEB-INF\\jsp\\upload";
                File directory = new File(path);
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }
                // 在指定目录下创建文件
                File newFile = new File(directory, file.getOriginalFilename());
                file.transferTo(newFile);
            } catch (IllegalStateException | IOException e) {
                throw new PostException("文件上传错误！" + e.getMessage());
            }
            System.out.println(fileName);
            System.out.println(fileSize);
            System.out.println(path);
            redirectAttributes.addFlashAttribute("fileName", file.getOriginalFilename());
            redirectAttributes.addFlashAttribute("fileSize", fileSize);
            redirectAttributes.addFlashAttribute("path", path);
        }
        return "redirect:/admin/post/fileupload";
    }

    @PostMapping("/fileupload2")
    public String upload(MultipartFile file, RedirectAttributes redirectAttributes)  {
        System.out.print(file.getOriginalFilename());
        if (!file.isEmpty()) {
            try {
                taskService.addFile(file.getBytes(), file.getOriginalFilename());
            } catch (IOException e) {
                throw new PostException("上传文件读取错误！" + e.getMessage());
            }
            redirectAttributes.addFlashAttribute("fileName", file.getOriginalFilename());
            redirectAttributes.addFlashAttribute("fileSize", file.getSize());
        }
        return "redirect:/admin/post/fileupload";
    }
    @GetMapping("/filedownload")
    public void download(Model model) {
        model.addAttribute("files", taskService.files);
    }

    @PostMapping("/lectureupload")
    public String download(MultipartFile file)  {
        if (!file.isEmpty()) {
            try {
                taskService.addLecture(file.getBytes(), file.getOriginalFilename());
            } catch (IOException e) {
                throw new PostException("上传文件读取错误！" + e.getMessage());
            }
        }
        return "redirect:/admin/post/filedownload";
    }

    @GetMapping("/lecture/{fileid}")
    public ResponseEntity<byte[]> download(@PathVariable int fileid) {
        FileTask fileTask = taskService.getFileTask(fileid);
        Path path = Paths.get(fileTask.getFileName());
        return FileUtils.toResponseEntity(path);
    }
}
