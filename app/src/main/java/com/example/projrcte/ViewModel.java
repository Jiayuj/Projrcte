package com.example.projrcte;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class ViewModel extends AndroidViewModel {

    AppDao appDao;

    public ViewModel(@NonNull Application application) {
        super(application);

        appDao = AppBBDD.getInstance(application).appDao();
    }

    public void registraUser(final String email, final String pass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDao.insertarUser(new User(email,pass));
            }
        });
    }
}
