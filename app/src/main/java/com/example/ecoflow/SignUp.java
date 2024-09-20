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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    //Variables
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button callLogIn, signup_btn;
    ImageView regImage;
    TextView regText, regSlogan;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        callLogIn = findViewById(R.id.login_screen);
        regImage = findViewById(R.id.reg_image);
        regText = findViewById(R.id.reg_title);
        regSlogan = findViewById(R.id.reg_slogan);
        regName = findViewById(R.id.reg_name);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        signup_btn = findViewById(R.id.reg_btn);

        callLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(regImage,"logo_image");
                pairs[1] = new Pair<View,String>(regText,"logo_text");
                pairs[2] = new Pair<View,String>(regSlogan,"logo_desc");
                pairs[3] = new Pair<View,String>(regUsername,"username_tran");
                pairs[4] = new Pair<View,String>(regPassword,"password_tran");
                pairs[5] = new Pair<View,String>(signup_btn,"button_tran");
                pairs[6] = new Pair<View,String>(callLogIn,"login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                startActivity(intent, options.toBundle());

            }
        });
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field Cannot be Empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field Cannot be Empty");
            return false;
        }

        else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        }

        else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White spaces aren't allowed");
            return false;
        }

        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field Cannot be Empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field Cannot be Empty");
            return false;
        }

        else if (val.length() > 10) {
            regPhoneNo.setError("Invalid Phone Number");
            return false;
        }

        else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPassword.setError("Field Cannot be Empty");
            return false;
        }

        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {

        if (!validateName() | !validateUsername() | !validateEmail() | !validatePassword() | !validatePhoneNo()) {
            return;
        } else {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("User");

            //Get all the values in String
            String name = regName.getEditText().getText().toString();
            String username = regUsername.getEditText().getText().toString();
            String email = regEmail.getEditText().getText().toString();
            String phoneNo = regPhoneNo.getEditText().getText().toString();
            String password = regPassword.getEditText().getText().toString();

            UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);

            reference.child(username).setValue(helperClass);

            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
        }
    }


}