package authservice.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import authservice.auth.dto.LoginRequest;
import authservice.auth.dto.SignUpRequest;
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest dto) {
        boolean validationResult = authService.login(dto);
        
        if (validationResult == true) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Map.of("message", "Email verification successful"));
        } else {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("message", "Invalid email or password"));
        }
    }
}
