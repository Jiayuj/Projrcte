package com.example.projrcte;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projrcte.bd.AppBBDD;
import com.example.projrcte.bd.AppDao;
import com.example.projrcte.model.Restaurante;
import com.example.projrcte.model.User;

import java.util.ArrayList;
import java.util.List;


public class ViewModel extends AndroidViewModel {

    private AppDao appDao;
    String existe;
    User correct;

    public MutableLiveData<List<Restaurante>> listaElementos = new MutableLiveData<>();
    public MutableLiveData<Restaurante> elementoSeleccionado = new MutableLiveData<>();

    public MutableLiveData<List<String>> listaCart = new MutableLiveData<>();
    public MutableLiveData<String> elementoCart = new MutableLiveData<>();
    List<String> restaurantes = new ArrayList<>();

    public ViewModel(@NonNull Application application) {
        super(application);
        rellenarListaElementos();
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

    public String comprobar(final String email, final String pass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                try {
                    existe = appDao.findByEmail(email).getEmail();
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

    public void rellenarListaElementos(){
        List<Restaurante> restaurantes = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Restaurante restaurante = new Restaurante();
            restaurante.id = i;
            restaurante.nombre = "Restaurante " + i;
            restaurante.descripcion = "Descripcion " + i;
            restaurantes.add(restaurante);
        }
        listaElementos.setValue(restaurantes);
    }
    public void establecerElementoSeleccionado(Restaurante restaurante){
        elementoSeleccionado.setValue(restaurante);
    }

    public void rellenarListaCart(String nomTienda){
        restaurantes.add(nomTienda);
        listaCart.setValue(restaurantes);
    }
    public void establecerElementoCart(String nomTienda){
        elementoCart.setValue(nomTienda);
    }
}
