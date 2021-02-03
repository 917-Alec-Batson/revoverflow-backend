package com.revature.firebaseSecurity;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.http.HttpMethod;
import com.revature.firebaseSecurity.props.CorsConfigurationProps;
import com.revature.repositories.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenPresentFilter tokenPresentFilter;
    private final CorsConfigurationProps corsConfigurationProps;

    public SecurityConfiguration(TokenPresentFilter tokenPresentFilter, CorsConfigurationProps corsConfigurationProps) {
        this.tokenPresentFilter = tokenPresentFilter;
        this.corsConfigurationProps = corsConfigurationProps;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers(HttpMethod.GET, "/actuator/*");
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors(spec -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(corsConfigurationProps.getAllowedOrigins());
                    config.setAllowedMethods(corsConfigurationProps.getAllowedMethods());
                    config.setAllowedHeaders(corsConfigurationProps.getAllowedHeaders());
                    config.setExposedHeaders(corsConfigurationProps.getExposedHeaders());
                    config.setAllowCredentials(corsConfigurationProps.isAllowCredentials());
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", config);
                    spec.configurationSource(source);
                })
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeRequests()
                .mvcMatchers().permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(tokenPresentFilter, UsernamePasswordAuthenticationFilter.class);
        
    }
}