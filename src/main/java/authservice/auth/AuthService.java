package authservice.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import authservice.exception.DuplicateEmailException;
import authservice.jwt.JwtTokenProvider;
import authservice.jwt.TokenResponse;
import authservice.user.domain.User;
import authservice.auth.dto.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class AuthService {

    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public void signup(SignUpRequest dto) {

        if (authRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }

        authRepository.save(dto.toUser(passwordEncoder));
    }

    public TokenResponse login(LoginRequest request) {
  
        User user = authRepository.findByEmail(request.email())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }   

        Long userId = user.getId();

        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        return new TokenResponse(
            accessToken,
            refreshToken,
            "Bearer",
            3600
        );
    }
    
}
