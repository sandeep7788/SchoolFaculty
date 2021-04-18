package com.education.Vidhyalaya_Faculty_APP.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accountapi {

    @SerializedName("email")
    @Expose
    private String email;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

}
