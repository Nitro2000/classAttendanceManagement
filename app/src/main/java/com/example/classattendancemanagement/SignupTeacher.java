package com.example.classattendancemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupTeacher extends AppCompatActivity {

    Button btnTeacherRegis;
    TextView txtLoginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_teacher);

        btnTeacherRegis = findViewById(R.id.btnTeacherRegis);

        btnTeacherRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupTeacher.this, Teacher.class);
                startActivity(intent);
            }
        });
        txtLoginNow = findViewById(R.id.txtLoginNow);
        txtLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupTeacher.this, LoginTeacher.class);
                startActivity(intent);
            }
        });
    }
}