package com.education.Faculty.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_List {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Userid")
    @Expose
    private String userid;
    @SerializedName("classid")
    @Expose
    private String classid;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("Student")
    @Expose
    private String student;
    @SerializedName("student_image")
    @Expose
    private String studentImage;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("school_name")
    @Expose
    private String schoolName;
    @SerializedName("staffid")
    @Expose
    private String staffid;
    @SerializedName("staffuserid")
    @Expose
    private String staffuserid;
    @SerializedName("Classteacher")
    @Expose
    private String classteacher;
    @SerializedName("FacultyPhone")
    @Expose
    private String facultyPhone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStaffuserid() {
        return staffuserid;
    }

    public void setStaffuserid(String staffuserid) {
        this.staffuserid = staffuserid;
    }

    public String getClassteacher() {
        return classteacher;
    }

    public void setClassteacher(String classteacher) {
        this.classteacher = classteacher;
    }

    public String getFacultyPhone() {
        return facultyPhone;
    }

    public void setFacultyPhone(String facultyPhone) {
        this.facultyPhone = facultyPhone;
    }

}
