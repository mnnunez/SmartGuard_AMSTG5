package com.example.emask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int contadorPersonas=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void btnComenzar(View view){
        Intent sgtSesion= new Intent(this, inicioSesion.class);
        startActivity(sgtSesion);
    }
}