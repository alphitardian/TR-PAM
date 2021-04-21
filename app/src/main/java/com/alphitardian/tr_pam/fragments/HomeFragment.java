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
import android.widget.TextView;

import com.alphitardian.tr_pam.R;

public class HomeFragment extends Fragment {

    private TextView textView;

    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pref = this.getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String fullName = pref.getString("fullName", "full name");
        textView = view.findViewById(R.id.home_textview);

        textView.setText(fullName);
    }
}