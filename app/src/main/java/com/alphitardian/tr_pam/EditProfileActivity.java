package com.alphitardian.tr_pam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alphitardian.tr_pam.models.UserDetail;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.alphitardian.tr_pam.utils.PathProvider.getPath;

public class EditProfileActivity extends AppCompatActivity {

    private final int MAPACTIVITY_REQ_CODE = 1;
    private final int GALLERY_IMAGE_REQ_CODE = 4;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    EditText fName, uName, email, password, address, confirmPassword;
    String _fName, _uName, _email, _password, _address, _confirmPassword, _photoPath;
    Button _btnSaveProfile;
    ImageView _btnLocation, _profileImage;

    private Bitmap photoBitmap;

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
        _btnLocation = findViewById(R.id.location_button);
        _profileImage = findViewById(R.id.profile_image);

        preferences = this.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        editor = preferences.edit();

        fName.setText(preferences.getString("fullName", ""));
        uName.setText(preferences.getString("username", ""));
        email.setText(preferences.getString("email", ""));
        address.setText(preferences.getString("address", ""));
        _profileImage.setImageBitmap(photoBitmap);
        Glide.with(getApplicationContext())
                .load(preferences.getString("photo_path", ""))
                .override(200,200)
                .into(_profileImage);

        _btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditProfile();
            }
        });

        _btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(intent, MAPACTIVITY_REQ_CODE);
            }
        });

        _profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_IMAGE_REQ_CODE);
            }
        });
    }

    public void saveEditProfile() {

        _fName = fName.getText().toString();
        _uName = uName.getText().toString();
        _email = email.getText().toString();
        _password = password.getText().toString();
        _confirmPassword = password.getText().toString();
        _address = address.getText().toString();

        if (TextUtils.isEmpty(_fName) || TextUtils.isEmpty(_uName) || TextUtils.isEmpty(_password) || TextUtils.isEmpty(_address) || TextUtils.isEmpty(_email)) {
            Toast.makeText(EditProfileActivity.this, "Fill the blank form!",
                    Toast.LENGTH_SHORT).show();
        } else {
            editor.putString("fullName", _fName);
            editor.putString("username", _uName);
            editor.putString("email", _email);
            editor.putString("address", _address);
            editor.putString("photo_path", _photoPath);
            editor.apply();

            saveProfile(_email, _password, _fName, _uName, _address, _photoPath);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void saveProfile(String email, String password, String fullName, String userName, String address, String photoPath) {

        if (!_confirmPassword.equals(password)) {
            Toast.makeText(EditProfileActivity.this, "Password is not equal", Toast.LENGTH_LONG);
        } else {
            user.updateEmail(email);
            user.updatePassword(password);
            UserDetail userDetail = new UserDetail(userName, fullName, address, photoPath);
            String uid = user.getUid();
            db.collection("users").document(uid).set(userDetail);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAPACTIVITY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                address.setText(data.getStringExtra(MapsActivity.EXTRA_ADDRESS));
            }
        }

        if (requestCode == GALLERY_IMAGE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Glide.with(getApplicationContext())
                        .load(getPath(getApplicationContext(), data.getData()))
                        .override(200,200)
                        .into(_profileImage);
                _photoPath = getPath(getApplicationContext(), data.getData());
                Log.d("IMAGE_PICKER", "onActivityResult: " + _photoPath);
            }
        }
    }
}
