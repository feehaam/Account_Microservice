package com.prep.account.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prep.account.config.SpringApplicationContext;
import com.prep.account.iservice.AccessService;
import com.prep.account.iservice.AccountManagementService;
import com.prep.account.model.AccountReadDTO;
import com.prep.account.model.LoginRequestDTO;
import com.prep.account.model.LoginResponseDTO;
import com.prep.account.utilities.constants.AppConstants;
import com.prep.account.utilities.token.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/sign_in");
    }

    private final Map<String, Integer> attemptCount = new HashMap<String, Integer>();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequestDTO credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
        } catch (IOException e) {
            writeResponse(response, "Exception while reading credentials", 400);
        }
        attemptCount.put(credentials.getIdentity(), attemptCount.getOrDefault(credentials.getIdentity(), 0) + 1);
        return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getIdentity(),credentials.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();
        AccountManagementService accountManagementService = (AccountManagementService) SpringApplicationContext.getBean("accountManagementServiceImpl");
        AccountReadDTO accountReadDTO = accountManagementService.getUserInfo(username);

        if (attemptCount.get(accountReadDTO.getEmail()) > AppConstants.MAX_LOGIN_ATTEMPTS_LIMIT) {
            restrictedResponse(response);
            return;
        } else {
            attemptCount.put(accountReadDTO.getEmail(), 0);
        }

        List<String> userRoles = accountReadDTO.getRoles().stream().map(r -> "ROLE_" + r).collect(Collectors.toList());
        String bearerToken = JWTUtils.generateToken(username, userRoles);

        LoginResponseDTO responseBody = new LoginResponseDTO(accountReadDTO.getUserId().toString(),
                accountReadDTO.getUserName(), bearerToken, accountReadDTO.getRoles());
        writeResponse(response, responseBody, 200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Username or email wrong. Max failed attempt: " + AppConstants.MAX_LOGIN_ATTEMPTS_LIMIT);
        writeResponse(response, errorResponse, 400);
    }

    private void restrictedResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Restricted", "Your account has been locked for " + AppConstants.MAX_LOGIN_ATTEMPTS_LIMIT +" failed attempts.");
        writeResponse(response, errorResponse, 403);
    }

    private void writeResponse(HttpServletResponse response, Object object, int statusCode){
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try{
            new ObjectMapper().writeValue(response.getWriter(), object);
        }
        catch (IOException ioe){
            log.error("Failed to write in response.", ioe);
        }
    }
}