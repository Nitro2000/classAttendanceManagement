package com.example.classattendancemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    ImageView civTeacher, civStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AttenderFireBase.setfAuth(AttenderFireBase.getfAuth());
        AttenderFireBase.setfStore(AttenderFireBase.getfStore());

        civTeacher = findViewById(R.id.civTeacher);


        civTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttenderFireBase.setcRefTea(AttenderFireBase.fStore.collection("Teachers"));
                Intent intent = new Intent(MainActivity.this, LoginTeacher.class);
                MainActivity.this.startActivity(intent);

            }
        });

        civStudent = findViewById(R.id.civStudent);

        civStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupStudent.class);
                MainActivity.this.startActivity(intent);
            }
        });


    }
}