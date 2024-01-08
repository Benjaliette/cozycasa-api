package me.cozycosa.api.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyFilter extends OncePerRequestFilter {
    private String apiKey;

    public ApiKeyFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestApiKey = request.getHeader("API-KEY");

        if (isValidApiKey(requestApiKey)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key invalide");
        }
    }

    private boolean isValidApiKey(String requestApiKey) {
        return apiKey.equals(requestApiKey);
    }
}
