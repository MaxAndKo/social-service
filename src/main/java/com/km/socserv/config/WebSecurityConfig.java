package com.km.socserv.config;

import com.km.socserv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/new").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/operator").hasRole("OPERATOR")
                .anyRequest().authenticated();
        httpSecurity
                .formLogin()
                .defaultSuccessUrl("/check_role")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Autowired
        UserService userService;
        @Autowired
        BCryptPasswordEncoder bCryptPasswordEncoder;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {

            auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        }

    }

}
