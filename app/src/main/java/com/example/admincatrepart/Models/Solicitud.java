package com.example.admincatrepart.Models;

import java.util.List;

public class Solicitud {
    private String telefono;
    private String nombre;
    private String direccion;
    private String total;
    private String status;
    private List<Order> comidas;

    public Solicitud() {
    }

    public Solicitud(String telefono, String nombre, String direccion, String total, List<Order> comidas) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.direccion = direccion;
        this.total = total;
        this.comidas = comidas;
        this.status = "0";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getComidas() {
        return comidas;
    }

    public void setComidas(List<Order> comidas) {
        this.comidas = comidas;
    }
}
