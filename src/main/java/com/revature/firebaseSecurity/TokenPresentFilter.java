package com.revature.firebaseSecurity;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author William Gentry
 */
@Component
public class TokenPresentFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AuthenticationManager authenticationManager;
    
    private final UserDetailsService clientUserDetailsService;

    public TokenPresentFilter(AuthenticationManager authenticationManager, UserDetailsService clientUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.clientUserDetailsService = clientUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String uri = httpServletRequest.getRequestURI();

        if (uri.contains("actuator")) {
            logger.debug("Request to actuator endpoint. Ignoring");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (HttpMethod.POST.name().equals(httpServletRequest.getMethod()) && "/login".equals(httpServletRequest.getRequestURI())) {
  		  filterChain.doFilter(httpServletRequest, httpServletResponse);
  		  return;
  		}
        final String header = httpServletRequest.getHeader("Authorization");
        String token = null;
        Authentication authentication = null;
        if (header != null && header.startsWith("Bearer")) {

            token = header.replace("Bearer ", "");
            authentication = authenticationManager.authenticate(new UnauthenticatedFirebaseUser(token));
        } else {
            logger.info("No JWT Token present. Ignoring header");
        }

        if (authentication instanceof FirebaseUser) {
        	UserDetails details = clientUserDetailsService.loadUserByUsername(authentication.getName());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            		details.getUsername(),details.getPassword(), details.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(auth);
            logger.info("Successfully set security context for {} request to {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}