package com.alphitardian.tr_pam;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.alphitardian.tr_pam.models.UserDetail;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static com.alphitardian.tr_pam.utils.PathProvider.getPath;

public class EditProfileActivity extends AppCompatActivity {

    private final int MAPACTIVITY_REQ_CODE = 1;
    private final int PICK_IMAGE_REQUEST = 22;

    private Uri filePath;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseStorage storage;
    StorageReference storageReference;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView changePhoto;
    EditText fName, uName, email, address;
    String _fName, _uName, _email, _password, _address, _photoPath;
    Button _btnSaveProfile;
    ImageView _btnLocation, editProfileImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_screen);

        fName = findViewById(R.id.newEditTextFullname);
        uName = findViewById(R.id.newEditTextUsername);
        email = findViewById(R.id.newEditTextEmail);
        address = findViewById(R.id.newEditTextAddress);
        changePhoto = findViewById(R.id.changePhoto);
        _btnSaveProfile = findViewById(R.id.btnSaveProfile);
        _btnLocation = findViewById(R.id.location_button);
        editProfileImage = findViewById(R.id.editProfileImage);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        preferences = this.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        editor = preferences.edit();

        fName.setText(preferences.getString("fullName", ""));
        uName.setText(preferences.getString("username", ""));
        email.setText(preferences.getString("email", ""));
        address.setText(preferences.getString("address", ""));

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

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
    }
    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                editProfileImage.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == MAPACTIVITY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                address.setText(data.getStringExtra(MapsActivity.EXTRA_ADDRESS));
            }
        }
    }

    public void saveEditProfile() {

        _fName = fName.getText().toString();
        _uName = uName.getText().toString();
        _email = email.getText().toString();
        _address = address.getText().toString();

        if (TextUtils.isEmpty(_fName) || TextUtils.isEmpty(_uName) || TextUtils.isEmpty(_address) || TextUtils.isEmpty(_email)) {
            Toast.makeText(EditProfileActivity.this, "Fill the blank form!",
                    Toast.LENGTH_SHORT).show();
        } else {

            saveProfile(_email, _password, _fName, _uName, _address);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void saveProfile(String email, String password, String fullName, String userName, String address ) {

        if(filePath!= null){

            StorageReference ref = storageReference.child("/images/" + UUID.randomUUID().toString());

            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String path = taskSnapshot.getMetadata().getPath();
                    String photoPath = path;

                    user.updateEmail(email);
                    String uid = user.getUid();

                    UserDetail userDetail = new UserDetail(userName, fullName, address, photoPath);

                    editor.putString("fullName", _fName);
                    editor.putString("username", _uName);
                    editor.putString("email", _email);
                    editor.putString("address", _address);
                    editor.putString("photo_path", photoPath);
                    editor.apply();

                    Log.w("photo path", userDetail.getPhoto_path() + "");
                    db.collection("users").document(uid).set(userDetail);

                    Toast.makeText(EditProfileActivity.this,"Image Uploaded!!",  Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            user.updateEmail(email);
            String uid = user.getUid();
            String photoPath = preferences.getString("photo_path", "default");
            UserDetail userDetail = new UserDetail(userName, fullName, address, photoPath);
            Log.w("photo path", userDetail.getPhoto_path() + "");
            db.collection("users").document(uid).set(userDetail);
        }

    }

}
