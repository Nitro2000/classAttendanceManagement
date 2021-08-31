package com.example.classattendancemanagement;

public class ModelStudent {
    private String rollNo;
    private String studentName;
    private String status;


    public ModelStudent(String rollNo, String studentName) {
        this.rollNo = rollNo;
        this.studentName = studentName;
        status = "";
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
