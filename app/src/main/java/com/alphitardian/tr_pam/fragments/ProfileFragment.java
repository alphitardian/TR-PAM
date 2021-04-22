package com.alphitardian.tr_pam.fragments;

import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphitardian.tr_pam.EditProfileActivity;
import com.alphitardian.tr_pam.LoginActivity;
import com.alphitardian.tr_pam.MapsActivity;
import com.alphitardian.tr_pam.R;

import com.alphitardian.tr_pam.RegisterActivity;

import com.alphitardian.tr_pam.WalletActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private final int EDITPROFILE_ACTIVITY_REQ_CODE = 3;

    private TextView fullNameTextView, usernameTextView, emailTextView, addressTextView;
    private ImageView profileImage, editButton, walletButton;

    private Button signOutButton;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pref = this.getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        editor = pref.edit();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullNameTextView = view.findViewById(R.id.full_name_textview);
        usernameTextView = view.findViewById(R.id.username_textview);
        emailTextView = view.findViewById(R.id.email_textview);
        addressTextView = view.findViewById(R.id.address_textview);
        profileImage = view.findViewById(R.id.profile_image);
        editButton = view.findViewById(R.id.edit_button);
        walletButton = view.findViewById(R.id.wallet_button);
        signOutButton = view.findViewById(R.id.signOutButton);

        fullNameTextView.setText(pref.getString("fullName", "fullName"));
        usernameTextView.setText(pref.getString("username", "Username"));
        emailTextView.setText(pref.getString("email", "Email"));
        addressTextView.setText(pref.getString("address", "Address"));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivityForResult(intent, EDITPROFILE_ACTIVITY_REQ_CODE);
            }
        });

        walletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITPROFILE_ACTIVITY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                fullNameTextView.setText(pref.getString("fullName", "fullName"));
                usernameTextView.setText(pref.getString("username", "Username"));
                emailTextView.setText(pref.getString("email", "Email"));
                addressTextView.setText(pref.getString("address", "Address"));
            }
        }
    }
}