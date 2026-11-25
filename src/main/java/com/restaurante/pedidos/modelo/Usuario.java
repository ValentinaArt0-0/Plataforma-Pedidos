package com.restaurante.pedidos.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("usuarios")
public class Usuario {

    @Id
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena; // siempre guardada cifrada con BCrypt
    private LocalDateTime fechaRegistro;

    protected Usuario() {
    }

    public Usuario(String nombre, String correo, String telefono, String contrasenaCifrada) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasenaCifrada;
        this.fechaRegistro = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
