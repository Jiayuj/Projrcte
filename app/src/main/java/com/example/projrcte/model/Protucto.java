package com.example.projrcte.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Protucto")
public class Protucto {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public String descripcion;
    public String precio;

    public Protucto() {
    }
}
