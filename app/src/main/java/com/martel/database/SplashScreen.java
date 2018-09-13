package com.martel.database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends BaseActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences =
                getSharedPreferences("user_preferences", MODE_PRIVATE);
        if (preferences.contains("ja_abriu_app")) {
            if (mAuth.getCurrentUser() != null) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user.isEmailVerified()){
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
                startActivity(new Intent(SplashScreen.this, EsperaActivity.class));
                finish();
            }
        } else {
            mostrarSplash();
        }

    }

    private void mostrarSplash() {
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if (user.isEmailVerified()){
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
                startActivity(new Intent(SplashScreen.this, SignInActivity.class));
                finish();

            }
        }, 2000);
    }
    private void adicionarPreferenceJaAbriu(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("ja_abriu_app", true);
        editor.commit();
    }


}
