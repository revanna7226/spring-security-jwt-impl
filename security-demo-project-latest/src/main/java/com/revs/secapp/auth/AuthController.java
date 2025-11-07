package com.revs.secapp.auth;

import com.revs.secapp.auth.request.AuthRequest;
import com.revs.secapp.auth.request.RefreshRequest;
import com.revs.secapp.auth.request.RegistrationRequest;
import com.revs.secapp.auth.response.AuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody
        @Valid
        final AuthRequest request
        ) {
        AuthResponse authResponse = this.authService.login(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
        @RequestBody
        @Valid
        final RegistrationRequest request
        ) {
        this.authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
        @RequestBody
        @Valid
        final RefreshRequest request
        ) {
        AuthResponse authResponse = this.authService.refreshToken(request);
        return ResponseEntity.ok(authResponse);
    }

}
