package com.example.projrcte.bd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projrcte.model.Restaurante;
import com.example.projrcte.model.User;

import java.util.List;

@Dao
public interface  AppDao  {
   @Query("SELECT * FROM user")
   List<User> getAll();

   @Query("SELECT * FROM user WHERE email=:email")
   User findByEmail(String email);

   @Query("SELECT * FROM user WHERE email=:email AND pass=:pass")
   User findByEmailAndPass(String email, String pass);

   @Insert
   public void insertarUser(User user);

//   @Insert
//   public void insertarRestaurante(Restaurante restaurante);

}
