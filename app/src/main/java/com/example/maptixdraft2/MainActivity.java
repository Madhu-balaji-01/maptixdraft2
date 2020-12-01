package com.example.maptixdraft2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //send intent over to the homepage class
                Intent intent = new Intent(MainActivity.this, signUpActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}