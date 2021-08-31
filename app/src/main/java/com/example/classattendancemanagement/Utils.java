package com.example.classattendancemanagement;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;
    private static ArrayList<ModelClass> teacherClass;
    private static ArrayList<ModelStudent> takeAttendance;

    // Getters
    public static ArrayList<ModelStudent> getTakeAttendance() {
        return takeAttendance;
    }
    public static ArrayList<ModelClass> getTeacherClass() {
        return teacherClass;
    }

    private Utils() {
        if (null == teacherClass) {
            teacherClass = new ArrayList<>();
        }
        if (null == takeAttendance) {
            takeAttendance = new ArrayList<>();
        }
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public static void addTeacherClass(ModelClass modelClass) {
        teacherClass.add(modelClass);
    }
    public static void addTakeAttendance(ModelStudent modelStudent) {
        takeAttendance.add(modelStudent);
    }
}
