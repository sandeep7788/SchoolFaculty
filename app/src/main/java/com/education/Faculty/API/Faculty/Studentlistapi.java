package com.education.Faculty.API.Faculty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Studentlistapi {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Student")
    @Expose
    private String student;
    boolean selected = false;

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
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public Studentlistapi(String id, String student, boolean selected) {
        super();
        this.id = id;
        this.student = student;
        this.selected = selected;
    }
    public Studentlistapi(String id, String student) {
        super();
        this.id = id;
        this.student = student;
    }
}