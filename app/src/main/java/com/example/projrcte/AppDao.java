package com.example.projrcte;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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

}
