package com.example.classattendancemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class TeacherProfile extends AppCompatActivity {

    private static final String TAG = "TAG";
    TextView txtName, txtEmail, txtDep, txtProfName, txtProfEmail, edTxtName, edTxtEmail, edTxtDepName;
    ImageView ivProf;

    float v = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);


        initalise();

        AttenderFireBase.dRefTea.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                ivProf.setImageURI(Uri.parse("content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2FIMG_20210822_150734.jpg"));
                                txtProfName.setText(document.getString("Name"));
                                txtProfEmail.setText(document.getString("E-mail"));
                                edTxtName.setText(document.getString("Name"));
                                edTxtEmail.setText(document.getString("E-mail"));
                                edTxtDepName.setText(document.getString("Department"));
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

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


        ivProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK);
            Uri uri = data.getData();
            ivProf.setImageURI(Uri.parse(uri + ""));
            AttenderFireBase.dRefTea.update("ImageUri", uri + "");
        }
    }


    private void initalise() {

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtDep = findViewById(R.id.txtDep);
        txtProfName = findViewById(R.id.txtProfName);
        txtProfEmail = findViewById(R.id.txtProfEmail);
        edTxtName = findViewById(R.id.edTxtName);
        edTxtEmail = findViewById(R.id.edTxtEmail);
        edTxtDepName = findViewById(R.id.edTxtDepName);
        ivProf = findViewById(R.id.ivprof);


    }


}