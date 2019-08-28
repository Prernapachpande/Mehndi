package com.example.mehndi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prernapachpande.Mehndi.R;

public class Splash extends AppCompatActivity {
    public static int Splash_Time_out=4000;
    TextView splash;
    Animation slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (TextView) findViewById(R.id.text);
        slide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_top);
        splash.setVisibility(View.VISIBLE);
        splash.startAnimation(slide);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(Splash.this,MainActivity.class);
                startActivity(splash);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        },Splash_Time_out);

    }
}
