package authservice.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import authservice.user.dto.SignUpRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void signup(SignUpRequest dto) {
        userRepository.save(dto.toUser(passwordEncoder));
    }
    
}
