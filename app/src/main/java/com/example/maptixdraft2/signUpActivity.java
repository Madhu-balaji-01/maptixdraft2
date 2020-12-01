package com.example.maptixdraft2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class signUpActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText signUpUser;
        final EditText signUpPassword;
        Button signUp_button;
        TextView signin;

        signUpUser = findViewById(R.id.signupUsername);
        signUpPassword = findViewById(R.id.signupPassword);
        signUp_button = findViewById(R.id.signupButton);
        signin = findViewById(R.id.signinhere);




        final Firebase.booleanCallbackInterface signupCallback = new Firebase.booleanCallbackInterface() {
            @Override
            public void onCallback(boolean userExists) {
                Log.i("Kewen signup","inside signup callback");

                if(userExists) {
                    Toast.makeText(signUpActivity.this, "User exist, please sign in!", Toast.LENGTH_SHORT).show();
                    Log.i("Kewen Sign Up","EXISTS");
                }
                else  {
                    Toast.makeText(signUpActivity.this, "User created!", Toast.LENGTH_SHORT).show();
                    Log.i("Kewen Sign Up","NO EXISTS");
                    Intent signinIntent = new Intent(signUpActivity.this, signInActivity.class);
                    startActivity(signinIntent);

                }

            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = new Intent(signUpActivity.this, signInActivity.class);
                startActivity(signinIntent);
            }
        });

        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String signupUser = signUpUser.getText().toString();
                final String signupPassword = signUpPassword.getText().toString();
                Log.i("username",signupUser);
                Log.i("paddword",signupPassword);
                Log.i("Kewen signup","triggered signup");
                Firebase.signUp(signupCallback, signupUser , signupPassword);
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
