package com.example.projrcte.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Restaurante")
public class Restaurante {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "descripcion")
    public String descripcion;


}
