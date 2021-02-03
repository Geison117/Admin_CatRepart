package com.example.admincatrepart.Models;

public class Usuario {
    private String Nombre;
    private String Contrasena;
    private String telefono;

    public Usuario() {
    }

    public Usuario(String nombre, String contrasena) {
        Nombre = nombre;
        Contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }
}
