package com.example.projrcte;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User  {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String email, pass;

    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }
}
