package com.example.emask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class inicioSesion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText emailUsuario;
    private EditText passUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        emailUsuario=(EditText)findViewById(R.id.correoUsuario);
        passUsuario=(EditText)findViewById(R.id.passwordUsuario);
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void iniciarSesion(View v){
        String email= emailUsuario.getText().toString();
        if(!emailUsuario.getText().toString().trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!passUsuario.getText().toString().trim().isEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailUsuario.getText().toString(),
                        passUsuario.getText().toString()).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            //goToPantallaInicial();
                        } else {
                            // If sign in fails, display a message to the user.
                            showAlert();
                        }
                    }
                });
            }else{
                Toast.makeText(this, "Error: Ingrese una contraseña por favor",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Error: Email vacío o estructura de email inválida",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void btnSesionGoogle(View v){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
        startActivityForResult(mGoogleSignInClient.getSignInIntent(),RC_SIGN_IN);
        //goToPantallaInicial();
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            String email = user.getEmail();
            goToPantallaInicial();

        }else{
            System.out.println("sin registrarse");
        }

    }

    public void goToPantallaInicial(){
        Intent gtscreenInicial= new Intent(this, PantallaInicial.class);
        startActivity(gtscreenInicial);
    }

    public void showAlert(){
        Toast.makeText(this, "Error: Usuario no existe",
                Toast.LENGTH_SHORT).show();
    }

    public void btnRegistro(View view){
        Intent sgtSesion= new Intent(this, registroUsuario.class);
        startActivity(sgtSesion);
    }






}