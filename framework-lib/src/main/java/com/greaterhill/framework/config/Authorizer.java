package com.greaterhill.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greaterhill.framework.model.CommonResponseObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;


@Component
@Slf4j
public class Authorizer extends OncePerRequestFilter {

    @Value("${app.secretkey}")
    private String SECRET_KEY;

    private static boolean ignoreUri(String uri){
        return uri.contains("/actuator") || uri.contains("/health");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }
            if(ignoreUri(request.getRequestURI()) || SECRET_KEY.equals(token)) {
                filterChain.doFilter(request, response);
            }
            else {
                log.error("Invalid Token");
                handleException(response, new AccessDeniedException("Invalid Token"));
            }
        } catch (Exception e) {
          log.error("Invalid Request");
        }
    }

    private void handleException(HttpServletResponse response, Exception exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        CommonResponseObject commonResponseObject = CommonResponseObject.builder().status("failed").message(exception.getMessage()).build();
        response.getWriter().write(new ObjectMapper().writeValueAsString(commonResponseObject));
        response.getWriter().flush();
        response.getWriter().close();
    }
}