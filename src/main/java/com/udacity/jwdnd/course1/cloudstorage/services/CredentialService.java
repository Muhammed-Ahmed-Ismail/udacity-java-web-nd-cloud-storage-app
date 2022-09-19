package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.authutils.EncryptionService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private UserMapper userMapper;
    private CredentialMapper credentialMapper;

    private EncryptionService encryptionService;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper,EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void CreateCredential(Credential credential, Authentication authentication) throws AuthenticationException {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        String loggedInUserName = authentication.getName();
        User loggedInUser = userMapper.getUser(loggedInUserName);
        if(loggedInUser == null)
            throw new AuthenticationCredentialsNotFoundException("");
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        credential.setUserId(loggedInUser.getUserId());
        credentialMapper.insertCredential(credential);
    }

    public List<Credential> getEncryptCredentialsByUserId(Authentication authentication) throws AuthenticationException  {
        String loggedInUserName = authentication.getName();
        User loggedInUser = userMapper.getUser(loggedInUserName);
        if(loggedInUser == null)
            throw new AuthenticationCredentialsNotFoundException("");
        return credentialMapper.getCredentialsByUserId(loggedInUser.getUserId());
    }
    public List<Credential> getDecryptCredentialsByUserId(Authentication authentication) throws AuthenticationException  {
        String loggedInUserName = authentication.getName();
        User loggedInUser = userMapper.getUser(loggedInUserName);
        if(loggedInUser == null)
            throw new AuthenticationCredentialsNotFoundException("");
        return credentialMapper.getCredentialsByUserId(loggedInUser.getUserId()).stream().map(credential -> {
            credential.setPassword(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
            return credential;
        }).collect(Collectors.toList());
    }

    public void updateCredential(Credential credential)
    {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialMapper.updateCredential(credential);
    }


}
