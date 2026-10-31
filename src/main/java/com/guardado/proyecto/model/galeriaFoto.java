package com.guardado.proyecto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "galeria_fotos")
public class galeriaFoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seccion; 
    private String url;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}