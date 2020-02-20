package com.example.projrcte.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Protucto {
    public String nombre;
    public String descripcion;
    public String precio;

    public Protucto() {
    }

    public Protucto(String nombre, String descripcion, String precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
