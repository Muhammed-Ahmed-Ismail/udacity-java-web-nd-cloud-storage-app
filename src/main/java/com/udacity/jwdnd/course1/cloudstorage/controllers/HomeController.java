package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private  NoteService noteService;
    public HomeController(NoteService noteService){
        this.noteService = noteService;
    }
    @GetMapping()
    public String getHomePage(Model model, Authentication authentication,
                              @ModelAttribute("note") Note note)
    {
        model.addAttribute("notes",noteService.getNotesByUserId(authentication));
        return "home";
    }
}
