package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public void addFile(MultipartFile file, Authentication authentication) throws AuthenticationException, IOException {
        File fileDto = new File();
        String loggedInUserName = authentication.getName();
        User loggedInUser = userMapper.getUser(loggedInUserName);
        if (loggedInUser == null)
            throw new AuthenticationCredentialsNotFoundException("");
        fileDto.setFileName(file.getOriginalFilename());
        fileDto.setFileData(file.getBytes());
        fileDto.setContentType(file.getContentType());
        fileDto.setFileSize(Long.toString(file.getSize()));
        fileDto.setUserId(loggedInUser.getUserId());
        fileMapper.storeFile(fileDto);
    }

    public List<String> getStoredFilesNames(Authentication authentication) throws AuthenticationException {
        String loggedInUserName = authentication.getName();
        User loggedInUser = userMapper.getUser(loggedInUserName);
        if (loggedInUser == null)
            throw new AuthenticationCredentialsNotFoundException("");
        return fileMapper.getStoredFileNames(loggedInUser.getUserId());
    }

    public List<Integer> getStoredFilesIds(Authentication authentication) throws AuthenticationException {
        String loggedInUserName = authentication.getName();
        User loggedInUser = userMapper.getUser(loggedInUserName);
        if (loggedInUser == null)
            throw new AuthenticationCredentialsNotFoundException("");
        return fileMapper.getStoredFilesId(loggedInUser.getUserId());
    }

    public File getStoredFile(int fileId) {

        return fileMapper.getFileData(fileId);
    }

    public void deleteFile(int fileId) {
        fileMapper.deleteFile(fileId);
    }
}
