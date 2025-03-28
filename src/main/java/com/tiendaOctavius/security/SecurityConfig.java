package com.tiendaOctavius.security;

import com.tiendaOctavius.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Definir un PasswordEncoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Inyectar tu UserDetailsServiceImpl
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 3. Configurar el AuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 4. SecurityFilterChain para definir las rutas públicas y privadas
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/login", "/registro/**", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .loginPage("/login") // Ruta del formulario de login
                .permitAll() // Permitir acceso a todos
                .defaultSuccessUrl("/", true) // Redirigir al inicio tras login exitoso
                )
                .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                );

        return http.build();
    }

    // Elimina el método configure(AuthenticationManagerBuilder auth) que retorna void.
}
