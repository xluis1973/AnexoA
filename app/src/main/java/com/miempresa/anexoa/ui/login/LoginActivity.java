package com.miempresa.anexoa.ui.login;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miempresa.anexoa.MainActivity;
import com.miempresa.anexoa.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        solicitarPermisos();

        loginViewModel =new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;




        loginViewModel.getCorrecto().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loginResult) {
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
            }
        });

        loginViewModel.getIncorrecto().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loginResult) {
                Toast.makeText(LoginActivity.this,"Usuarios y/o contraseña incorrecto",Toast.LENGTH_LONG).show();

            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }
    public boolean solicitarPermisos(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && (checkSelfPermission(ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)  ||
                (checkSelfPermission(ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},1000);
        }

        return true;
    }

}