package com.guardado.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guardado.proyecto.entities.usuario;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Integer> {
    Optional<usuario> findByNombreUsuario(String nombreUsuario);
}
