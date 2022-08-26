package com.example.emask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.emask.services.AlarmaCercania;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantallaInicial extends AppCompatActivity {
    private DatabaseReference db_reference;
    private ImageView imagenClic;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);
        db_reference = FirebaseDatabase.getInstance().getReference().child("RegistroDatos").child("SmartGuard1");
        imagenClic=(ImageView) findViewById(R.id.imageView2);
        context=this.getApplicationContext();
        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int cont=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String dato = String.valueOf(dataSnapshot.child("Distancia").getValue());
                    System.out.println(dato);
                    String datoactual= dato.replace("\r\n","");
                    long datoInt = Integer.parseInt(datoactual);
                    if(datoInt<=200){
                        cont++;
                    }
                }
                if (cont >= 50) {
                    alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context, AlarmaCercania.class);
                    alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                    alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime() +
                                    5000, alarmIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void btnPantallaSGT(View view){
        Intent sgtSesion= new Intent(this, screenPersonasContact.class);
        startActivity(sgtSesion);
    }
    public void estado_mascarilla(View v){
        Intent sgtSesion = new Intent(this,EstadoMascarilla.class);
        startActivity(sgtSesion);
    }
    public void detalleInteraccion(View v){
        Intent sgtSesion = new Intent(this,DetalleInteraccion.class);
        startActivity(sgtSesion);
    }

    public void btnCerrarSesion(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent cerrarSession= new Intent(this, inicioSesion.class);
        cerrarSession.putExtra("msg","cerrarSesion");
        startActivity(cerrarSession);

    }
}