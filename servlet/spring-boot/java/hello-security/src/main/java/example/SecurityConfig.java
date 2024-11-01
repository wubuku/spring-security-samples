package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import example.repository.UserRepository;
import example.model.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")) // 对 H2 控制台禁用 CSRF 保护
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())) // 允许同源的 frame 加载
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // 允许所有人访问 H2 控制台
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .permitAll());

        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user = User.builder()
    // .username("user")
    // .password(passwordEncoder().encode("password"))
    // .roles("USER")
    // .build();

    // return new InMemoryUserDetailsManager(user);
    // }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            example.model.User user = userRepository.findByUsername(username) // 明确指定使用你的 User 实体
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return org.springframework.security.core.userdetails.User // 明确指定使用 Spring Security 的 User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
}