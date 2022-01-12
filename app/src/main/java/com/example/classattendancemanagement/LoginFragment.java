package com.example.classattendancemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class LoginFragment extends Fragment {

    TextView txtRegisterNow;
    EditText edTxtLoginEmail, edTxtLoginPass;
    TextView txtForgPass;
    Button btnLogin;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);


        initialise(view);

        txtRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignupTeacher.class);
                startActivity(intent);

            }
        });

        // Setting new password by link from firebase
        txtForgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText getEmail = new EditText(view.getContext());
                AlertDialog.Builder setEmail = new AlertDialog.Builder(view.getContext());
                setEmail.setTitle("Reset Password");
                setEmail.setMessage("Enter your \"EMAIL\" to receive reset link");
                setEmail.setView(getEmail);

                setEmail.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String mail = getEmail.getText().toString();
                        AttenderFireBase.fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Reset password link has been sent to your email", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getContext(), "Error occured try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                setEmail.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                setEmail.create().show();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edTxtLoginEmail.getText().toString().trim();
                String pass = edTxtLoginPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edTxtLoginEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    edTxtLoginPass.setError("Password is required");
                    return;
                }

                // Authenticate user
                AttenderFireBase.fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AttenderFireBase.dRefTea = AttenderFireBase.cRefTea.document(AttenderFireBase.fAuth.getCurrentUser().getUid());
                            Toast.makeText(getActivity(), "User Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Teacher.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Invalid credentials or check your internet and try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        return view;

    }
    public void initialise(ViewGroup view) {
        txtRegisterNow = view.findViewById(R.id.txtRegisterNow);


        edTxtLoginEmail = view.findViewById(R.id.edTxtLoginEmail);
        edTxtLoginPass = view.findViewById(R.id.edTxtLoginPass);

        txtForgPass = view.findViewById(R.id.txtForgPass);
        btnLogin = view.findViewById(R.id.btnLogin);
    }



}
