package fr.esgi.avis.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. JWT Bearer token (API REST)
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(auth) && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            if (jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.getEmailFromToken(token);
                String role  = jwtTokenProvider.getRoleFromToken(token);
                setAuthentication(email, role);
            }
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Session HTTP (UI Thymeleaf)
        HttpSession session = request.getSession(false);
        if (session != null) {
            String role = (String) session.getAttribute("role");
            String email = resolveEmail(session, role);
            if (role != null && email != null) {
                setAuthentication(email, role);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String email, String role) {
        var token = new UsernamePasswordAuthenticationToken(
                email, null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role)));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private String resolveEmail(HttpSession session, String role) {
        if ("JOUEUR".equals(role))     return (String) session.getAttribute("joueurEmail");
        if ("MODERATEUR".equals(role)) return (String) session.getAttribute("modEmail");
        return null;
    }
}
