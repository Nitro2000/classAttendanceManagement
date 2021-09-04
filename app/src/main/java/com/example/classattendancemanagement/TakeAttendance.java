package com.example.classattendancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class TakeAttendance extends AppCompatActivity {

    private static final String TAG = "TAG";
    private Toolbar toolbar;
    private ImageView ivBack, ivSave;
    private EditText edTxtStudentName, edTxtRollNo;
    private Button btn_diaS_cancel, btn_diaS_add, btnUpdAttendance;
    private TextView txtToolClassName, txtToolDate;
    private RecyclerView studentRecView;
    private Student_RecAdapter studentRecAdapter;
    private AttendanceCalendar calendar;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);


        // Initialising widgets
        initialise();

        // Getting info about class
        Intent intent = getIntent();

        // Toolbar naming for class and subject in taking student attendance
        txtToolClassName.setText(intent.getStringExtra("className"));
        txtToolDate.setText(calendar.getDate());
        int position = intent.getIntExtra("position", -1);
        ivBack.setOnClickListener(v -> onBackPressed());
        ivSave.setOnClickListener(v -> addStatus());

        btnUpdAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadStatusData();
            }
        });


        // Menu adding in toolbar
        toolbar.inflateMenu(R.menu.menu_student_attendance);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()) {
                   case R.id.menu_add_item:
                       showStudentDialog();
                       break;
                   case R.id.menu_show_calendar:
                       showCalendar();
                       break;
               }
               return true;
           };
       });





        // Setting recycler view for students

        studentRecView.setAdapter(studentRecAdapter);
        studentRecView.setLayoutManager(new LinearLayoutManager(this));
        studentRecAdapter.setOnItemClickListener(this::change);
        loadStudentData();




    }


    private void addStatus() {
        for (ModelStudent ms : Utils.getTakeAttendance()) {
            Map<String, String> mapstatus = new HashMap<>();
            mapstatus.put("Status", ms.getStatus());
            AttenderFireBase.cRef4 = AttenderFireBase.cRef3.document(ms.getStudentName()).collection(ms.getStudentName());
            AttenderFireBase.cRef4.document(calendar.getDate()).set(mapstatus, SetOptions.merge());
        }
        Toast.makeText(this, "Attendance saved", Toast.LENGTH_SHORT).show();
    }

    private void loadStatusData() {
        for (ModelStudent ms : Utils.getTakeAttendance()) {
            AttenderFireBase.cRef4 = AttenderFireBase.cRef3.document(ms.getStudentName()).collection(ms.getStudentName());
            AttenderFireBase.cRef4.document(calendar.getDate())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                ms.setStatus(document.getString("Status"));
                            }
                        } else {
                            Toast.makeText(TakeAttendance.this, "No attendance on this day", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
        studentRecAdapter.notifyDataSetChanged();
    }

    private void loadStudentData() {
        Utils.getTakeAttendance().clear();
        String className = txtToolClassName.getText().toString();
        AttenderFireBase.cRef3 = AttenderFireBase.cRef2.document(className).collection(className);
        AttenderFireBase.cRef3
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Utils.addTakeAttendance(new ModelStudent(documentSnapshot.getString("RollNo"), documentSnapshot.getId(), "P"));
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Utils.getTakeAttendance().sort((m, n) -> Integer.compare(Integer.parseInt(m.getRollNo()), Integer.parseInt(n.getRollNo())));
                            }
                            studentRecAdapter.notifyDataSetChanged();
                        }  else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void showCalendar() {

        calendar.show(getSupportFragmentManager(), "");
        calendar.setOnCalendarClickListener(this::onCalendarOkClicked);
    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        txtToolDate.setText(calendar.getDate());
        loadStudentData();
    }

    private void change(int position1) {
        String status = Utils.getTakeAttendance().get(position1).getStatus();

        if (status.equals("P")) status = "A";
        else status = "P";

        Utils.getTakeAttendance().get(position1).setStatus(status);
        studentRecAdapter.notifyDataSetChanged();
    }

    private void showStudentDialog() {
        AlertDialog.Builder studentBuilder = new AlertDialog.Builder(TakeAttendance.this);
        View view = LayoutInflater.from(TakeAttendance.this).inflate(R.layout.student_dialog, null);
        studentBuilder.setView(view);

        AlertDialog dialog = studentBuilder.create();
        dialog.show();

        edTxtStudentName = view.findViewById(R.id.edTxtStudentName);
        edTxtRollNo = view.findViewById(R.id.edTxtRollNo);



        btn_diaS_cancel = view.findViewById(R.id.btn_diaS_cancel);
        btn_diaS_add = view.findViewById(R.id.btn_diaS_add);

        btn_diaS_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_diaS_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = edTxtStudentName.getText().toString();
                String rollNo = edTxtRollNo.getText().toString();
                if (!studentName.equals("")) {
                    if (Utils.notInTakeAttendance(studentName) && Utils.notInTakeAttendanceRoll(rollNo)) {
                        try {
                            int roll = Integer.parseInt(rollNo);

                            Map<String, Object> map = new HashMap<>();
                            map.put("RollNo", rollNo);
                            AttenderFireBase.cRef3.document(studentName).set(map);
                            Utils.addTakeAttendance(new ModelStudent(rollNo, studentName, "P"));
                            edTxtRollNo.setText(String.valueOf(roll + 1));
                            studentRecAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            edTxtRollNo.setText("");
                            Toast.makeText(TakeAttendance.this, "Enter correct roll no", Toast.LENGTH_SHORT).show();
                        }
                        edTxtStudentName.setText("");
                    } else {
                        Toast.makeText(TakeAttendance.this, "Enter new student name and roll no", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TakeAttendance.this, "Fill fields properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialise() {

        toolbar = findViewById(R.id.studentToolbar);

        ivBack = findViewById(R.id.ivBack);
        ivSave = findViewById(R.id.ivSave);

        btnUpdAttendance = findViewById(R.id.btnUpdAttendance);

        txtToolClassName = findViewById(R.id.txtToolClassName);
        txtToolDate = findViewById(R.id.txtToolDate);

        studentRecAdapter = new Student_RecAdapter(this, Utils.getTakeAttendance(), "TakeAttendance");
        studentRecView = findViewById(R.id.student_rec_view);

        calendar = new AttendanceCalendar();



    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            deleteStudent(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteStudent(int position) {
        String studentName = Utils.getTakeAttendance().get(position).getStudentName();
        Utils.getTakeAttendance().remove(position);
        AttenderFireBase.cRef3.document(studentName).delete();
        studentRecAdapter.notifyItemRemoved(position);
    }

}