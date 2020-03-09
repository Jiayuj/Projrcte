package com.example.projrcte.model;


import android.widget.ImageView;

public class Restaurante {

    public String id;

    public String nombre;

    public String descripcion;
    public String urlImage;

    public Restaurante() {
    }

    public Restaurante(String nombre, String descripcion, String urlImage) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
    }

}
