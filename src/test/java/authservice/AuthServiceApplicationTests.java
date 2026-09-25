package authservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import authservice.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void signupStoresHashedPasswordAndRejectsDuplicateEmail() throws Exception {
        String request = "{\"email\":\"user@example.com\",\"password\":\"password1\"}";

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType("application/json")
                .content(request))
            .andExpect(status().isCreated());

        var user = userRepository.findAll().getFirst();
        assertThat(user.getPasswordHash()).isNotEqualTo("password1");
        assertThat(passwordEncoder.matches("password1", user.getPasswordHash())).isTrue();

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType("application/json")
                .content(request))
            .andExpect(status().isConflict());
    }
}
