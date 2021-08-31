package com.example.classattendancemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TeacherProfile extends AppCompatActivity {

    TextView txtName, txtEmail, txtDep, edTxtName, edTxtEmail, edTxtDepName;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        initalise();

        txtName.setTranslationY(300);
        txtEmail.setTranslationY(300);
        txtDep.setTranslationY(300);
        edTxtName.setTranslationY(300);
        edTxtEmail.setTranslationY(300);
        edTxtDepName.setTranslationY(300);

        txtName.setAlpha(v);
        txtEmail.setAlpha(v);
        txtDep.setAlpha(v);
        edTxtName.setAlpha(v);
        edTxtEmail.setAlpha(v);
        edTxtDepName.setAlpha(v);

        txtName.animate().translationY(0).alpha(1).setDuration(400).setStartDelay(100).start();
        txtEmail.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(400).start();
        txtDep.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        edTxtName.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(300).start();
        edTxtEmail.animate().translationY(0).alpha(1).setDuration(900).setStartDelay(600).start();
        edTxtDepName.animate().translationY(0).alpha(1).setDuration(1200).setStartDelay(900).start();

    }

    private void initalise() {

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtDep = findViewById(R.id.txtDep);
        edTxtName = findViewById(R.id.edTxtName);
        edTxtEmail = findViewById(R.id.edTxtEmail);
        edTxtDepName = findViewById(R.id.edTxtDepName);


    }
}