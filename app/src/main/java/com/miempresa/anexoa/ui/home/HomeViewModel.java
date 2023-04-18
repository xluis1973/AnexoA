package com.miempresa.anexoa.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.miempresa.anexoa.R;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HomeViewModel extends AndroidViewModel {
    private FusedLocationProviderClient fused;
    private  LatLng YO;
    private Context context;
    private MutableLiveData<MapaActual> mapa;
    private MutableLiveData<LatLng> ubicacion;



    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();

    }

    public LiveData<MapaActual> getMapa(){
        if(mapa==null){
            mapa=new MutableLiveData<>();
        }
        return mapa;
    }

    public LiveData<LatLng> getUbicacion(){
        if(ubicacion==null){
            ubicacion=new MutableLiveData<>();
        }
        return ubicacion;
    }

public void construirMapa(LatLng latLng){

        this.YO=latLng;
        mapa.setValue(new MapaActual());

}

    public class MapaActual implements OnMapReadyCallback {


        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(YO).title("ULP"));
            CameraPosition camPos=new CameraPosition.Builder()
                    .target(YO)
                    .zoom(19)
                    .bearing(45)
                    .tilt(70)
                    .build();
            CameraUpdate update= CameraUpdateFactory.newCameraPosition(camPos);
            googleMap.animateCamera(update);

        }
    }

    public void obtenerUltimaUbicacion() {
        //Obtener lectura de ubicación más reciente.
        fused = LocationServices.getFusedLocationProviderClient(context);

        @SuppressLint("MissingPermission") Task<Location> tarea = fused.getLastLocation();
        tarea.addOnSuccessListener( getApplication().getMainExecutor(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    Log.d("salida ",location.getLongitude()+"");
                    YO=new LatLng(location.getLatitude(),location.getLongitude());
                    ubicacion.postValue(YO);


                }
            }
        });
        Log.d("salida hilo principal ","Salida");
    }
}