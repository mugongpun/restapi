package com.example.restapi.jwt;

import com.example.restapi.security.CustomUserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 필터
 */

//@Component
@RequiredArgsConstructor
@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headStr = request.getHeader("Authorization");
        if (headStr == null || !headStr.startsWith("Bearer ")) {
            handleException(new Exception("ACCESS TOKEN NOT FOUND"));
        }

        try {
            String accessToken = headStr.substring(7);
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            String mid = claims.get("mid")
                               .toString();
            String[] roles = claims.get("role")
                                   .toString()
                                   .split(",");

            UsernamePasswordAuthenticationToken up = new UsernamePasswordAuthenticationToken(new CustomUserPrincipal(mid), null, Arrays.stream(roles)
                                                                                                                                       .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                                                                                                                       .collect(Collectors.toList()));
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(up);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getRequestURI().startsWith("api/token")) {
            return true;
        }

        return false;
    }

    public ResponseEntity<Map<String, String>> handleException(Exception e){
        HashMap<String, String> body = new HashMap<>();
        body.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(body);
    }
}
