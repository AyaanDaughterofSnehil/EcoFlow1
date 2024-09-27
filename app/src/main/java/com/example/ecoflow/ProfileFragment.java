package com.example.ecoflow;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class ProfileFragment extends Fragment {

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, usernameLabel, waterLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize your views here
        fullName = view.findViewById(R.id.full_name_profile);
        email = view.findViewById(R.id.email_profile);
        phoneNo = view.findViewById(R.id.phone_no_profile);
        password = view.findViewById(R.id.password_profile);
        fullNameLabel = view.findViewById(R.id.fullname_field);
        usernameLabel = view.findViewById(R.id.username_field);

        waterLevel = view.findViewById(R.id.remaining_water);

        showAllUserData();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    waterLevel.setText("0.4L");
                }
            }
        }, 4000);

        return view;
    }

    private void showAllUserData() {
        // Get the arguments passed to the fragment
        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            String user_username = intent.getStringExtra("username");
            String user_name = intent.getStringExtra("name");
            String user_email = intent.getStringExtra("email");
            String user_phoneNo = intent.getStringExtra("phoneNo");
            String user_password = intent.getStringExtra("password");

            fullNameLabel.setText(user_name);
            usernameLabel.setText(user_username);
            fullName.getEditText().setText(user_name);
            email.getEditText().setText(user_email);
            phoneNo.getEditText().setText(user_phoneNo);
            password.getEditText().setText(user_password);
        }
    }
}
