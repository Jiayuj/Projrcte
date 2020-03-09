package com.example.projrcte.model;


import android.net.Uri;
import android.widget.ImageView;

import java.net.URL;

public class Restaurante {

    public String id;

    public String nombre;

    public String descripcion;
    public String urlImage;

    public Restaurante() {
    }

    public Restaurante(String nombre, String descripcion, Uri urlImage) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImage = String.valueOf(urlImage);
    }

}
