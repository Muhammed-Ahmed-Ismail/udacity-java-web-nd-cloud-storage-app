package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/credential")
    public String postCredentialsForm(@ModelAttribute("credential") Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {

        credentialService.CreateCredential(credential, authentication);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/result";
    }

    @PutMapping("/credential")
    public String updateCredential(@ModelAttribute("credential") Credential credential,RedirectAttributes redirectAttributes) {
        credentialService.updateCredential(credential);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/result";
    }

    @DeleteMapping("/credential/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId,RedirectAttributes redirectAttributes){
        credentialService.deleteCredentials(credentialId);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/result";
    }
}
