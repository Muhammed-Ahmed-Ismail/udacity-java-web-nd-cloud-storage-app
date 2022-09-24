package com.udacity.jwdnd.course1.cloudstorage.controllers.auth;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String postForm(@ModelAttribute("user")User user, Model model)
    {
        if(userService.isUserNameAvailable(user))
        {
            model.addAttribute("signupError",true);
            model.addAttribute("signupSuccess",false);

        } else
        {
            userService.createUser(user);
            model.addAttribute("signupError",false);
            model.addAttribute("signupSuccess",true);
        }
        return "redirect:/login";
    }
}
