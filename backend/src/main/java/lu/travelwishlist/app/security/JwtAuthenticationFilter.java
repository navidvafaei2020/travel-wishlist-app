package lu.travelwishlist.app.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lu.travelwishlist.app.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT authentication filter that intercepts incoming HTTP requests
 * to validate JWT tokens and set the Spring Security context.
 *
 * <p>
 * Extracts user ID and role from the token and sets authentication
 * for downstream controllers and services.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. Check if header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3. Extract token (remove "Bearer " prefix)
            String token = authHeader.substring(7);

            // 4. Validate token
            if (jwtService.isTokenValid(token)) {
                // 5. Extract user info from token
                //String username = jwtService.extractUsername(token);
                Long userId = jwtService.extractUserId(token);
                String role = jwtService.extractRole(token);

                // 6. Create authentication object
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,  // Principal (user ID)
                                null,    // Credentials (not needed)
                                Collections.singletonList(authority)  // Authorities (roles)
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Token is invalid - continue without authentication
            logger.error("JWT validation failed: " + e.getMessage());
        }

        // 8. Continue with the request
        filterChain.doFilter(request, response);
    }
}