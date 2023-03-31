package com.official.mawa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Utils.blackiconStatus(splash.this,R.color.white);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Home = new Intent(splash.this,MainActivity.class);
                startActivity(Home);
                finish();
            }
        },1500);


    }
}