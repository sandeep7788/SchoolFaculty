package com.education.Vidhyalaya_Faculty_APP.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSchoolCessionModel {

    @SerializedName("session")
    @Expose
    private String session;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

}