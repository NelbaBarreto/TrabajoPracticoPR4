package Modelos;

import java.io.Serializable;

public class Pizza implements Serializable  {
    private String sabor;
    private String tamanio;
    private int cantidad;

    public Pizza(String sabor, String tamanio, int cantidad) {
        this.sabor = sabor;
        this.tamanio = tamanio;
        this.cantidad = cantidad;
    }

    public String getSabor() {
        return sabor;
    }

    public String getTamanio() {
        return tamanio;
    }

    public int getCantidad() {
        return cantidad;
    }
}
