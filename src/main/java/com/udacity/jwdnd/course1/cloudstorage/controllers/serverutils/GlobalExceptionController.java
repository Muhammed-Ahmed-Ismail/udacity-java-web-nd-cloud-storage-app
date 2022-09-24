package com.udacity.jwdnd.course1.cloudstorage.controllers.serverutils;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileNameExists;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public String handelExceedIUploadedFileSize(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "File is too large Max allowable size is 1MB");
        return "redirect:/result";
    }

    @ExceptionHandler(value = FileNameExists.class)
    public String handelFileAlreadyExists(FileNameExists e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "File with the same name already exists");
        return "redirect:/result";
    }
    @ExceptionHandler(value = IOException.class)
    public String handleIOException( RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "File with the same name already exists");
        return "redirect:/result";
    }

}

