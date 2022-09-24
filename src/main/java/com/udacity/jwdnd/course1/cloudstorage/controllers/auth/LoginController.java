package com.udacity.jwdnd.course1.cloudstorage.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping()
    public String getLoginPage(Model model, RedirectAttributes redirectAttributes) {

        return "login";
    }
}
