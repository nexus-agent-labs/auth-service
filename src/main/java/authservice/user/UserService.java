package authservice.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import authservice.exception.DuplicateEmailException;
import authservice.user.dto.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void signup(SignUpRequest dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }

        userRepository.save(dto.toUser(passwordEncoder));
    }

    public boolean login(LoginRequest dto) {
        return userRepository.findByEmail(dto.getEmail())
            .map(user -> passwordEncoder.matches(dto.getPassword(), user.getPasswordHash()))
            .orElse(false);
    }
    
}
