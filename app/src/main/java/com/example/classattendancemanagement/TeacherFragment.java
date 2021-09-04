package com.example.classattendancemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class TeacherFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.teacher_fragment, container, false);

        AttenderFireBase.setfAuth(AttenderFireBase.getfAuth());
        AttenderFireBase.setfStore(AttenderFireBase.getfStore());

        AttenderFireBase.cRefTea = AttenderFireBase.fStore.collection("Teachers");


        return view;
    }
}
