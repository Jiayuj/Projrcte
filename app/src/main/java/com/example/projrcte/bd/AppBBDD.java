package com.example.projrcte.bd;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projrcte.model.Protucto;
import com.example.projrcte.model.Restaurante;
import com.example.projrcte.model.User;

@Database(entities = {User.class}, version = 3,exportSchema = false)
public abstract class AppBBDD extends RoomDatabase {
    public static AppBBDD appBBDD;

    public abstract AppDao appDao();

    public static AppBBDD getInstance(Context context) {
        if (appBBDD == null) {
            appBBDD = Room.databaseBuilder(context, AppBBDD.class, "app.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }
                    })
                    .build();
        }
        return appBBDD;
    }
}

