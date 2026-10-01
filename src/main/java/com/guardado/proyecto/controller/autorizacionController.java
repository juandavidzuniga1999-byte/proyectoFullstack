package com.guardado.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guardado.proyecto.model.credenciales;
import com.guardado.proyecto.security.JwtService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class autorizacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // POST /api/auth/login -> recibe {"usuario": "...", "contraseña": "..."}
    // y si son correctas regresa {"token": "..."} para usar en el header
    // Authorization: Bearer <token> en las siguientes peticiones.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody credenciales datos) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(datos.getUsuario(), datos.getContraseña()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario o contraseña incorrectos"));
        }

        String token = jwtService.generarToken(datos.getUsuario());
        return ResponseEntity.ok(Map.of("token", token));
    }
}