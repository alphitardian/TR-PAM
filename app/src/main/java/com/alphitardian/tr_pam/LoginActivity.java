package com.alphitardian.tr_pam;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnSignIn;

    String _email, _password;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        btnSignIn = findViewById(R.id.btnLogin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInOnClick();
            }
        });
    }

    public void signInOnClick(){
        _email = email.getText().toString();
        _password = password.getText().toString();

        if(TextUtils.isEmpty(_email) || TextUtils.isEmpty(_password)){
            Toast.makeText(LoginActivity.this, "Fill the blank form!",
                    Toast.LENGTH_SHORT).show();
        }else{
            signIn(_email, _password);
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Wrong username or password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
