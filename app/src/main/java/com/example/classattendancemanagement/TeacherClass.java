package com.example.classattendancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.grpc.okhttp.internal.Util;

public class TeacherClass extends AppCompatActivity {

    private static final String TAG = "TAG";
    private FloatingActionButton class_float;
    private RecyclerView class_recView;
    private EditText edTxtClassName;
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
        loadData();


        class_float = findViewById(R.id.class_float);

        class_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherClass.this);
                View view1 = LayoutInflater.from(TeacherClass.this).inflate(R.layout.class_dialog, null);
                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();


                edTxtClassName = view1.findViewById(R.id.edTxtClassName);

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
                        String className = edTxtClassName.getText().toString();
                        if (!className.equals("")) {
                            if (Utils.notInTeacherClass(className)){
                                Map<String, String> map = new HashMap<>();
                                map.put("sub", "Attender");
                                AttenderFireBase.cRef2.document(className).set(map);
                                Utils.addTeacherClass(new ModelClass(className));
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                edTxtClassName.setText("");
                                Toast.makeText(TeacherClass.this, "Enter different class name", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TeacherClass.this, "Fill fields properly", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteClass(int position) {
        String className = Utils.getTeacherClass().get(position).getClassName();
        Utils.getTeacherClass().remove(position);
        AttenderFireBase.cRef2.document(className).delete();
        adapter.notifyItemRemoved(position);

    }

    private void loadData() {

        Utils.getTeacherClass().clear();
        AttenderFireBase.cRef2 = AttenderFireBase.dRefTea.collection(AttenderFireBase.fAuth.getCurrentUser().getUid());
                AttenderFireBase.cRef2
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Utils.addTeacherClass(new ModelClass(documentSnapshot.getId()));
                            }
                            adapter.notifyDataSetChanged();
                        }  else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



}