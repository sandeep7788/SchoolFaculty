package com.education.Faculty.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feesdet {

    @SerializedName("Headname")
    @Expose
    private String feehead;
    @SerializedName("amount")
    @Expose
    private String amt;
    @SerializedName("Nextduedate")
    @Expose
    private String duedate;

    public String getFeehead() {
        return feehead;
    }

    public void setFeehead(String feehead) {
        this.feehead = feehead;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

}