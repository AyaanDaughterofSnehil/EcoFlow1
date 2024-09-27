package com.example.ecoflow;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logoText,"logo_text");
                pairs[2] = new Pair<View,String>(sloganText,"logo_desc");
                pairs[3] = new Pair<View,String>(username,"username_tran");
                pairs[4] = new Pair<View,String>(password,"password_tran");
                pairs[5] = new Pair<View,String>(login_btn,"button_tran");
                pairs[6] = new Pair<View,String>(callSignUp,"login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent, options.toBundle());

            }
        });

    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field Cannot be Empty");
            return false;
        }

        else if (val.length() >= 15) {
            username.setError("Username too long");
            return false;
        }

        else if (!val.matches(noWhiteSpace)) {
            username.setError("White spaces aren't allowed");
            return false;
        }

        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field Cannot be Empty");
            return false;
        }

        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void signIn(View view) {

        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
            //Intent myIntent = new Intent(Login.this, Dashboard.class);
            //Login.this.startActivity(myIntent);
        }
    }

    private void isUser() {

        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = snapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("phoneNo",phoneNoFromDB);
                        intent.putExtra("password",passwordFromDB);

                        startActivity(intent);

                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("No such User exists");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }
}