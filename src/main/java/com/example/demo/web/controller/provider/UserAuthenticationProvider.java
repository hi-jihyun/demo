package com.example.demo.web.controller.provider;

import com.example.demo.web.controller.service.UserAuthDetailService;
import com.example.demo.web.dto.UserAuthDto;
import com.example.demo.web.entity.UserAuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserAuthDetailService userAuthDetailService;

    //private final String loginDelimiter = ":===:";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String userEmail=authentication.getName(); //  combinedUsername  userEmail
        /////////////////////////
        /*System.out.println("컴바인드:"+combinedUsername);
        String[] split = combinedUsername.split(String.valueOf(Character.LINE_SEPARATOR));


        if (split == null || split.length != 2) throw new UsernameNotFoundException("Combined ID: " + combinedUsername);
        String userEmail=split[0];
        String loginUserType=split[1];
        System.out.println("인증프로바이더:"+loginUserType);*/
        /////////////////////////

        String password = (String)authentication.getCredentials();
        //String userType = ((CustomWebAuthenticationDetails)authentication.getDetails()).getLoginUserType();

        System.out.println("인증프로바이더 사용자:"+userEmail);

        UserAuthDetails userAuthDetails=(UserAuthDetails)userAuthDetailService.loadUserByUsername(userEmail);
        if(userAuthDetails==null){
            throw new UsernameNotFoundException(userEmail);
        }

        UserAuthDto uAuthDto=userAuthDetails.getUserAuthDto();
        if(uAuthDto!=null){

            if(!password.equals(uAuthDto.getUserPw())){
                throw new BadCredentialsException("");
            }

            Integer userNo=uAuthDto.getUserNo();

            /*
            if(!sha512PasswordEncoder.matches(password, userAuthDetails.getPassword())){
                throw new BadCredentialsException("BadCredentialsException");
            }
            
            ~~~~~  DB 확인처리
            
            */


            userAuthDetails.getUserAuthDto().setUserPw(null);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                    userAuthDetails.getUserAuthDto(), null, userAuthDetails.getAuthorities());

            //set 사용자detail
            authenticationToken.setDetails(uAuthDto);
            return authenticationToken;
        }else{
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
