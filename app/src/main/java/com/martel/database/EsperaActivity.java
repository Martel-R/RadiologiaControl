package com.martel.database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EsperaActivity extends BaseActivity {
    private TextView espera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espera);

        espera = findViewById(R.id.tv_espera);

        verificar();


    }
//    TODO Testar essa verificação

    private void verificar(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        auth.setLanguageCode("pt-br");

        if (user.isEmailVerified()) {
            Toast.makeText(EsperaActivity.this, "Bem vindo!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EsperaActivity.this, MainActivity.class));
            finish();
        }
        espera.setText("Entre no seu email para validar sua conta!");
        try {
            Thread.sleep(120000);
            verificar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
