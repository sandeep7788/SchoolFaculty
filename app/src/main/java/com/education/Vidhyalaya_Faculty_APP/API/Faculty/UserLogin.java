package com.education.Vidhyalaya_Faculty_APP.API.Faculty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogin {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("facultyid")
    @Expose
    private String facultyid;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("class_teacher")
    @Expose
    private String classTeacher;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("school_name")
    @Expose
    private String schoolName;
    @SerializedName("school_session")
    @Expose
    private String schoolSession;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacultyid() {
        return facultyid;
    }

    public void setFacultyid(String facultyid) {
        this.facultyid = facultyid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
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

    public String getSchoolSession() {
        return schoolSession;
    }

    public void setSchoolSession(String schoolSession) {
        this.schoolSession = schoolSession;
    }

}

