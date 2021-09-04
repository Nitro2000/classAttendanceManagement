package com.example.classattendancemanagement;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherAbout extends AppCompatActivity {

    TextView txtWelcome, txtWelAns, txtMaker, txtMakerAns, txtMakerAns2,
            txtHowItWorks, txtClass, txtClassAns, txtAttToolBar, txtUpdatePrev, txtUpdPrevAns
            ,txtContact, txtContactAns, txtContactAns2;
    ImageView ivTool;
    int wel, mak, con, how, clas, attend, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        initialise();


        txtWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wel == 2) {
                    wel = 3;
                    txtWelAns.setVisibility(View.GONE);
                }
                else {
                    wel = 2;
                    txtWelAns.setVisibility(View.VISIBLE);
                }
            }
        });
        txtMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mak == 2) {
                    mak = 3;
                    txtMakerAns.setVisibility(View.GONE);
                    txtMakerAns2.setVisibility(View.GONE);
                }
                else {
                    mak = 2;
                    txtMakerAns.setVisibility(View.VISIBLE);
                    txtMakerAns2.setVisibility(View.VISIBLE);
                }
            }
        });

        txtContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (con == 2) {
                    con = 3;
                    txtContactAns.setVisibility(View.GONE);
                    txtContactAns2.setVisibility(View.GONE);
                } else {
                    con = 2;
                    txtContactAns.setVisibility(View.VISIBLE);
                    txtContactAns2.setVisibility(View.VISIBLE);
                }
            }
        });
        txtHowItWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (how == 2) {
                    how = 3;
                    txtClass.setVisibility(View.GONE);
                    txtClass.setVisibility(View.GONE);
                    txtAttToolBar.setVisibility(View.GONE);
                    ivTool.setVisibility(View.GONE);
                    txtUpdatePrev.setVisibility(View.GONE);
                    txtUpdPrevAns.setVisibility(View.GONE);
                } else {
                    how = 2;
                    txtClass.setVisibility(View.VISIBLE);
                    txtClass.setVisibility(View.VISIBLE);
                    txtAttToolBar.setVisibility(View.VISIBLE);
                    ivTool.setVisibility(View.VISIBLE);
                    txtUpdatePrev.setVisibility(View.VISIBLE);
                    txtUpdPrevAns.setVisibility(View.VISIBLE);
                }
            }
        });
        txtClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clas == 2) {
                    clas = 3;
                    txtClassAns.setVisibility(View.GONE);
                } else {
                    clas = 2;
                    txtClassAns.setVisibility(View.VISIBLE);
                }
            }
        });
        txtAttToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attend == 2) {
                    attend = 3;
                    ivTool.setVisibility(View.GONE);
                } else {
                    attend = 2;
                    ivTool.setVisibility(View.VISIBLE);
                }
            }
        });
        txtUpdatePrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update == 2) {
                    update = 3;
                    txtUpdPrevAns.setVisibility(View.GONE);
                } else {
                    update = 2;
                    txtUpdPrevAns.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initialise() {
        txtWelcome = findViewById(R.id.txtWelcome);
        txtWelAns = findViewById(R.id.txtWelAns);
        txtMaker = findViewById(R.id.txtMaker);
        txtMakerAns = findViewById(R.id.txtMakerAns);
        txtMakerAns2 = findViewById(R.id.txtMakerAns2);
        txtContact = findViewById(R.id.txtContact);
        txtContactAns = findViewById(R.id.txtContactAns);
        txtContactAns2 = findViewById(R.id.txtContactAns2);
        txtHowItWorks = findViewById(R.id.txtHowItWorks);
        txtClass = findViewById(R.id.txtClass);
        txtClassAns = findViewById(R.id.txtClassAns);
        txtAttToolBar = findViewById(R.id.txtAttToolBar);
        txtUpdatePrev = findViewById(R.id.txtUpdatePrev);
        txtUpdPrevAns = findViewById(R.id.txtUpdPrevAns);

        ivTool = findViewById(R.id.ivTool);

        wel = 2;
        mak = 3;
        con = 3;
        how = 3;
        clas = 3;
        attend = 3;
        update = 3;
    }
}
