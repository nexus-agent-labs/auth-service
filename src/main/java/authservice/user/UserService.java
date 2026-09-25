package authservice.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import authservice.exception.DuplicateEmailException;
import authservice.user.dto.SignUpRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void signup(SignUpRequest dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }

        userRepository.save(dto.toUser(passwordEncoder));
    }
    
    @Override 
    public UserDetails loadUserByUsername(String username) 
        throws UsernameNotFoundException {
            return userRepository.findByEmail(username)
                .orElseThrow(() -> 
                    new UsernameNotFoundException("username '" + username + "' not found"));
        }
}
