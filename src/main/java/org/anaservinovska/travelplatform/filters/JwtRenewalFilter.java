package org.anaservinovska.travelplatform.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.anaservinovska.travelplatform.utils.JwtUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtRenewalFilter extends org.springframework.web.filter.OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtRenewalFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    if (jwtUtil.validateToken(token)) {
                        if (jwtUtil.needsRenewal(token)) {
                            String username = jwtUtil.getUsernameFromToken(token);
                            String newToken = jwtUtil.generateToken(username, "regular");

                            Cookie newCookie = new Cookie("JWT", newToken);
                            newCookie.setHttpOnly(true);
                            newCookie.setSecure(true); // Set to true in production
                            newCookie.setPath("/");
                            newCookie.setMaxAge((int) jwtUtil.getExpiration() / 1000);

                            response.addCookie(newCookie);
                        }
                    }
                    break;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}