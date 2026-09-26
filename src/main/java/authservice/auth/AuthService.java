package authservice.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import authservice.exception.DuplicateEmailException;
import authservice.auth.dto.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class AuthService {

    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;

    public void signup(SignUpRequest dto) {

        if (authRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }

        authRepository.save(dto.toUser(passwordEncoder));
    }

    public boolean login(LoginRequest dto) {
        return authRepository.findByEmail(dto.getEmail())
            .map(user -> passwordEncoder.matches(dto.getPassword(), user.getPasswordHash()))
            .orElse(false);
    }
    
}
