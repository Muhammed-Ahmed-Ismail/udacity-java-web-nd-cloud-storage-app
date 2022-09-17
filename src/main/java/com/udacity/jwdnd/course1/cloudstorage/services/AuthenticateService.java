package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.authutils.HashService;
import models.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticateService  implements AuthenticationProvider {

    private HashService hashService;
    private UserMapper userMapper;

    public AuthenticateService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        User user = userMapper.getUser(userName);
        if(user != null)
        {
            String password = authentication.getCredentials().toString();
            String hashedPassword = hashService.getHashedValue(password,user.getSalt());
            if(user.getPassword().equals(hashedPassword))
            {
                return new UsernamePasswordAuthenticationToken(userName,password,new ArrayList<>());
            }

        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
       return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
