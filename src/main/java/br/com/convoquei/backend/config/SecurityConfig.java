package br.com.convoquei.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // API REST: normalmente desabilita CSRF (principalmente se usar JWT/stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // Se for usar token depois, já deixa stateless
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // liberar login/registro
                        .requestMatchers(HttpMethod.POST,  "/api/v**/auth/**").permitAll()

                        // liberar swagger (se estiver usando springdoc)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // TODO: se tiver healthcheck/actuator:
                        // .requestMatchers("/actuator/health").permitAll()

                        // qualquer outra rota precisa estar autenticada
                        .anyRequest().authenticated()
                )

                // Para API REST, normalmente desliga formLogin.
                .httpBasic(Customizer.withDefaults()) // pode manter temporariamente p/ testar
                .formLogin(AbstractHttpConfigurer::disable)

                .build();
    }
}