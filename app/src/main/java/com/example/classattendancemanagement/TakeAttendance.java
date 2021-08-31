package com.example.classattendancemanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TakeAttendance extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView ivBack, ivSave;
    private EditText edTxtStudentName, edTxtRollNo;
    private Button btn_dia_cancel, btn_dia_add;
    private TextView dia_add_class, txtDiaStudentName, txtDiaRollNo, txtToolClassName, txtToolSubName;
    private RecyclerView studentRecView;
    private Student_RecAdapter studentRecAdapter;
    private AttendanceCalendar calendar;
    private String subjectName;


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
        subjectName = intent.getStringExtra("subName");
        txtToolClassName.setText(intent.getStringExtra("className"));
        txtToolSubName.setText(subjectName + "|" + calendar.getDate());
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


    }

    private void showCalendar() {

        calendar.show(getSupportFragmentManager(), "");
        calendar.setOnCalendarClickListener(this::onCalendarOkClicked);
    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        txtToolSubName.setText(subjectName + "|" + calendar.getDate());
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
        View view = LayoutInflater.from(TakeAttendance.this).inflate(R.layout.class_dialog, null);
        studentBuilder.setView(view);

        AlertDialog dialog = studentBuilder.create();
        dialog.show();

        edTxtStudentName = view.findViewById(R.id.edTxtClassName);
        edTxtStudentName.setHint("Student Name");
        edTxtRollNo = view.findViewById(R.id.edTxtSubName);
        edTxtRollNo.setHint("Roll No");

        dia_add_class = view.findViewById(R.id.dia_add_class);
        dia_add_class.setText("Add Stud.");

        txtDiaStudentName = view.findViewById(R.id.txtDiaClassName);
        txtDiaStudentName.setText("Student Name");

        txtDiaRollNo = view.findViewById(R.id.txtDiaSubjectName);
        txtDiaRollNo.setText("Roll No");


        btn_dia_cancel = view.findViewById(R.id.btn_dia_cancel);
        btn_dia_add = view.findViewById(R.id.btn_dia_add);

        btn_dia_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_dia_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edTxtStudentName.getText().toString().equals("")) {
                    try {
                        int roll = Integer.parseInt(edTxtRollNo.getText().toString());
                        Utils.addTakeAttendance(new ModelStudent(edTxtRollNo.getText().toString(), edTxtStudentName.getText().toString()));
                        edTxtRollNo.setText(String.valueOf(roll + 1));
                        studentRecAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        edTxtRollNo.setText("");
                        Toast.makeText(TakeAttendance.this, "Enter correct roll no", Toast.LENGTH_SHORT).show();
                    }

                     edTxtStudentName.setText("");
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
        txtToolSubName = findViewById(R.id.txtToolSubName);

        studentRecAdapter = new Student_RecAdapter(this, Utils.getTakeAttendance(), "TakeAttendance");
        studentRecView = findViewById(R.id.student_rec_view);

        calendar = new AttendanceCalendar();



    }
}