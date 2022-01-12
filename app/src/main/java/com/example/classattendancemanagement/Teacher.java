package com.example.classattendancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.PrimitiveIterator;


public class Teacher extends AppCompatActivity {

    private static final String TAG = "TAG";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView txtNavTeacherName, txtNavTeacherEmail;
    private ImageView ivNavProf;

    private RecyclerView attendance_recView;
    private Class_RecAdapter attendanceAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        intialisation();
        
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.classs:
                        startActivity(new Intent(Teacher.this, TeacherClass.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.profile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Teacher.this, TeacherProfile.class));
                        break;
                    case R.id.logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        AttenderFireBase.getfAuth().signOut();
                        startActivity(new Intent(Teacher.this, MainActivity.class));
                        finish();
                        break;
                    case R.id.about:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Teacher.this, TeacherAbout.class));
                        break;
                }
                return false;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        txtNavTeacherName = headerView.findViewById(R.id.txtNavTeacherName);
        txtNavTeacherEmail = headerView.findViewById(R.id.txtNavTeacherEmail);

        AttenderFireBase.dRefTea.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                txtNavTeacherName.setText(document.getString("Name"));
                                txtNavTeacherEmail.setText(document.getString("E-mail"));
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


        attendance_recView = findViewById(R.id.attendance_recView);
        attendanceAdapter = new Class_RecAdapter(this, Utils.getTeacherClass(), "Attender");
        attendance_recView.setLayoutManager(new LinearLayoutManager(this));
        attendance_recView.setAdapter(attendanceAdapter);
        loadData();


    }

    private void intialisation() {

        Utils.getInstance();

        toolbar = findViewById(R.id.navig_toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_teacher);

        navigationView = findViewById(R.id.navigation_view);

        txtNavTeacherName = findViewById(R.id.txtNavTeacherName);
        txtNavTeacherEmail = findViewById(R.id.txtNavTeacherEmail);


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
                            attendanceAdapter.notifyDataSetChanged();
                        }  else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        attendanceAdapter.notifyDataSetChanged();
    }
}