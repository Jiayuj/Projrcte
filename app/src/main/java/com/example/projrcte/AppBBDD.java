package com.example.projrcte;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppBBDD extends RoomDatabase {
    public static AppDao appDao() {
        return null;
    }

    public static AppBBDD appBBDD;
    public static AppBBDD getInstance(Context context){
        if (appBBDD == null){
            appBBDD = Room.databaseBuilder(context, AppBBDD.class, "app.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            addUserAdmin();
                        }
                    })
                    .build();
        }
        return appBBDD;
    }

    private static void addUserAdmin(){
        appDao().insertarUser(new User("admin","admin"));

    }


}

