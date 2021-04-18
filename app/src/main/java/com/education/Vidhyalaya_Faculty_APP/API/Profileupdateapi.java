package com.education.Vidhyalaya_Faculty_APP.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profileupdateapi {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}