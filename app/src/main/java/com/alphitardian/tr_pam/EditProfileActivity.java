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

import com.alphitardian.tr_pam.models.UserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    EditText fName, uName, email, password, address, confirmPassword;
    String _fName, _uName, _email, _password, _address, _confirmPassword;
    Button _btnSaveProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_screen);

        fName = findViewById(R.id.newEditTextFullname);
        uName = findViewById(R.id.newEditTextUsername);
        email = findViewById(R.id.newEditTextEmail);
        password = findViewById(R.id.newEditTextPassword);
        confirmPassword = findViewById(R.id.newEditTextConfirmPassword);
        address = findViewById(R.id.newEditTextAddress);
        _btnSaveProfile = findViewById(R.id.btnSaveProfile);

        _btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditProfile();
            }
        });
    }

    public void saveEditProfile(){

        _fName = fName.getText().toString();
        _uName = uName.getText().toString();
        _email = email.getText().toString();
        _password = password.getText().toString();
        _confirmPassword = password.getText().toString();
        _address = address.getText().toString();

        if(TextUtils.isEmpty(_fName) || TextUtils.isEmpty(_uName) || TextUtils.isEmpty(_password) || TextUtils.isEmpty(_address) || TextUtils.isEmpty(_email)){
            Toast.makeText(EditProfileActivity.this, "Fill the blank form!",
                    Toast.LENGTH_SHORT).show();
        }else{
            saveProfile(_email, _password, _fName, _uName, _address);
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void saveProfile(String email, String password, String fullName, String userName, String address) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        UserDetail userDetail = new UserDetail(userName, fullName, address, "default");
//
//                        if (task.isSuccessful()) {
//                            String uid = task.getResult().getUser().getUid();
//                            db.collection("users").document(uid).set(userDetail);
//
//                            startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
//                        } else {
//                            Toast.makeText(EditProfileActivity.this, "Change not save",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        if (TextUtils.isEmpty(_confirmPassword)) {
            Toast.makeText(EditProfileActivity.this, "Password is not equal", Toast.LENGTH_LONG);
        } else {
            user.updateEmail(email);
            user.updatePassword(password);
            UserDetail userDetail = new UserDetail(userName, fullName, address, "default");
            String uid = user.getUid();
            db.collection("users").document(uid).set(userDetail);
        }

    }
}
