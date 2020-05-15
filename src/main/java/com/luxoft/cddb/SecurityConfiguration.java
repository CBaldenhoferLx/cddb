package com.luxoft.cddb;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.luxoft.cddb.beans.user.UserBean;
import com.luxoft.cddb.services.IUserService;
import com.luxoft.cddb.views.Views;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final String LOGIN_PROCESSING_URL = "/login";
    public static final String LOGIN_FAILURE_URL = "/login?error";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGOUT_SUCCESS_URL = "/logoutSuccess";
    
    @Autowired
    private IUserService userService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    @Bean
    protected LogoutSuccessHandler appLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
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
            .and().logout().logoutSuccessUrl(LOGIN_URL).logoutSuccessHandler(appLogoutSuccessHandler()).logoutUrl(LOGOUT_URL)//.addLogoutHandler(appLogoutHandler())
            .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).and() 
            .and().oauth2Login().loginPage("/oauth2/authorization/google").defaultSuccessUrl(Views.withSlash(Views.LOGIN_SUCCESS), true).failureUrl(LOGIN_FAILURE_URL).userInfoEndpoint().oidcUserService(this.oidcUserService());
    }
    
	private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
		final OidcUserService delegate = new OidcUserService();

		return (userRequest) -> {
			// Delegate to the default implementation for loading a user
			OidcUser oidcUser = delegate.loadUser(userRequest);

			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
			
			Optional<UserBean> user = userService.findByName(oidcUser.getEmail());
			
			if (user.isPresent()) {
				for (GrantedAuthority auth : user.get().getAuthorities()) {
					mappedAuthorities.add(auth);
				}
			}
			
			oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
			return oidcUser;
		};
	}
    
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
    
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