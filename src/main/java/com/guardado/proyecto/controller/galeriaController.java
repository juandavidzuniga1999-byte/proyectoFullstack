package com.guardado.proyecto.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guardado.proyecto.model.galeriaFoto;
import com.guardado.proyecto.repository.galeriaFotoRepository;

@RestController
@RequestMapping("/api/galeria")
public class galeriaController {

    private static final List<String> SECCIONES_VALIDAS = List.of("stone", "boulevard", "greengarden", "deck");

    @Autowired
    private galeriaFotoRepository repo;

    @Value("${app.upload.dir}")
    private String uploadDir;

    // GET /api/galeria/stone -> fotos de esa sección (público)
    @GetMapping("/{seccion}")
    public List<galeriaFoto> listar(@PathVariable String seccion) {
        return repo.findBySeccionOrderByIdAsc(seccion);
    }

    // POST /api/galeria/stone  body: {"url": "/uploads/xxx.jpg"} (solo ADMIN)
    @PostMapping("/{seccion}")
    public ResponseEntity<?> agregar(@PathVariable String seccion, @RequestBody Map<String, String> body) {
        if (!SECCIONES_VALIDAS.contains(seccion)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Sección no válida"));
        }
        String url = body.get("url");
        if (url == null || url.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Falta la url de la foto"));
        }
        galeriaFoto foto = new galeriaFoto();
        foto.setSeccion(seccion);
        foto.setUrl(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(foto));
    }

    // DELETE /api/galeria/foto/7 (solo ADMIN)
    @DeleteMapping("/foto/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        return repo.findById(id).map(foto -> {
            borrarArchivoSiEsLocal(foto.getUrl());
            repo.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Si la foto se subió desde el admin (/uploads/...), también borra el archivo
    // del disco para no acumular basura. Las fotos estáticas (images/...) no se tocan.
    private void borrarArchivoSiEsLocal(String url) {
        if (url == null || !url.startsWith("/uploads/")) return;
        try {
            Path carpeta = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path archivo = carpeta.resolve(url.substring("/uploads/".length())).normalize();
            if (archivo.startsWith(carpeta)) {
                Files.deleteIfExists(archivo);
            }
        } catch (IOException e) {
            // si no se pudo borrar el archivo, igual se quita el registro
        }
    }
}