package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String postNoteForm(@ModelAttribute("note") Note note , Authentication authentication, RedirectAttributes redirectAttributes){
        noteService.createNote(note,authentication);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/result";
    }
    @DeleteMapping("/note/{noteId}")
    public String deleteNote(@PathVariable Integer noteId,RedirectAttributes redirectAttributes){
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/result";
    }
    @PutMapping("/note")
    public String updateNote(@ModelAttribute("note") Note note,RedirectAttributes redirectAttributes)
    {
        noteService.UpdateNote(note);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/result";
    }
}
