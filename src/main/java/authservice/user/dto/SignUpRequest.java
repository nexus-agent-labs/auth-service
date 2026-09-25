package authservice.user.dto;

import authservice.user.User;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

@Data 
public class SignUpRequest {
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
            .email(email)
            .passwordHash(passwordEncoder.encode(password))
            .build();
    }
}
