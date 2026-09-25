package authservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import authservice.user.dto.SignUpRequest;
import lombok.AllArgsConstructor;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth") 
@AllArgsConstructor 
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(SignUpRequest dto) {
        userService.signup(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Map.of("message", "Signup is successfully end")
        );
    }
    
    
}
