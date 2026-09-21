package com.guardado.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
public class securityConfig {
 
    // Como el proyecto ya trae spring-boot-starter-security, sin esta
    // configuracion TODO (paginas HTML y la API) quedaria bloqueado detras
    // de un login con password aleatorio.
    //
    // Por ahora dejamos todo publico para poder seguir desarrollando.
    // Cuando tengas un panel de administracion, aqui es donde vas a
    // restringir POST/PUT/DELETE de /api/propiedades/** solo a usuarios
    // autenticados.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}

