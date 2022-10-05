package com.example.demo.web.entity;


import com.example.demo.web.dto.UserAuthDto;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@ToString
public class UserAuthDetails extends User{

    private final UserAuthDto userAuthDto;

    public UserAuthDetails(UserAuthDto userAuthDto, Collection<? extends GrantedAuthority> authorities){
        super(userAuthDto.getUserId(), userAuthDto.getUserPw(), authorities);
        //enabled, accountNonExpired, credentilsNonExpired, accountNonLocked
        //true, true, true, true set
        this.userAuthDto=userAuthDto;
    }

}
