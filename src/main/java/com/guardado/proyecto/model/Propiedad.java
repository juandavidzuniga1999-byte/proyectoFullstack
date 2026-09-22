package com.guardado.proyecto.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propiedades")

public class propiedad {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String imagenUrl; 
    private String tipoPropiedad;
    private int terreno;
    private int construccion;
    private int banos;
    private int medioBano;
    private int estacionamiento;
    private int cuarto;
    private String condicion;
    private String ubicacion;
    private String descripcion;
    private int precio;

    
    @ElementCollection
    @CollectionTable(name = "propiedad_imagenes", joinColumns = @JoinColumn(name = "propiedad_id"))
    @OrderColumn(name = "posicion")
    @Column(name = "url")
    private List<String> imagenes = new ArrayList<>();
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getTitulo() {
        return titulo;
    }
 
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
 
    public String getImagenUrl() {
        return imagenUrl;
    }
 
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
 
    public String getTipoPropiedad() {
        return tipoPropiedad;
    }
 
    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }
 
    public int getTerreno() {
        return terreno;
    }
 
    public void setTerreno(int terreno) {
        this.terreno = terreno;
    }
 
    public int getConstruccion() {
        return construccion;
    }
 
    public void setConstruccion(int construccion) {
        this.construccion = construccion;
    }
 
    public int getBanos() {
        return banos;
    }
 
    public void setBanos(int banos) {
        this.banos = banos;
    }
 
    public int getMedioBano() {
        return medioBano;
    }
 
    public void setMedioBano(int medioBano) {
        this.medioBano = medioBano;
    }
 
    public int getEstacionamiento() {
        return estacionamiento;
    }
 
    public void setEstacionamiento(int estacionamiento) {
        this.estacionamiento = estacionamiento;
    }
 
    public int getCuarto() {
        return cuarto;
    }
 
    public void setCuarto(int cuarto) {
        this.cuarto = cuarto;
    }
 
    public String getCondicion() {
        return condicion;
    }
 
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
 
    public String getUbicacion() {
        return ubicacion;
    }
 
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public int getPrecio() {
        return precio;
    }
 
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

}
