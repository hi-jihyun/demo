package com.example.demo.web.controller.service;


import com.example.demo.web.dto.UserAuthDto;
import com.example.demo.web.entity.UserAuthDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userAuthDetailService")
@Getter
@RequiredArgsConstructor
public class UserAuthDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String userId) throws AuthenticationException {


        if(!userId.equals("testuser")){
            throw new UsernameNotFoundException(userId);
        }


        UserAuthDto userAuthDto= new UserAuthDto(1, "testuser", "1234", "C",
                "테스터");


        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));


        UserAuthDetails userAuthDetails = new UserAuthDetails(userAuthDto, roles);
        return userAuthDetails;
    }


}
