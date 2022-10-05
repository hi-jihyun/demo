package com.example.demo.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @RequestMapping("/")
    public String showMain(HttpSession session, ModelMap model){

        return "home/home";
    }

    @RequestMapping("/login")
    public String goLogin(HttpServletRequest request, HttpServletResponse response,
                          HttpSession session, ModelMap model) throws ServletException, IOException {

        String err=(String)request.getAttribute("error");
        AuthenticationException accessException = (AuthenticationException)request.getAttribute("exception");


        if(!StringUtils.isEmpty(err)) {
            model.addAttribute("error", err);
        }

        return "account/login";
    }

    @PostMapping("/loginProcess")
    public String accountLogin(HttpSession session, ModelMap model) {

        System.out.println("로그인 프로세스 : ");
        return "account/login";
    }

    @RequestMapping("/cart")
    public String showCart(HttpSession session, ModelMap model){


        return "cart/cart";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";

    }


}
