package com.udacity.jwdnd.course1.cloudstorage.controllers.auth;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getSignupPage(@ModelAttribute("user")User user, Model model)
    {
        model.addAttribute("signupError",false);
        model.addAttribute("signupSuccess",false);
        return "signup";
    }
//TODO change redirecting only when user is created successfully
    @PostMapping()
    public String postForm(@ModelAttribute("user")User user, Model model, RedirectAttributes redirectAttributes)
    {
        if(userService.isUserNameAvailable(user))
        {
            model.addAttribute("signupError",true);
            model.addAttribute("signupSuccess",false);
           return "signup";
        } else
        {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("signupError",false);
            redirectAttributes.addFlashAttribute("signupSuccess",true);
            redirectAttributes.addFlashAttribute("successMessage",true);
            return "redirect:/login";
        }
    }
}
