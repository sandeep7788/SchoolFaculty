package com.education.Vidhyalaya_Faculty_APP.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profileapi {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("profileimage")
    @Expose
    private String profileimage;

    public String getEmail() {
        return status;
    }

    public void setEmail(String status) {
        this.status = status;
    }

}