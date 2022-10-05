package com.example.demo.config;

import com.example.demo.web.controller.handler.UserAuthenticationFailureHandler;
import com.example.demo.web.controller.handler.UserAuthenticationSuccessHandler;
import com.example.demo.web.controller.provider.UserAuthenticationProvider;
import com.example.demo.web.controller.provider.UserUsernamePasswordAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity//(debug=true)
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
public class SpringSecurityConfig {

    public static final String[] SECURITY_EXCLUDE_PATTERN_ARR = {
            // resource
            "/error/**"
            ,"/favicon.ico"
            ,"/resources/**"
            ,"/js/**"
            ,"/lib/**"
            ,"/img/**"
            ,"/css/**"
            ,"/font/**"
    };

    /*
    @Order(1)
    @Configuration
    public static class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
            ~~~ 생략
    }*/

    //@Order(2)
    @Configuration
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

        public UserSecurityConfig(){ super();}


        public UserUsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {

            UserUsernamePasswordAuthenticationFilter filter =
                    new UserUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));

            filter.setAuthenticationManager(authenticationManagerBean());
            return filter;
        }


        @Override
        public void configure(WebSecurity web) {
            web
                    .ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .antMatchers(SECURITY_EXCLUDE_PATTERN_ARR);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests(request -> {
                        request
                                //.antMatchers("/**").permitAll();
                            .antMatchers("/cart/**", "/logout").hasRole("USER")
                            .anyRequest().permitAll();
                    })
                    .formLogin(login -> {
                        login
                                .loginPage("/login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login?loginFail=true")
                                .usernameParameter("userId")
                                .passwordParameter("password")
                                .loginProcessingUrl("/loginProcess")
                                .successHandler(new UserAuthenticationSuccessHandler())
                                .failureHandler(new UserAuthenticationFailureHandler())
                                .permitAll();
                    })
                    .logout(logout -> {
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID");
                    })
                    .csrf().disable();

        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(userAuthenticationProvider());
        }

        @Bean
        public AuthenticationProvider userAuthenticationProvider(){
            return new UserAuthenticationProvider();
        }
    }
}
