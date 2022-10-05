package com.example.demo.web.controller.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID=-5138870746127783L;
    private final String userType;

    public UserUsernamePasswordAuthenticationToken(String principal, String credentials, String userType){
        super(principal, credentials);

        this.userType=userType;
    }

    public UserUsernamePasswordAuthenticationToken(UserDetails principal, String credentials, String userType,
                                                   Collection<? extends GrantedAuthority> authorities){
        super(principal, credentials, authorities);
        System.out.println("커스텀토큰:"+principal+" loginUserType:"+userType);
        this.userType=userType;

    }

    public String getUserType(){
        return userType;
    }
}

