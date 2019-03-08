package com.example.webdemo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.springframework.http.HttpStatus.OK;

public class RedirectLogoutSuccessHandler implements LogoutSuccessHandler {

    private final String targetUrl;

    public RedirectLogoutSuccessHandler(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(OK.value());
        response.sendRedirect(targetUrl);
    }
}
