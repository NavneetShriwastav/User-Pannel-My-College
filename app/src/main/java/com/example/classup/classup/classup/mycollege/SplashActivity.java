package com.example.classup.classup.classup.mycollege;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.classup.classup.classup.mycollege.R;
import com.example.classup.classup.classup.mycollege.ui.auth.SignIn;
import com.example.classup.classup.classup.mycollege.ui.auth.SignUp;

public class SplashActivity extends AppCompatActivity {
    TextView tv,tv1;
    Animation scale,translate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity after the delay
                Intent mainIntent = new Intent(SplashActivity.this, SignIn.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000);

        tv = findViewById(R.id.tv);
        tv1 = findViewById(R.id.tv1);

        scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        tv.setAnimation(scale);

        translate = AnimationUtils.loadAnimation(this,R.anim.transalate);
        tv1.setAnimation(translate);


    }
}