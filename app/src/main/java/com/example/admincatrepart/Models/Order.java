package com.example.admincatrepart.Models;

public class Order {
    private String ProductoID;
    private String ProductoNombre;
    private String Cantidad;
    private String Precio;

    public Order() {
    }

    public Order(String productoID, String productoNombre, String cantidad, String precio) {
        ProductoID = productoID;
        ProductoNombre = productoNombre;
        Cantidad = cantidad;
        Precio = precio;
    }

    public String getProductoID() {
        return ProductoID;
    }

    public void setProductoID(String productoID) {
        ProductoID = productoID;
    }

    public String getProductoNombre() {
        return ProductoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        ProductoNombre = productoNombre;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }
}
