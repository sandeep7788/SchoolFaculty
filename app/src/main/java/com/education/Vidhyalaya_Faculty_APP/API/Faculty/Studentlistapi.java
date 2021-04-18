package com.education.Vidhyalaya_Faculty_APP.API.Faculty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Studentlistapi {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Student")
    @Expose
    private String student;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
    public Studentlistapi(String id,String student) {

        this.id = id;
        this.student = student;
         // true is male, false is woman
        // not selected when create
    }
}