package com.education.Vidhyalaya_Faculty_APP.API.Faculty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("subjectid")
    @Expose
    private String subjectid;
    @SerializedName("subject")
    @Expose
    private String subject;

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Subject(String subjectid,String subject) {
        this.subject = subject;
        this.subjectid = subjectid; // true is male, false is woman
        // not selected when create
    }
}