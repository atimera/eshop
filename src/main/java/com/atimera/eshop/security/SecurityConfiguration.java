package com.atimera.eshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * Configuration Spring security
 */
@EnableWebSecurity 
@Configuration 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disables cross-site request forgery (CSRF) protection, as Vaadin already has CSRF protection.
        http.csrf().disable()

                // Uses CustomRequestCache to track unauthorized requests so that users are redirected appropriately after login.
                .requestCache().requestCache(new CustomRequestCache())

                // Turns on authorization.
                .and().authorizeRequests()

                // Allows all internal traffic from the Vaadin framework.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allows all authenticated traffic.
                .anyRequest().authenticated()

                // Enables form-based login and permits unauthenticated access to it.
                .and().formLogin()
                    .loginPage(LOGIN_URL).permitAll()
                    // Configures the login page URLs.
                    .loginProcessingUrl(LOGIN_PROCESSING_URL)
                    .failureUrl(LOGIN_FAILURE_URL)

                // Configures the logout URL.
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("laenzo")
                        .password("{noop}laenzo")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    //
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/**", // POUR LE DEV
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/frontend/**",
                "/styles/**",
                "/webapp/**",
                "/h2-console/**");
    }

}