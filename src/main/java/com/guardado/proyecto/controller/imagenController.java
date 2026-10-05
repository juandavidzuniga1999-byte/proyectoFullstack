package com.guardado.proyecto.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagenes")
public class imagenController {

    // Carpeta donde se guardan los archivos. Local: carpeta "uploads" junto
    // al jar. Railway: el path donde montaste el Volume (ver application.properties).
    @Value("${app.upload.dir}")
    private String uploadDir;

    private static final List<String> EXTENSIONES_PERMITIDAS = List.of("jpg", "jpeg", "png", "webp", "gif");
    private static final long TAMANO_MAXIMO_BYTES = 10L * 1024 * 1024; // 10MB

    // POST /api/imagenes/upload (multipart/form-data, campo "archivo")
    // Regresa {"url": "/uploads/xxxxx.jpg"} para guardar ese valor en
    // el campo imagenUrl de la propiedad.
    @PostMapping("/upload")
    public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No se recibió ningún archivo"));
        }
        if (archivo.getSize() > TAMANO_MAXIMO_BYTES) {
            return ResponseEntity.badRequest().body(Map.of("error", "El archivo es demasiado grande (máximo 10MB)"));
        }

        String nombreOriginal = archivo.getOriginalFilename();
        String extension = "";
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            extension = nombreOriginal.substring(nombreOriginal.lastIndexOf('.') + 1).toLowerCase();
        }
        if (!EXTENSIONES_PERMITIDAS.contains(extension)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato no permitido. Usa jpg, png, webp o gif"));
        }

        try {
            Path carpeta = Paths.get(uploadDir);
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            // Nombre unico para que no se pisen dos imagenes con el mismo nombre
            String nombreArchivo = UUID.randomUUID().toString() + "." + extension;
            Path destino = carpeta.resolve(nombreArchivo);
            archivo.transferTo(destino);

            String urlPublica = "/uploads/" + nombreArchivo;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("url", urlPublica));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "No se pudo guardar la imagen"));
        }
    }
}