package com.example.emask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class PantallaInicial extends AppCompatActivity {

    private ImageView imagenClic;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        imagenClic=(ImageView) findViewById(R.id.imageView2);

    }

    public void btnPantallaSGT(View view){
        Intent sgtSesion= new Intent(this, screenPersonasContact.class);
        startActivity(sgtSesion);
    }

    public void btnCerrarSesion(View view){
        mAuth.signOut();
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        mGoogleSignInClient.signOut();
        Intent cerrarSession= new Intent(this, inicioSesion.class);
        startActivity(cerrarSession);
        finish();
    }

    /*private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }*/







}