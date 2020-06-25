package com.education.Faculty.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendentapi {

    @SerializedName("total_days")
    @Expose
    private String totalDays;
    @SerializedName("total_attendance")
    @Expose
    private String totalAttendance;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("attendance_list")
    @Expose
    private List<AttendanceList> attendanceList = null;

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(String totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<AttendanceList> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceList> attendanceList) {
        this.attendanceList = attendanceList;
    }

}
