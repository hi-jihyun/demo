package com.example.demo.web.controller.handler;


import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException accessException) throws IOException, ServletException {

        String errorMsg="";
        System.out.println("예외:"+accessException.getMessage());

        if (accessException instanceof AuthenticationServiceException) {
            request.setAttribute("error", "존재하지 않는 사용자입니다.");

        } else if(accessException instanceof BadCredentialsException) {
            request.setAttribute("error", "비밀번호가 틀립니다.");

        } else if(accessException instanceof LockedException) {
            //String strFailCnt=accessException.getMessage();
            request.setAttribute("error", "잠긴 계정입니다..");

        } else if(accessException instanceof DisabledException) {
            request.setAttribute("error", "비활성화 계정입니다..");

        } else if(accessException instanceof AccountExpiredException) {
            request.setAttribute("error", "만료된 계정입니다..");

        } else if(accessException instanceof CredentialsExpiredException) {
            request.setAttribute("error", "비밀번호가 만료되었습니다.");
        } else if(accessException instanceof UsernameNotFoundException){
            request.setAttribute("error", "아이디를 확인해 주세요.");
        }


        request.setAttribute("exception", accessException);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);


        //response.sendRedirect("/login?err="+ URLEncoder.encode(errorMsg));

    }

}
