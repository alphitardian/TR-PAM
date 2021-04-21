package com.alphitardian.tr_pam.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphitardian.tr_pam.R;

public class ProfileFragment extends Fragment {

    private TextView fullNameTextView, usernameTextView, emailTextView, addressTextView;
    private ImageView profileImage, editButton;

    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pref = this.getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);

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

        fullNameTextView.setText(pref.getString("fullName", "fullName"));
        usernameTextView.setText(pref.getString("username", "Username"));
        emailTextView.setText(pref.getString("email", "Email"));
        addressTextView.setText(pref.getString("address", "Address"));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}