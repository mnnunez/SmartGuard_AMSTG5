package com.example.emask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class registroUsuario extends AppCompatActivity {

    private EditText campoNombre,campoApellidos,campoCorreoR,campoPasswordR;
    private ImageView imagenClic;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        campoNombre=(EditText)findViewById(R.id.campoNombre);
        campoApellidos=(EditText)findViewById(R.id.campoApellidos);
        campoCorreoR=(EditText)findViewById(R.id.campoCorreoR);
        campoPasswordR=(EditText)findViewById(R.id.campoPasswordR);
        mAuth=FirebaseAuth.getInstance();
    }

    public void btnRegistroUsuario(View view){
        Intent sgtSesion= new Intent(this, inicioSesion.class);
        startActivity(sgtSesion);
    }

    public void btnSesion(View view){
        Intent sgtSesion= new Intent(this, inicioSesion.class);
        startActivity(sgtSesion);
    }

    public void succesRegister(View v){
        String email=campoCorreoR.getText().toString();
        if(!campoNombre.getText().toString().trim().isEmpty() && !campoApellidos.getText().toString().trim().isEmpty() && !campoCorreoR.getText().toString().trim().isEmpty()
                && !campoPasswordR.getText().toString().trim().isEmpty()){
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(campoCorreoR.getText().toString(),
                        campoPasswordR.getText().toString()).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            goToPantallaInicial();
                        } else {
                            // If sign in fails, display a message to the user.
                            showAlert();
                        }
                    }

                    DocumentReference collection = db.collection("users").document(email);
                });
            }else{
                Toast.makeText(this, "Estructura de email inválida",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Por favor, rellene todos los campos",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void goToPantallaInicial(){
        Intent ingresoCorrecto= new Intent(this,PantallaInicial.class);
        startActivity(ingresoCorrecto);
    }

    public void showAlert(){
        Toast.makeText(this, "Error: No se pudo realizar el registro correctamente)",
                Toast.LENGTH_SHORT).show();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }














}