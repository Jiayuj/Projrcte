package com.example.projrcte;

import android.widget.TextView;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public abstract class AppDao  {
   @Query("SELECT * FROM User")


   @Insert
   public abstract void insertarUser(User user);
}
