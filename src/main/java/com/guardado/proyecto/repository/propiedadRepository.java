package com.guardado.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.guardado.proyecto.model.propiedad;
 
public interface propiedadRepository extends JpaRepository<propiedad, Integer> {
}
