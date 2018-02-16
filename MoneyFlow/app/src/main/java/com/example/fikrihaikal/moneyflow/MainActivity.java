package com.example.fikrihaikal.moneyflow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    TextView tvdontalready;
    EditText txemail,txpassword;
    Button btnsignin;
    ProgressBar progress;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvdontalready = findViewById(R.id.tv_dontalready);
        txemail = findViewById(R.id.login_email);
        txpassword = findViewById(R.id.login_password);
        progress = findViewById(R.id.login_progress);
        btnsignin = findViewById(R.id.email_sign_in_button);

        mAuth = FirebaseAuth.getInstance();
        tvdontalready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(),signup.class);
                startActivity(i);
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,MainPage.class));
        }
    }

    private void userLogin() {
        String username = txemail.getText().toString();
        String password = txpassword.getText().toString();
        if (username.isEmpty()){
            txemail.setError("Email is Require");
            txemail.requestFocus();
            return;
        }
//        if (Patterns.EMAIL_ADDRESS.matcher(username).matches()){
//            etusername.setError("Please Enter a Valid Email");
//            etusername.requestFocus();
//            return;
//        }
        if (password.isEmpty()){
            txpassword.setError("Password is Require");
            txpassword.requestFocus();
            return;
        }
        if (password.length()<6){
            txpassword.setError("Password is too short");
            txpassword.requestFocus();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    finish();
                    Intent i = new Intent(MainActivity.this, Profile.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
