package com.example.emask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EstadoMascarilla extends AppCompatActivity {
    DatabaseReference db_reference;
    TextView txt_estado;
    Button btn_regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_mascarilla);
        db_reference = FirebaseDatabase.getInstance().getReference().child("RegistroDatos").child("SmartGuard1");
        txt_estado = (TextView) findViewById(R.id.est_mascarilla);
        btn_regresar =(Button) findViewById(R.id.btn_volver);
        leerRegistro();
    }
    public void leerRegistro(){
        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> listaEstado = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String estado= String.valueOf(dataSnapshot.child("Estado").getValue());
                    listaEstado.add(estado);
                }
                txt_estado.setText(listaEstado.get(listaEstado.size()-1));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.toException());
            }
        });
    }
    public void regresar(View v){
        Intent intent = new Intent(this, PantallaInicial.class);
        startActivity(intent);
        finish();
    }

}