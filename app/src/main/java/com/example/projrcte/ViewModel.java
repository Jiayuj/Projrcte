package com.example.projrcte;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;


public class ViewModel extends AndroidViewModel {

    private AppDao appDao;
    User existe;
    User correct;
    public ViewModel(@NonNull Application application) {
        super(application);
        appDao = AppBBDD.getInstance(application).appDao();
    }

    public User comprobarAccer (final String email, final String pass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                correct=appDao.findByEmailAndPass(email,pass);
            }
        });
        return correct;
    }

    public User comprobar(final String email, final String pass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                try {
                    existe = appDao.findByEmail(email);
//                    existe =true;
//                    Log.e("SS", "Email existe ");
//                }catch (NullPointerException e){
//                    existe=false;
//                }
            }
        });
        return existe;
    }
    public void registraUser(final String email, final String pass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDao.insertarUser(new User(email, pass));
            }
        });
    }
    public void mostra(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (User user : appDao.getAll()) {
                Log.e("SS", "Email: " + user.getEmail() + " Pass: " + user.getPass() );
                }
            }
        });
    }
}
