package com.example.classattendancemanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TeacherClass extends AppCompatActivity {

    private FloatingActionButton class_float;
    private RecyclerView class_recView;
    private EditText edTxtClassName, edTxtSubName;
    private Button btn_dia_cancel, btn_dia_add;
    private Class_RecAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class);

        class_recView = findViewById(R.id.class_recView);
        adapter = new Class_RecAdapter(this, Utils.getTeacherClass(), "Class");
        class_recView.setAdapter(adapter);
        class_recView.setLayoutManager(new LinearLayoutManager(this));







        class_float = findViewById(R.id.class_float);

        class_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO : round dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherClass.this);
                View view1 = LayoutInflater.from(TeacherClass.this).inflate(R.layout.class_dialog, null);
                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();


                edTxtClassName = view1.findViewById(R.id.edTxtClassName);
                edTxtSubName = view1.findViewById(R.id.edTxtSubName);

                btn_dia_cancel = view1.findViewById(R.id.btn_dia_cancel);
                btn_dia_add = view1.findViewById(R.id.btn_dia_add);

                btn_dia_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_dia_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edTxtClassName.getText().toString().equals("") && !edTxtSubName.getText().toString().equals("")) {
                            Utils.addTeacherClass(new ModelClass(edTxtClassName.getText().toString(), edTxtSubName.getText().toString()));
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(TeacherClass.this, "Fill fields properly", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

}