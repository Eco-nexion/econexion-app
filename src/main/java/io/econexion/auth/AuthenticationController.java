package io.econexion.auth;

import io.econexion.lab.users.InMemoryLabUserService;
import io.econexion.lab.users.dto.UserDto;
import io.econexion.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y registro de usuarios")

public class AuthenticationController {
    private final InMemoryLabUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,InMemoryLabUserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // ==== DTOs ====
    public record LoginRequest(@NotBlank String email, 
                               @NotBlank String password) {}

    public record LoginResponse(String token) {}

    public record RegisterRequest(@NotBlank String username, 
                                  @NotBlank String password, 
                                  @NotBlank String email) {}


    @Operation(summary = "Autenticar usuario", description = "Recibe email y contraseña y devuelve un JWT")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        String token = jwtUtil.generate(req.email());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario y devuelve su información")

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest user) {
        if (userService.existsByEmail(user.email)) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        UserDto newUser = userService.create(
                                                new UserDto(
                                                    user.username(), 
                                                    user.email(), 
                                                    passwordEncoder.encode( user.password())
                                                )
                                            );      

        return ResponseEntity.ok(newUser);
    }
}
