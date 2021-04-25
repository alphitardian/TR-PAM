package com.alphitardian.tr_pam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnSignIn;
    LinearLayout loading;

    String _email, _password;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.btnLogin);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInOnClick();
            }
        });
    }

    public void signInOnClick() {
        loading.setVisibility(View.VISIBLE);

        _email = email.getText().toString();
        _password = password.getText().toString();

        if (TextUtils.isEmpty(_email) || TextUtils.isEmpty(_password)) {
            Toast.makeText(LoginActivity.this, getResources().getText(R.string.blank_form_toast),
                    Toast.LENGTH_SHORT).show();
        } else {
            signIn(_email, _password);
        }
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String Uid = task.getResult().getUser().getUid();
                            String email = task.getResult().getUser().getEmail();
                            DocumentReference docRef = db.collection("users").document(Uid);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot snapshot = task.getResult();
                                        if (snapshot.exists()) {
                                            String fName, uName, address, photo_path;

                                            fName = snapshot.getString("fullName");
                                            uName = snapshot.getString("username");
                                            address = snapshot.getString("address");
                                            photo_path = snapshot.getString("photo_path");

                                            editor.putBoolean("LoggedIn", true);
                                            editor.putString("userId", Uid);
                                            editor.putString("email", email);
                                            editor.putString("fullName", fName);
                                            editor.putString("username", uName);
                                            editor.putString("address", address);
                                            editor.putString("photo_path", photo_path);
                                            editor.apply();

                                            loading.setVisibility(View.GONE);

                                            startActivity(new Intent(LoginActivity.this, AddPinLogin.class));
                                            finish();
                                        }
                                    }
                                }
                            });

                        } else {
                            loading.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, getResources().getText(R.string.wrong_username_password_toast),
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
