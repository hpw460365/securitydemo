package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.annotation.web.configurers.PasswordManagementConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userDetailsService;
    //安全过滤器链
        //认证管理器
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return  http.authorizeRequests()
                .antMatchers("/css/**", "/login.html","/login").permitAll()   //请求不用认证
                .anyRequest().authenticated()    //请求需要认证
                .and()
                .formLogin().loginProcessingUrl("/login").successForwardUrl("/success").passwordParameter("username").passwordParameter("password").successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        HttpSession session = request.getSession();


                        System.out.println("denglu success" + ",session.getId()="+session.getId()+",session.getCreationTime()="+new Date(session.getCreationTime()));
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("denglu fail");
                    }
                })
                .and()
                .apply(new DefaultLoginPageConfigurer<>())
                .and()
                .sessionManagement()
                .and()
                .csrf().disable()
                .build();
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }



    //安全自定义器
}
