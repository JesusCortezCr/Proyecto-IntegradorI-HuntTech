package com.proyecto.integrador1.proyecto_integrador.configuration;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.proyecto.integrador1.proyecto_integrador.entities.Usuario;
import com.proyecto.integrador1.proyecto_integrador.repositories.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository; // Solo usa el repositorio directamente

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
            Usuario usuario = usuarioOpt.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            String roleName = usuario.getRol().getNombre();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }

            return new User(
                    usuario.getEmail(),
                    usuario.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(roleName)));
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }
}