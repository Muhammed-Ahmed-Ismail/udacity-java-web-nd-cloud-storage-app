package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileNameExists;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private CredentialService credentialService;

    private FileService fileService;

    public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String getHomePage(Model model, Authentication authentication,
                              @ModelAttribute("note") Note note,
                              @ModelAttribute("credential") Credential credential,
                              RedirectAttributes redirectAttributes) {
        List<List<Credential>> credentials = new ArrayList<>();
        credentials.add(credentialService.getEncryptCredentialsByUserId(authentication));
        credentials.add(credentialService.getDecryptCredentialsByUserId(authentication));
        List<String> filesNames = fileService.getStoredFilesNames(authentication);
        List<Integer> filesIds = fileService.getStoredFilesIds(authentication);
        model.addAttribute("notes", noteService.getNotesByUserId(authentication));
        model.addAttribute("credentials", credentials);
        model.addAttribute("filesNames", filesNames);
        model.addAttribute("fileIds", filesIds);
        return "home";
    }
}
