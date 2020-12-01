package com.example.maptixdraft2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class signUpActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_signup);

        EditText signUpUser;
        EditText signUpPassword;
        Button signUp_button;
        TextView signin;

        signUpUser = findViewById(R.id.signupUsername);
        signUpPassword = findViewById(R.id.signupPassword);
        signUp_button = findViewById(R.id.signupButton);
        signin = findViewById(R.id.signinhere);

        String signupUser = signUpUser.getText().toString();
        String signupPassword = signUpPassword.getText().toString();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = new Intent(signUpActivity.this, signInActivity.class);
                startActivity(signinIntent);
            }
        });


    }
}
