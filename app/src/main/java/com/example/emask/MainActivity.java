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
        onStart();
    }
    public void btnComenzar(View view){
        Intent sgtSesion= new Intent(this, inicioSesion.class);
        startActivity(sgtSesion);
    }


    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pref = preferences.getString("UsuarioJSon","");
        if(!pref.equals("")){
            startActivity(new Intent(this, PantallaInicial.class));
        }
    }

    public void alertaMascarilla(){
        if(contadorPersonas>50){
            Toast.makeText(MainActivity.this, "ALERTA: CAMBIAR MASCARILLA", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "MASCARILLA UTILIZABLE", Toast.LENGTH_SHORT).show();
        }
    }



}