package com.guardado.proyecto.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.guardado.proyecto.entities.usuario;
import com.guardado.proyecto.repository.usuarioRepository;

@Configuration
public class gestionUsuariosConfig {

    // Si no hay ningun usuario en la tabla, crea un admin por default.
    // IMPORTANTE: entra con estas credenciales y cambia la contraseña
    // (o borra este usuario y crea uno nuevo) despues del primer login.
    // Usuario: admin
    // Password: Admin123!
    @Bean
    public CommandLineRunner crearAdminPorDefault(usuarioRepository repo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repo.count() == 0) {
                usuario admin = new usuario();
                admin.setNombreUsuario("admin");
                admin.setContraseña(passwordEncoder.encode("Admin123!"));
                admin.setAutoridad("ROLE_ADMIN");
                repo.save(admin);
            }
        };
    }
}