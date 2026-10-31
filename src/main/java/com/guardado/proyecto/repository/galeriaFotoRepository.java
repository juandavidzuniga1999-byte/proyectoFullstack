package com.guardado.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guardado.proyecto.model.galeriaFoto;

public interface galeriaFotoRepository extends JpaRepository<galeriaFoto, Integer> {
    List<galeriaFoto> findBySeccionOrderByIdAsc(String seccion);
}