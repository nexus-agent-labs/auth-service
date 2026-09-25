package authservice.user;

import authservice.user.domain.User;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("email not found"));
        
        return user.getPasswordHash().equals(dto.getPassword());
    }
    
}
