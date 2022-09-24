package com.udacity.jwdnd.course1.cloudstorage.controllers.serverutils;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileNameExists;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.FileAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ModelAndView handelExceedIUploadedFileSize(MaxUploadSizeExceededException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("error", true);
        modelAndView.addObject("errorMessage", "File is too large Max allowable size is 1MB");
        return modelAndView;
    }

//    @ExceptionHandler(value = FileNameExists.class)
//    public ModelAndView handelFileAlreadyExists(FileNameExists e, ModelMap model) {
//        model.addAttribute("error", true);
//        model.addAttribute("errorMessage", "File with the same name already exists");
//        return new ModelAndView("redirect:/home", model);
//    }

}

