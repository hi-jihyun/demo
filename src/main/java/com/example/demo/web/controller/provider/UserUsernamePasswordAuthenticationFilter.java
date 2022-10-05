package com.example.demo.web.controller.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Getter
@Setter
//@Component
public class UserUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter{ //extends UsernamePasswordAuthenticationFilter


    //
    /*public UserUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager){
        //this.authenticationManager=authenticationManager;
    }*/
    public UserUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println("커스텀필터:");

        if(!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported:"+request.getMethod());
        }

        // String username = obtainUsername(request);
        // String password = obtainPassword(request);

        //////////////////
        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);

        return this.getAuthenticationManager()
                .authenticate(authRequest);
        ///////////////////
        /*
        String username=obtainUsername(request);
        String password=obtainPassword(request);
        //로그인 추가된 파라미터
        String userType=request.getParameter("loginUserType");

        UserUsernamePasswordAuthenticationToken authRequest = new UserUsernamePasswordAuthenticationToken(username, password, userType);
        return this.authenticationManager.authenticate(authRequest);
        */
    }


    private UsernamePasswordAuthenticationToken getAuthRequest( HttpServletRequest request) {

        String username=request.getParameter("userId");
        String password=request.getParameter("password");
        String userType=request.getParameter("loginUserType");
        System.out.println("커스텀필터:"+username+" loginUserType:"+userType);


        String usernameCombined = String.format("%s%s%s", username.trim(),
                String.valueOf(Character.LINE_SEPARATOR), userType);
        return new UsernamePasswordAuthenticationToken(
                usernameCombined, password);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}

