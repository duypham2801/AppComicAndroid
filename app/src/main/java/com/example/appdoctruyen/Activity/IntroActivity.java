package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.appdoctruyen.R;


public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        startApp();
    }
    private void startApp()
    {
        CountDownTimer Timer = new CountDownTimer(3000, 3000)
        {
            public void onTick(long millisUntilFinished) { }

            public void onFinish()
            {
                startActivity(new Intent(IntroActivity.this, MainInterface.class));
            }
        }.start();

    }
}
