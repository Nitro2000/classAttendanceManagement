package com.example.classattendancemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginTeacher extends AppCompatActivity {

    ViewPager viewPager;
    TextView txtRegisterNow, txtLog;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);

        txtRegisterNow = findViewById(R.id.txtRegisterNow);
        txtRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginTeacher.this, SignupTeacher.class);
                startActivity(intent);
            }
        });

        txtLog = findViewById(R.id.txtLog);


        viewPager = findViewById(R.id.view_pager_login);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, 1);
        viewPager.setAdapter(adapter);

        txtLog.setTranslationY(300);
        txtRegisterNow.setTranslationY(300);

        txtRegisterNow.setAlpha(v);
        txtLog.setAlpha(v);

        txtRegisterNow.animate().translationY(0).alpha(1).setDuration(2100).setStartDelay(1800).start();
        txtLog.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(300).start();
    }
}