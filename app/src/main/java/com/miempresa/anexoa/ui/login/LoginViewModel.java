package com.miempresa.anexoa.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.Context;


public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> correcto = new MutableLiveData<>();
    private MutableLiveData<Boolean> incorrecto = new MutableLiveData<>();
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context=application;
    }

    public LiveData<Boolean> getCorrecto(){
        if(correcto==null){
            correcto=new MutableLiveData<>();
        }
        return correcto;
    }
    public LiveData<Boolean> getIncorrecto(){
        if(incorrecto==null){
            incorrecto=new MutableLiveData<>();
        }
        return incorrecto;
    }
    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        if(username==null||password==null){
            incorrecto.setValue(false);
        } else if(username.equals("luis")&& password.equals("123")){
            correcto.setValue(true);
        } else{
            incorrecto.setValue(false);
        }


    }




}