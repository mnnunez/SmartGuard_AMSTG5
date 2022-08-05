package com.example.emask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class PantallaInicial extends AppCompatActivity {

    private ImageView imagenClic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        imagenClic=(ImageView) findViewById(R.id.imageView2);

    }

    public void btnPantallaSGT(View view){
        Intent sgtSesion= new Intent(this, inicioSesion.class);
        startActivity(sgtSesion);
    }

}