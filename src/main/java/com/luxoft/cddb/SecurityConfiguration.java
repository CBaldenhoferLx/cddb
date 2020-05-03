package com.luxoft.cddb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.luxoft.cddb.views.Views;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	public static final String LOGIN_PROCESSING_URL = "/login";
    public static final String LOGIN_FAILURE_URL = "/login?error";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGOUT_SUCCESS_URL = "/logoutSuccess";
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  
            .requestCache().requestCache(new CustomRequestCache()) 
            .and().authorizeRequests() 
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
            .and().authorizeRequests()
            .antMatchers("/oauth2/authorization/google").permitAll()

            .anyRequest().authenticated() 

            .and().formLogin()
            .loginPage(LOGIN_URL).defaultSuccessUrl(Views.withSlash(Views.DASHBOARD_VIEW)).permitAll()
            .loginProcessingUrl(LOGIN_PROCESSING_URL)  
            .failureUrl(LOGIN_FAILURE_URL)
            .and().logout().logoutSuccessUrl(Views.withSlash(Views.LOGOUT_SUCCESS))//.logoutUrl(LOGOUT_URL)
            .and().oauth2Login().loginPage("/oauth2/authorization/google").defaultSuccessUrl(Views.withSlash(Views.LOGIN_SUCCESS), true).failureUrl(LOGIN_FAILURE_URL); 
    }
    
    /*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
            User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }*/
    
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest",
            "/sw.js",
            "/offline.html",
            "/icons/**",
            "/images/**",
            "/styles/**",
            "/h2-console/**");
    }

}