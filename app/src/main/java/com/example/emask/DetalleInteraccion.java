package com.example.emask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetalleInteraccion extends AppCompatActivity {
    DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_interaccion);
        db_reference= FirebaseDatabase.getInstance().getReference().child("RegistroDatos").child("SmartGuard1");
        Tabla tabla = new Tabla(this,(TableLayout) findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);

        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add(Integer.toString(i));
                    elementos.add(dataSnapshot.child("Distancia").getValue().toString());
                    elementos.add(dataSnapshot.child("Fecha:").getValue().toString());
                    elementos.add(dataSnapshot.child("Hora:").getValue().toString());
                    tabla.agregarFilaTabla(elementos);
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.toException());

            }
        });
    }
}