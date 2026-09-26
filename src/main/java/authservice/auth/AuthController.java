package authservice.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import authservice.auth.dto.LoginRequest;
import authservice.auth.dto.SignUpRequest;
import authservice.jwt.TokenResponse;
import lombok.AllArgsConstructor;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth") 
@AllArgsConstructor 
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest dto) {
        authService.signup(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Map.of("message", "Signup successful"));
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}
