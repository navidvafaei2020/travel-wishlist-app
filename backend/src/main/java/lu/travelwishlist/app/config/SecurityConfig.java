package lu.travelwishlist.app.config;


import lu.travelwishlist.app.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Central Spring Security configuration for the application.
 *
 * <p>
 * This class configures:
 * <ul>
 *   <li>JWT-based authentication</li>
 *   <li>Stateless session management</li>
 *   <li>CORS settings for the frontend</li>
 *   <li>Authorization rules for API endpoints</li>
 * </ul>
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Custom JWT authentication filter used to validate JWT tokens
     * on incoming requests.
     */
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;


    /**
     * Password encoder bean using BCrypt hashing.
     *
     * @return a BCrypt-based {@link PasswordEncoder}
     *
     * <p>
     * BCrypt is a secure, adaptive hashing algorithm recommended
     * for storing user passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Defines the CORS configuration for the application.
     *
     * @return a {@link CorsConfigurationSource} allowing frontend access
     *
     * <p>
     * This configuration:
     * <ul>
     *   <li>Allows requests from the frontend (localhost:5173)</li>
     *   <li>Supports common HTTP methods</li>
     *   <li>Allows all headers</li>
     *   <li>Enables credentials (cookies / auth headers)</li>
     * </ul>
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configures the Spring Security filter chain.
     *
     * @param http the {@link HttpSecurity} configuration object
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if security configuration fails
     *
     * <p>
     * Key behaviors:
     * <ul>
     *   <li>Enables CORS and disables CSRF (JWT-based security)</li>
     *   <li>Uses stateless sessions</li>
     *   <li>Allows public access to auth, Swagger, and GET destinations</li>
     *   <li>Requires authentication for all other API endpoints</li>
     *   <li>Registers the JWT filter before username/password authentication</li>
     * </ul>
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // ← ADD THIS
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Swagger/OpenAPI - public
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        // Auth endpoints - public
                        .requestMatchers("/api/auth/**").permitAll()

                        // Listing GET endpoints - public (anyone can browse)
                        .requestMatchers(HttpMethod.GET, "/api/destinations/**").permitAll()

                        // All other /api/** endpoints require authentication
                        .requestMatchers("/api/**").authenticated()

                        // Everything else - require auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}