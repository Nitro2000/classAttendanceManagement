package com.example.classattendancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class SignupTeacher extends AppCompatActivity {

    Button btnTeacherRegis;
    TextView txtLoginNow;
    EditText edTxtRegName, edTxtRegEmail, edTxtRegDepName, edTxtRegPass;
    DocumentReference docRef;
    private String FieldTeacher = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_teacher);

        initalise();

        btnTeacherRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edTxtRegName.getText().toString().trim();
                String email = edTxtRegEmail.getText().toString().trim();
                String depName = edTxtRegDepName.getText().toString().trim();
                String pass = edTxtRegPass.getText().toString().trim();

                if (name.equals("") || depName.equals("")) {
                    Toast.makeText(SignupTeacher.this, "Fill fields properly", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    edTxtRegEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    edTxtRegPass.setError("Password is required");
                    return;
                }
                if (pass.length() < 6) {
                    edTxtRegPass.setError("Password must be 6 letter long");
                }

                // Firebase authentication begin
                AttenderFireBase.fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AttenderFireBase.dRefTea = AttenderFireBase.cRefTea.document(AttenderFireBase.fAuth.getCurrentUser().getUid());

                            Map<String, Object> docTeaMap = new HashMap<>();
                            docTeaMap.put("Name", name);
                            docTeaMap.put("E-mail", email);
                            docTeaMap.put("Department", depName);
                            docTeaMap.put("ImageUri", "https://img.freepik.com/free-vector/teacher-concept-illustration_114360-2166.jpg?size=338&ext=jpg&ga=GA1.2.2103811857.1630281600");

                            AttenderFireBase.dRefTea.set(docTeaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(FieldTeacher, "onSuccess : teacher profile is created");
                                }
                            });

                            Toast.makeText(SignupTeacher.this, "User Register Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupTeacher.this, Teacher.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignupTeacher.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        txtLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupTeacher.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initalise() {

        btnTeacherRegis = findViewById(R.id.btnTeacherRegis);

        txtLoginNow = findViewById(R.id.txtLoginNow);

        edTxtRegName = findViewById(R.id.edTxtRegName);
        edTxtRegEmail = findViewById(R.id.edTxtRegEmail);
        edTxtRegDepName = findViewById(R.id.edTxtRegDepName);
        edTxtRegPass = findViewById(R.id.edTxtRegPass);



    }
}