package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private  NoteMapper noteMapper;
    private  UserMapper userMapper;


    public NoteService(NoteMapper noteMapper,UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public List<Note> getNotesByUserId(Authentication authentication) {
        String loggedInUserName =  authentication.getName();
        User loggedInUser= userMapper.getUser(loggedInUserName);
        if(loggedInUser != null)
            return noteMapper.getNotesByUserId(loggedInUser.getUserId());
        else
            return new ArrayList<Note>();
    }

    public Note getNoteById(int noteId) {
        return noteMapper.getNoteById(noteId);
    }


    public Note createNote(Note note, Authentication authentication) {
        String loggedInUserName =  authentication.getName();
        User loggedInUser= userMapper.getUser(loggedInUserName);
        note.setUserId(loggedInUser.getUserId());
        noteMapper.insertNote(note);
        return note;
    }

    public void UpdateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(int noteId) {
        noteMapper.deleteNote(noteId);
    }
}
