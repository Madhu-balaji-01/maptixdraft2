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

public class signInActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        final EditText signInUser;
        final EditText signInPassword;
        Button signIn_button;

        signInUser = findViewById(R.id.signinUsername);
        signInPassword = findViewById(R.id.signinPassword);
        signIn_button = findViewById(R.id.signinButton);





        final Firebase.stringCallbackInterface signinCallback = new Firebase.stringCallbackInterface() {
            @Override
            public void onCallback(String string) {

                switch (string) {
                    case ("correct"):
                        Log.i("Kewen signin","inside signin, correct password");
                        Toast.makeText(signInActivity.this, "User login!", Toast.LENGTH_SHORT).show();

                        String signinUser = signInUser.getText().toString();
                        Intent clicksigninIntent = new Intent(signInActivity.this, Homepage.class);
                        clicksigninIntent.putExtra("username", signinUser);
                        startActivity(clicksigninIntent);
                        break;
                    case ("incorrect"):
                        Log.i("Kewen signin","inside signin, wrong password");
                        Toast.makeText(signInActivity.this, "Incorrect password!", Toast.LENGTH_LONG).show();
                        break;

                    case ("signup"):
                        Log.i("Kewen signin","inside signin, user no exist");
                        Toast.makeText(signInActivity.this, "User does not exist!", Toast.LENGTH_LONG).show();
                        break;
                }

            }
        };

        signIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signinUser = signInUser.getText().toString();
                String signinPassword = signInPassword.getText().toString();
                Log.i("Kewen signin","signin triggered");
                Firebase.signIn(signinCallback, signinUser,signinPassword );
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
