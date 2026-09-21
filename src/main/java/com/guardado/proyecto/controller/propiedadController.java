package com.guardado.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guardado.proyecto.model.propiedad;
import com.guardado.proyecto.repository.propiedadRepository;

@RestController
@RequestMapping("/api/propiedades")
public class propiedadController {

    @Autowired
    private propiedadRepository propiedadRepository;

    // GET /api/propiedades -> lista todas las propiedades (esto es lo que
    // consume serviciosMov.html)
    @GetMapping
    public List<propiedad> listar() {
        return propiedadRepository.findAll();
    }

    // GET /api/propiedades/5 -> una sola propiedad
    @GetMapping("/{id}")
    public ResponseEntity<propiedad> obtener(@PathVariable int id) {
        return propiedadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/propiedades -> crea una propiedad nueva
    @PostMapping
    public ResponseEntity<propiedad> crear(@RequestBody propiedad propiedad) {
        propiedad guardada = propiedadRepository.save(propiedad);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // PUT /api/propiedades/5 -> actualiza una propiedad existente
    @PutMapping("/{id}")
    public ResponseEntity<propiedad> actualizar(@PathVariable int id, @RequestBody propiedad datos) {
        return propiedadRepository.findById(id).map(existente -> {
            datos.setId(id);
            propiedad actualizada = propiedadRepository.save(datos);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/propiedades/5 -> elimina una propiedad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (!propiedadRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        propiedadRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}