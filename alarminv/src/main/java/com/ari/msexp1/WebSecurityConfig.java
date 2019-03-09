package com.ari.msexp1;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // enforce HTTPS - disabled for now
        http.requiresChannel().antMatchers("/login*").requiresSecure();
        http.requiresChannel().anyRequest().requiresInsecure();
        // prevent CSRF
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // install content security policy to prevent cross site scripting
        // http.headers().contentSecurityPolicy("default-src script-src 'self' <url>; object-src <url>; report-uri <url>");

        //  authenticate and authorize
        http.authorizeRequests()
                .antMatchers("/", "/alarminventory", "/v2/api-docs", "/swagger*/**")
                .permitAll()
                .anyRequest()
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // for now - do not authenticate alarminventory REST and swagger
        web.ignoring().antMatchers("/", "/alarminventory/*/**", "/v2/api-docs", "swagger*", "/swagger*/**");
    }

}