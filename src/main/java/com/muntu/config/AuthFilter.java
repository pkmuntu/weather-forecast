package com.muntu.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter extends GenericFilterBean {

    private final ApplicationPropertyReader applicationPropertyReader;

    private final UserDetailsService userDetailsService;

    private final Map<String,String> mapOfClientIdAndClientSecret;

    public AuthFilter(ApplicationPropertyReader applicationPropertyReader, UserDetailsService userDetailsService) {
        this.applicationPropertyReader = applicationPropertyReader;
        this.userDetailsService = userDetailsService;
        mapOfClientIdAndClientSecret=new HashMap<>();
        String clients=applicationPropertyReader.getProperty("clients");
        for(String client : clients.split("\\|")){
            String []clientSplit=client.split(",");
            mapOfClientIdAndClientSecret.put(clientSplit[0],clientSplit[1]);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String clientId = httpServletRequest.getHeader("CLIENT_ID");
        String clientSecret = httpServletRequest.getHeader("CLIENT_SECRET");

        if(mapOfClientIdAndClientSecret.get(clientId)!= null && mapOfClientIdAndClientSecret.get(clientId).equals(clientSecret)){
            Authentication auth= getAuthentication(clientId);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private Authentication getAuthentication(String username) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
