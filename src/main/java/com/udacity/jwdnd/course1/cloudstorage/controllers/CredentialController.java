package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/credential")
    public String postCredentialsForm(@ModelAttribute("credential")Credential credential,Authentication authentication)
    {
        credentialService.CreateCredential(credential,authentication);
        return "redirect:/home";
    }
    @PutMapping("/credential")
    public String updateCredential(@ModelAttribute("credential")Credential credential){
        credentialService.updateCredential(credential);
        return "redirect:/home";
    }
}
