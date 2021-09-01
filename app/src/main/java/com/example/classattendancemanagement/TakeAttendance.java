package com.example.classattendancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class TakeAttendance extends AppCompatActivity {

    private static final String TAG = "TAG";
    private Toolbar toolbar;
    private ImageView ivBack, ivSave;
    private EditText edTxtStudentName, edTxtRollNo;
    private Button btn_diaS_cancel, btn_diaS_add;
    private TextView  txtDiaStudentName, txtDiaRollNo, txtToolClassName, txtToolDate;
    private RecyclerView studentRecView;
    private Student_RecAdapter studentRecAdapter;
    private AttendanceCalendar calendar;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        //TODO remove utils instance from take attendance
        Utils.getInstance();

        // Initialising widgets
        initialise();

        // Getting info about class
        Intent intent = getIntent();

        // Toolbar naming for class and subject in taking student attendance
        txtToolClassName.setText(intent.getStringExtra("className"));
        txtToolDate.setText(calendar.getDate());
        int position = intent.getIntExtra("position", -1);
        ivBack.setOnClickListener(v -> onBackPressed());

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

    private void loadStudentData() {
        Utils.getTakeAttendance().clear();
        String className = txtToolClassName.getText().toString();
        AttenderFireBase.cRef3 = AttenderFireBase.cRef2.document(className).collection(className);
        AttenderFireBase.cRef3
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Utils.addTakeAttendance(new ModelStudent(documentSnapshot.getString("RollNo"), documentSnapshot.getId()));
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
                AttendanceCalendar cal = new AttendanceCalendar();
                String studentName = edTxtStudentName.getText().toString();
                String rollNo = edTxtRollNo.getText().toString();
                String date = cal.getDate();
                if (!studentName.equals("")) {
                    if (Utils.notInTakeAttendance(studentName)) {
                        try {
                            int roll = Integer.parseInt(rollNo);

                            Map<String, Object> map = new HashMap<>();
                            map.put("RollNo", rollNo);
                            map.put("Status", "");
                            map.put("Date", date);

                            AttenderFireBase.cRef3.document(studentName).set(map);
                            Utils.addTakeAttendance(new ModelStudent(rollNo, studentName));
                            edTxtRollNo.setText(String.valueOf(roll + 1));
                            studentRecAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            edTxtRollNo.setText("");
                            Toast.makeText(TakeAttendance.this, "Enter correct roll no", Toast.LENGTH_SHORT).show();
                        }
                        edTxtStudentName.setText("");
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