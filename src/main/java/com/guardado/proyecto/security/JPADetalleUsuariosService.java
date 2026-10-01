package com.guardado.proyecto.security;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.guardado.proyecto.entities.usuario;
import com.guardado.proyecto.repository.usuarioRepository;

@Component
public class JPADetalleUsuariosService implements UserDetailsService {
    @Autowired usuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Optional<usuario> usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuario.isPresent()) {
            return new JPADetalleUsuarios(usuario.get());
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}