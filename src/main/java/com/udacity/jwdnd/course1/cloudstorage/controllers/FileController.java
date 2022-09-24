package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileNameExists;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ModelAndView uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:home");

        try {
            fileService.addFile(file, authentication);

        } catch (IOException E) {

        } catch (FileNameExists e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "File with the same name already exists");
        }
        return new ModelAndView("redirect:/home",model.asMap());
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer fileId) {
        File file = fileService.getStoredFile(fileId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFileName() + "\"").body(file.getFileData());
    }

    @DeleteMapping("/file/{fileId}")
    public String deleteFile(@PathVariable Integer fileId) {
        fileService.deleteFile(fileId);
        return "redirect:/home";
    }
}
