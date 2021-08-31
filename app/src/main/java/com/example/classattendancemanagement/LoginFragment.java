package com.example.classattendancemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    EditText edTxtLoginEmail, edTxtLoginPass;
    TextView txtForgPass;
    Button btnLogin;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        intinalise(root);

        edTxtLoginEmail.setTranslationY(300);
        edTxtLoginPass.setTranslationY(300);
        txtForgPass.setTranslationY(300);
        btnLogin.setTranslationY(300);

        edTxtLoginEmail.setAlpha(v);
        edTxtLoginPass.setAlpha(v);
        txtForgPass.setAlpha(v);
        btnLogin.setAlpha(v);


        edTxtLoginEmail.animate().translationY(0).alpha(1).setDuration(900).setStartDelay(600).start();
        edTxtLoginPass.animate().translationY(0).alpha(1).setDuration(1200).setStartDelay(900).start();
        txtForgPass.animate().translationY(0).alpha(1).setDuration(1500).setStartDelay(1200).start();
        btnLogin.animate().translationY(0).alpha(1).setDuration(1800).setStartDelay(1500).start();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Teacher.class);
                startActivity(intent);
            }
        });

        return root;
    }
    public void intinalise(ViewGroup root) {
        edTxtLoginEmail = root.findViewById(R.id.edTxtLoginEmail);
        edTxtLoginPass = root.findViewById(R.id.edTxtLoginPass);

        txtForgPass = root.findViewById(R.id.txtForgPass);
        btnLogin = root.findViewById(R.id.btnLogin);
    }
}
