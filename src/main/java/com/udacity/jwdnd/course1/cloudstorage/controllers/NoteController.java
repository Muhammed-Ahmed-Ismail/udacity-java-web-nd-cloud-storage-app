package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @DeleteMapping("/note/{noteId}")
    public String deleteNote(@PathVariable Integer noteId){
        noteService.deleteNote(noteId);
        return "redirect:/home";
    }
    @PostMapping("/note")
    public String postNoteForm(@ModelAttribute("note") Note note , Authentication authentication){
        noteService.createNote(note,authentication);
        return "redirect:/home";
    }
}
