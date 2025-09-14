package io.econexion.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    // Bean único del encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Usuarios en memoria
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails u = User.builder()
                .username("ada")
                .password(encoder.encode("school"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(u);
    }

    // Utilidad JWT
    @Bean
    public JwtUtil jwtUtil(@Value("${jwt.secret}") String secret,
                           @Value("${jwt.expiration-minutes}") long expMin) {
        return new JwtUtil(secret, expMin);
    }

    // Proveedor + AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService uds, PasswordEncoder enc) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(enc);
        return new ProviderManager(provider);
    }

    // Filtro de JWT para proteger POST /v1/weather/**
    @Bean
    public SimpleJwtFilter simpleJwtFilter(JwtUtil jwtUtil, AuthenticationManager am) {
        return new SimpleJwtFilter(jwtUtil, am);
    }

    // Cadena de filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SimpleJwtFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**", "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/weather/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/v1/weather/**").authenticated()
                .anyRequest().permitAll()
        );

        http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);

        return http.httpBasic(Customizer.withDefaults()).build();
    }

    // Controller de login
    @Bean
    public LoginController loginController(AuthenticationManager am, JwtUtil jwt, ObjectMapper om) {
        return new LoginController(am, jwt, om);
    }

    // ===== soporte =====
    static class SimpleJwtFilter extends BasicAuthenticationFilter {
        private final JwtUtil jwtUtil;

        public SimpleJwtFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
            super(authenticationManager);
            this.jwtUtil = jwtUtil;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                throws IOException, ServletException {
            if ("POST".equals(req.getMethod()) && req.getRequestURI().startsWith("/v1/weather/")) {
                String auth = req.getHeader("Authorization");
                if (auth == null || !auth.startsWith("Bearer ")) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Bearer token");
                    return;
                }
                String token = auth.substring(7);
                try {
                    SecretKey key = jwtUtil.key();
                    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                } catch (Exception e) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            }
            chain.doFilter(req, res);
        }
    }

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token) {}

    public static class LoginController {
        private final AuthenticationManager am;
        private final JwtUtil jwt;
        private final ObjectMapper om;

        public LoginController(AuthenticationManager am, JwtUtil jwt, ObjectMapper om) {
            this.am = am; this.jwt = jwt; this.om = om;
        }

        @org.springframework.web.bind.annotation.PostMapping(
                value = "/api/auth/login",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {
            LoginRequest body = om.readValue(req.getInputStream(), LoginRequest.class);
            Authentication auth = am.authenticate(
                    new UsernamePasswordAuthenticationToken(body.username(), body.password()));
            String token = jwt.generate(auth.getName());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            om.writeValue(res.getOutputStream(), new LoginResponse(token));
        }
    }
}
