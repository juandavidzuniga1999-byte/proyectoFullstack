package com.guardado.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.guardado.proyecto.model.Propiedad;
 
public interface propiedadRepository extends JpaRepository<Propiedad, Integer> {
}
