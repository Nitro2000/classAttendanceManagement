package com.example.classattendancemanagement;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AttenderFireBase {

    public static FirebaseAuth fAuth;
    public static FirebaseFirestore fStore;
    public static CollectionReference cRefTea;
    public static DocumentReference dRefTea;
    public static CollectionReference cRef2;
    public static CollectionReference cRef3;


    public static void setdRefTea(DocumentReference dRefTea) {
        AttenderFireBase.dRefTea = dRefTea;
    }


    public static void setfAuth(FirebaseAuth fAuth) {
        AttenderFireBase.fAuth = fAuth;
    }

    public static void setfStore(FirebaseFirestore fStore) {
        AttenderFireBase.fStore = fStore;
    }

    public static void setcRefTea(CollectionReference cRefTea) {
        AttenderFireBase.cRefTea = cRefTea;
    }

    public static FirebaseAuth getfAuth() {
        return FirebaseAuth.getInstance();
    }
    public static FirebaseFirestore getfStore() { return FirebaseFirestore.getInstance();}

}
