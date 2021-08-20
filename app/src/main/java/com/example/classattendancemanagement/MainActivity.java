package com.example.classattendancemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView civTeacher, civStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        civTeacher = findViewById(R.id.civStudent);

        civTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupStudent.class);
                MainActivity.this.startActivity(intent);

            }
        });

        civStudent = findViewById(R.id.civStudent);

        civStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupTeacher.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}