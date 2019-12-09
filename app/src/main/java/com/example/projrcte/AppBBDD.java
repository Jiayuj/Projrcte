package com.example.projrcte;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {User.class}, version = 2,exportSchema = false)
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

