package com.miempresa.anexoa.ui.gallery;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class GalleryViewModel extends AndroidViewModel {

private Context context;
    public GalleryViewModel(@NonNull Application application) {
        super(application);
        this.context=getApplication();
    }

    public void iniciarServicio(){
        Intent intent=new Intent(context, ServicioMusical.class);
        context.startService(intent);

   }
}