package com.alphitardian.tr_pam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alphitardian.tr_pam.models.UserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    EditText fName, uName, email, password, address;
    String _fName, _uName, _email, _password, _address;
    Button _btnSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        fName = findViewById(R.id.regisEditTextFullname);
        uName = findViewById(R.id.regisEditTextUsername);
        email = findViewById(R.id.regisEditTextEmail);
        password = findViewById(R.id.regisEditTextPassword);
        address = findViewById(R.id.regisEditTextAddress);
        _btnSignUp = findViewById(R.id.btnRegistration);

        _btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpOnClick();
            }
        });
    }

    public void signUpOnClick() {

        _fName = fName.getText().toString();
        _uName = uName.getText().toString();
        _email = email.getText().toString();
        _password = password.getText().toString();
        _address = address.getText().toString();

        if (TextUtils.isEmpty(_fName) || TextUtils.isEmpty(_uName) || TextUtils.isEmpty(_password) || TextUtils.isEmpty(_address) || TextUtils.isEmpty(_email)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.blank_form_toast),
                    Toast.LENGTH_SHORT).show();
        } else {
            signUp(_email, _password, _fName, _uName, _address);
        }
    }

    public void signUp(String email, String password, String fullName, String userName, String address) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        UserDetail userDetail = new UserDetail(userName, fullName, address, "default");

                        if (task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();
                            db.collection("users").document(uid).set(userDetail);

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.sign_up_failed_toast),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
