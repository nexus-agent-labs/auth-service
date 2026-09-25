package authservice.user.dto;

import authservice.user.User;
import lombok.Data;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data 
public class SignUpRequest {
    
    private String email;
    private String username;
    private String password;
    private LocalDateTime createdAt;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
            .email(email)
            .username(username)
            .password(passwordEncoder.encode(password))
            .build();
    }
}