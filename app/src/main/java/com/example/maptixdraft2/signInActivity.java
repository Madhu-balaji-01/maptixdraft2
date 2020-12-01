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

public class signInActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        EditText signInUser;
        EditText signInPassword;
        Button signIn_button;

        signInUser = findViewById(R.id.signinUsername);
        signInPassword = findViewById(R.id.signinPassword);
        signIn_button = findViewById(R.id.signinButton);

        String signpUser = signInUser.getText().toString();
        String signupPassword = signInPassword.getText().toString();

        signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clicksigninIntent = new Intent(signInActivity.this, Homepage.class);
                startActivity(clicksigninIntent);
            }
        });


    }


}
