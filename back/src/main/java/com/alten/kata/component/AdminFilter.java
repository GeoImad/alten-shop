package com.alten.kata.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

            // Vérifier si la requête concerne /products/** et nécessite une vérification d'admin
            if (requestURI.startsWith("/products") && !requestURI.contains("wishlist") && !requestURI.contains("cart") && (method.equals("POST") || method.equals("PUT") || method.equals("DELETE"))) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null || authentication.getPrincipal() == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"error\": \"Vous devez être connecté.\"}");
                    response.setContentType("application/json");
                    response.getWriter().flush();
                    return;
                }

                String email = authentication.getName(); // Récupère l'email de l'utilisateur

                if (!"admin@admin.com".equals(email)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"error\": \"Accès refusé. Seul admin@admin.com peut modifier les produits.\"}");
                    response.setContentType("application/json");
                    response.getWriter().flush();
                    return;
                }

            }

        chain.doFilter(request, response);
    }
}

