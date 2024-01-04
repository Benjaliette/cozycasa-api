package me.cozycosa.api.configuration;

import me.cozycosa.api.users.mappers.UserMapper;
import me.cozycosa.api.users.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthSecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserMapper userMapper(PasswordEncoder passwordEncoder) {
        return new UserMapper(passwordEncoder);
    }
}
