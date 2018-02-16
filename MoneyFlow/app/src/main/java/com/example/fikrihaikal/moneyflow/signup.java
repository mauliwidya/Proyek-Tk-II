package com.example.fikrihaikal.moneyflow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    EditText etusername, etpassword;
    TextView tvready;
    Button btsignUp;
    ProgressBar suprogress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tvready = findViewById(R.id.tv_dontalready);
        etusername = findViewById(R.id.login_email);
        etpassword = findViewById(R.id.login_password);
        btsignUp = findViewById(R.id.email_sign_in_button);
        suprogress = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();

        btsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        tvready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void registerUser(){
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString().trim();
        if (username.isEmpty()){
            etusername.setError("Email is Require");
            etusername.requestFocus();
            return;
        }
//        if (Patterns.EMAIL_ADDRESS.matcher(username).matches()){
//            etusername.setError("Please Enter a Valid Email");
//            etusername.requestFocus();
//            return;
//        }
        if (password.isEmpty()){
            etpassword.setError("Password is Require");
            etpassword.requestFocus();
            return;
        }
        if (password.length()<6){
            etpassword.setError("Password is too short");
            etpassword.requestFocus();
            return;
        }
        suprogress.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                suprogress.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(signup.this,Profile.class));
                    Toast.makeText(getApplicationContext(),"User Regiter Succesfull",Toast.LENGTH_SHORT).show();
                }else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You are already regiter",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
