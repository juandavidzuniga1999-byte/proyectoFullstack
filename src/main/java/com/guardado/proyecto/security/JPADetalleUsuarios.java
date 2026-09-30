package com.guardado.proyecto.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.guardado.proyecto.entities.usuario;

public class JPADetalleUsuarios implements UserDetails  {
    private final usuario usuario;

    public JPADetalleUsuarios(usuario usuario) {
      this.usuario = usuario;
    }

    @Override
    public String getUsername() {
        return usuario.getNombreUsuario();
    }

    @Override
    public String getPassword() {
        return usuario.getContraseña();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(usuario::getAutoridad);
    }
}

