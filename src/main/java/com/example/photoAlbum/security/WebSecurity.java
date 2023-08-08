package com.example.photoAlbum.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurity {

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    SecurityFilterChain configure(@NotNull HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                .anyRequest().authenticated())
                .oauth2Login(oauth -> {})
                .logout(logout -> {
                    //logout.logoutSuccessUrl("/");
                    logout.logoutSuccessHandler(oidcLogoutSuccessHandler());
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.deleteCookies("JSESSIONID");
                });

        return http.build();
    }

    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:8087/");
        return successHandler;
    }
}
