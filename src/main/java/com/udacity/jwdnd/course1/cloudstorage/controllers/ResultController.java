package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/result")
public class ResultController {
    @GetMapping
    public String getResultPage(RedirectAttributes redirectAttributes)
    {

        return "result";
    }
}
