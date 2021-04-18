package com.education.Vidhyalaya_Faculty_APP.API

import com.education.Vidhyalaya_Faculty_APP.API.Faculty.*
import com.education.Vidhyalaya_Faculty_APP.helper.GetSchoolCessionModel
import com.google.gson.JsonArray
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class Services
{

//    http://vidhyalaya.co.in/sspanel/API/holiday/parentlogin?mobno=9636045430&password=1234&remember_token=test123
//    val baseurl="http://vidhyalaya.co.in/sspanel/API/holiday/"
    val baseurl="http://vidhyalaya.co.in/sspanel/API/holiday/"
    public final val loginurl="parentlogin"

    var dashbord_menu: Load_DashBord_Menu? = null
    fun get_dashbord_menu(): Load_DashBord_Menu? {
        if (dashbord_menu == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            dashbord_menu = retrofit.create(Load_DashBord_Menu::class.java)
        }
        return dashbord_menu
    }

    var removeFromCart: Login_api? = null
    fun getServices(): Login_api? {
        if (removeFromCart == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            removeFromCart = retrofit.create(Login_api::class.java)
        }
        return removeFromCart
    }

    var loadUserlist: Load_UserList? = null
    fun getUserliost(): Load_UserList? {
        if (loadUserlist == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            loadUserlist = retrofit.create(Load_UserList::class.java)
        }
        return loadUserlist
    }

    var accountinfo: Account? = null
    fun getAccountinformation(): Account? {
        if (accountinfo == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            accountinfo = retrofit.create(Account::class.java)
        }
        return accountinfo
    }


    var data: updatedata? = null
    fun getaccountupdate(): updatedata? {
        if (data == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            data = retrofit.create(updatedata::class.java)
        }
        return data
    }
    var data1: updatedatawithoutimage? = null
    fun getaccountupdatewithoutimage(): updatedatawithoutimage? {
        if (data1 == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            data1 = retrofit.create(updatedatawithoutimage::class.java)
        }
        return data1
    }

    var notifaction: Notifaction? = null
    fun getNotifactionservice(): Notifaction? {
        if (notifaction == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            notifaction = retrofit.create(Notifaction::class.java)
        }
        return notifaction
    }

    var homework: Homework? = null
    fun gethomeworkservices(): Homework? {
        if (homework == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            homework = retrofit.create(Homework::class.java)
        }
        return homework
    }

    var holiday: Holiday? = null
    fun getholidayservices(): Holiday? {
        if (holiday == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            holiday = retrofit.create(Holiday::class.java)
        }
        return holiday
    }

    var answersheet: Answersheet? = null
    fun getanswersheetservices(): Answersheet? {
        if (answersheet == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            answersheet = retrofit.create(Answersheet::class.java)
        }
        return answersheet
    }
    var attendanceList: Atendence? = null
    fun getAttendenceList(): Atendence? {
        if (attendanceList == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            attendanceList = retrofit.create(Atendence::class.java)
        }
        return attendanceList
    }

    var leave: Leave? = null
    fun getLeaveServices(): Leave? {
        if (leave == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            leave = retrofit.create(Leave::class.java)
        }
        return leave
    }

    var changePassword: ChangePassword? = null
    fun getchange_password(): ChangePassword? {
        if (changePassword == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            changePassword = retrofit.create(ChangePassword::class.java)
        }
        return changePassword
    }





    var insertmoment: InsertMoment? = null
    fun setMoment(): InsertMoment? {
        if (insertmoment == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            insertmoment = retrofit.create(InsertMoment::class.java)
        }
        return insertmoment
    }


    var insertHomeWorkClass: InsertHomeWorkClass? = null
    fun sethomework(): InsertHomeWorkClass? {
        if (insertHomeWorkClass == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            insertHomeWorkClass = retrofit.create(InsertHomeWorkClass::class.java)
        }
        return insertHomeWorkClass
    }

    var classlist: ClassList? = null
    fun getclass(): ClassList? {
        if (classlist == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            classlist = retrofit.create(ClassList::class.java)
        }
        return classlist
    }

    var sectionsList: SectionsList? = null
    fun getsectionlist(): SectionsList? {
        if (sectionsList == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            sectionsList = retrofit.create(SectionsList::class.java)
        }
        return sectionsList
    }
    var subjectlist: Subjectlist? = null
    fun getsubjectlist(): Subjectlist? {
        if (subjectlist == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            subjectlist = retrofit.create(Subjectlist::class.java)
        }
        return subjectlist
    }

    var inserthomeworkstudent: InsertHomeWorkStudent? = null
    fun getinserthomeworkstudent(): InsertHomeWorkStudent? {
        if (inserthomeworkstudent == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            inserthomeworkstudent = retrofit.create(InsertHomeWorkStudent::class.java)
        }
        return inserthomeworkstudent
    }

    var inserthomeworkstudent1: InsertAnswersheet? = null
    fun getinsertanswersheet(): InsertAnswersheet? {
        if (inserthomeworkstudent1 == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            inserthomeworkstudent1 = retrofit.create(InsertAnswersheet::class.java)
        }
        return inserthomeworkstudent1
    }

    var studentlistapi: Studentlist? = null
    fun getstudentlist(): Studentlist? {
        if (studentlistapi == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            studentlistapi = retrofit.create(Studentlist::class.java)
        }
        return studentlistapi
    }

    var insertAttendence: InsertAttendence? = null
    fun insertAttendence(): InsertAttendence? {
        if (insertAttendence == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            insertAttendence = retrofit.create(InsertAttendence::class.java)
        }
        return insertAttendence
    }

    var videodata: Video? = null
    fun getvideolist(): Video? {
        if (videodata == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            videodata = retrofit.create(Video::class.java)
        }
        return videodata
    }

    var videodata1: AddVideo? = null
    fun getinsertvideo(): AddVideo? {
        if (videodata1 == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            videodata1 = retrofit.create(AddVideo::class.java)
        }
        return videodata1
    }

    var videodataa1: AddVideo1? = null
    fun getinsertvideo1(): AddVideo1? {
        if (videodataa1 == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            videodataa1 = retrofit.create(AddVideo1::class.java)
        }
        return videodataa1
    }

    var mGETSCHOOLCESSION: GETSCHOOLCESSION? = null
    fun get_school_cession(): GETSCHOOLCESSION? {
        if (videodataa1 == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mGETSCHOOLCESSION = retrofit.create(GETSCHOOLCESSION::class.java)
        }
        return mGETSCHOOLCESSION
    }

//http://vidhyalaya.co.in/sspanel/API/holiday/facultylogin?mobno=7014741657&pass=1234&remember_token=test123
    interface Login_api {
        @GET("facultylogin?")
        fun
                getProducts(
            @Query("mobno") one: String?,
            @Query("pass") two: String?,
            @Query("remember_token") key: String?
        ): Call<List<UserLogin>>//Call<UserLogin?>?
    }

//http://vidhyalaya.co.in/sspanel/API/holiday/insertmoment?
// school_id=32&title=test1&description=test&date=17/06/2020&
// faculty=42921&file=../upload/moment/test.jpg

    interface InsertMoment {
        @Multipart
        @POST("insertmoment?")
        fun insertmomrnt(
            @Part("school_id") school_id: RequestBody?,
            @Part("title") title: RequestBody?,
            @Part("description") description: RequestBody?,
            @Part("date") date: RequestBody?,
            @Part("faculty") faculty: RequestBody?,
            @Part pro_image: MultipartBody.Part?
        ): Call<InsertMomentapi>
    }

//http://vidhyalaya.co.in/sspanel/API/holiday/moments?school_id=32&facultyid=42921
    interface Load_DashBord_Menu {
        @GET("moments?")
        fun getmenu(
            @Query("school_id") school_id: String?,
            @Query("facultyid") facultyid: String?
        ): Call<List<MomentApi>>//Call<UserLogin?>?
    }

    interface Load_UserList {
        @GET("liststudent?")
        fun getUserlist(
            @Query("mobno") mobno: String?,
            @Query("school_session") school_session: String?
        ): Call<List<User_List>>//Call<UserLogin?>?
    }
//    http://vidhyalaya.co.in/sspanel/API/holiday/profile?school_id=32&userid=42921

    interface Account {
        @GET("profile?")
        fun getAccountinfo(
            @Query("school_id") school_id: String?,
            @Query("userid") userid: String?
        ): Call<List<Accountapi>>//Call<UserLogin?>?
    }
//http://vidhyalaya.co.in/sspanel/API/holiday/staffprofileupdate?
// school_id=32&userid=42921&address=JAIPUR&file=/upload/profile/test.jpg
    interface updatedata {
        @Multipart
        @POST("staffprofileupdate?")
        fun account(
            @Part("school_id") school_id: RequestBody?,@Part("userid") userid: RequestBody?,@Part("address") address: RequestBody?, @Part pro_image: MultipartBody.Part?
        ): Call<Profileupdateapi>
    }
    interface updatedatawithoutimage {
        @Multipart
        @POST("staffprofileupdate?")
        fun account(
            @Part("school_id") school_id: RequestBody?,@Part("userid") userid: RequestBody?,@Part("address") address: RequestBody?
        ): Call<Profileupdateapi>
    }

//http://vidhyalaya.co.in/sspanel/API/holiday/notification?school_id=32&facultyid=42921
    interface Notifaction {
        @GET("notification?")
        fun getNotifaction(
            @Query("school_id") school_id: String?,
            @Query("facultyid") stdid: String?
        ): Call<List<Notifactionpojo>>//Call<UserLogin?>?
    }

//http://vidhyalaya.co.in/sspanel/API/holiday/homeworklisting?school_id=32&facultyid=480

    interface Homework {
        @GET("homeworklisting?")
        fun gethomework(
            @Query("school_id") school_id: String?,
            @Query("facultyid") facultyid: String?
        ): Call<List<Homeworkapus>>//Call<UserLogin?>?
    }

//    http://www.skoolstarr.com/sspanel/API/holiday?school_id=28

    interface Holiday {
        @GET("?")
        fun getholiday(
            @Query("school_id") school_id: String?
        ): Call<List<Holadaypojo>>//Call<UserLogin?>?
    }

//http://vidhyalaya.co.in/sspanel/API/holiday/answersheet?school_id=32&studentid=43344
//    http://vidhyalaya.co.in/sspanel/API/holiday/answersheetlisting?school_id=32&facultyid=480
    interface Answersheet {
        @GET("answersheetlisting?")
        fun getanswersheet(
            @Query("school_id") school_id: String?,
            @Query("facultyid") studentid: String?
        ): Call<List<answerapi>>//Call<UserLogin?>?
    }

        interface Services {
        @GET("files/Node-Android-Chat.zip")
        @Streaming
        fun downloadFile(): Call<ResponseBody?>?
    }
//    http://vidhyalaya.co.in/sspanel/API/holiday/insertleaverequeststaff?school_id=32&
//    faculty_userid=42921&faculty_id=480&reason=xyz&description=xyz&start_date=13/07/2020&end_date=16/07/2020
    interface Leave {
        @FormUrlEncoded
        @POST("insertleaverequeststaff?")
        fun getLandingPageReport(
            @Field("school_id") school_id: String?,
            @Field("faculty_userid") student_id: String?,
            @Field("faculty_id") faculty_id: String?,
            @Field("reason") reason: String?,
            @Field("description") description: String?,
            @Field("start_date") start_date: String?,
            @Field("end_date") end_date: String?)
                : Call<Profileupdateapi>?
    }

interface Atendence {
    @GET("studentattendance?")
    fun get_atendenceList(
        @Query("school_id") school_id: String?,
        @Query("stdid") stdid: String?,
        @Query("month") month: String,
        @Query("year") year: String
    ): Call<Attendentapi>
}

    interface ChangePassword {
        @GET("passwordchange?")
        fun getchancepasswordservices(
            @Query("school_id") school_id: String?,
            @Query("userid") userid: String?,
            @Query("old_password") old_password: String?,
            @Query("new_password") new_password: String?): Call<Changepasswordapi>
    }
//    http://vidhyalaya.co.in/sspanel/API/holiday/getclasses?school_id=32

    interface ClassList {
    @GET("getclasses?")
    fun getdata(
        @Query("school_id") school_id: String?):
            Call<List<Classlist>>
}

//http://vidhyalaya.co.in/sspanel/API/holiday/getsections?school_id=32

interface SectionsList {
    @GET("getsections?")
    fun getdata(
        @Query("school_id") school_id: String?):
            Call<List<Section>>
}

//    http://vidhyalaya.co.in/sspanel/API/holiday/subjectlisting?school_id=32&classid=720
interface Subjectlist {
    @GET("subjectlisting?")
    fun getdata(
        @Query("school_id") school_id: String?,
        @Query("classid") classid: String?):
            Call<List<Subject>>
}
//http://vidhyalaya.co.in/sspanel/API/holiday/inserthomework?school_id=32&classid=720&sectionid=89&faculty=480&subject=327&
// title=test&description=test&submission_date=22/06/2020&homeworktype=a_class&file=../upload/homework/test.jpg

interface InsertHomeWorkClass {
    @Multipart
    @POST("inserthomework?")
    fun setdata_homework(
        @Part("school_id") school_id: RequestBody?,@Part("classid") classid: RequestBody?,
        @Part("sectionid") sectionid: RequestBody?, @Part("faculty") faculty: RequestBody?,
        @Part("subject") subject: RequestBody?, @Part("title") title: RequestBody?,
        @Part("description") description: RequestBody?, @Part("submission_date") submission_date: RequestBody?,
        @Part("homeworktype") homeworktype: RequestBody?,@Part file: MultipartBody.Part?
    ): Call<Profileupdateapi>
}

interface InsertHomeWorkStudent {
    @Multipart
    @POST("inserthomework?")
    fun setdata_homeworkstudent(
        @Part("school_id") school_id: RequestBody?,@Part("class_id") classid: RequestBody?,
        @Part("section_id") sectionid: RequestBody?, @Part("student") student: RequestBody?,
        @Part("faculty") faculty: RequestBody?, @Part("subject") subject: RequestBody?,
        @Part("title") title: RequestBody?, @Part("description") description: RequestBody?,
        @Part("submission_date") submission_date: RequestBody?,@Part("homeworktype") homeworktype: RequestBody?
        ,@Part file: MultipartBody.Part?
    ): Call<Profileupdateapi>
}
//    http://vidhyalaya.co.in/sspanel/API/holiday/answersheetInsert?school_id=32&staff_id=480&
//    student_id=43344&subject_id=327&remark=XYZ&file=/upload/answersheet/test.jpg

    interface InsertAnswersheet {
        @Multipart
        @POST("answersheetInsert?")
        fun setdata_homeworkstudent(
            @Part("school_id") school_id: RequestBody?,
            @Part("staff_id") classid: RequestBody?,
            @Part("student_id") sectionid: RequestBody?,
            @Part("subject_id") student: RequestBody?,
            @Part("remark") faculty: RequestBody?,
            @Part file: MultipartBody.Part?
        ): Call<Profileupdateapi>
    }

//    http://www.skoolstarr.com/sspanel/API/holiday/insertvideo?school_id=32&class_id=720&
//    section_id=&student=43344&faculty=480&title=test&description=test&videotype=a_student&file=../upload/video/test.jpg
    interface AddVideo {
        @Multipart
        @POST("insertvideo?")
        fun setdata_homeworkstudent(
            @Part("school_id") school_id: RequestBody?,
            @Part("class_id") class_id: RequestBody?,
            @Part("section_id") section_id: RequestBody?,
            @Part("student") student: RequestBody?,
            @Part("faculty") faculty: RequestBody?,
            @Part("title") title: RequestBody?,
            @Part("description") description: RequestBody?,
            @Part("videotype") videotype: RequestBody?,
            @Part file: MultipartBody.Part?
        ): Call<Profileupdateapi>
    }
//    http://vidhyalaya.co.in/sspanel/API/holiday/insertvideo?school_id=32&classid=720&sectionid=
//    &faculty=480&title=test&description=test&videotype=a_class&file=../upload/homework/test.jpg

interface AddVideo1 {
    @Multipart
    @POST("insertvideo?")
    fun setdata_homeworkstudent(
        @Part("school_id") school_id: RequestBody?,
        @Part("classid") class_id: RequestBody?,
        @Part("sectionid") section_id: RequestBody?,
        @Part("faculty") faculty: RequestBody?,
        @Part("title") title: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("videotype") videotype: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<Profileupdateapi>
}

    //http://vidhyalaya.co.in/sspanel/API/holiday/getstudents?school_id=32&class_id=720&section_id=&school_session=2020-2021
    interface Studentlist {
        @GET("getstudents?")
        fun getdata(
            @Query("school_id") school_id: String?,
            @Query("class_id") class_id: String?,
            @Query("section_id") section_id: Int?,
            @Query("school_session") school_session: String?):
                Call<List<Studentlistapi>>
    }

//    http://vidhyalaya.co.in/sspanel/API/holiday/studentattendanceInsert?schoolid=32&faculty=480&subdate=10/07/2020&
//     classid=720&sectionid=&stid=43344,43631&stid1=43632,43633&school_session=2020-2021

interface InsertAttendence {
    @GET("studentattendanceInsert?")
    fun getdata(
        @Query("schoolid") schoolid: String?,
        @Query("faculty") faculty: String?,
        @Query("subdate") subdate: String?,
        @Query("classid") classid: String?,
        @Query("sectionid") sectionid: String?,
        @Query("stid") stid: String?,
        @Query("stid1") stid1: String?,
        @Query("school_session") school_session: String?,
        @Query("session") session: String?):
            Call<Profileupdateapi>
}

    interface Video {
        @GET("video?")
        fun getvideolist(
            @Query("school_id") school_id: Int?,
            @Query("stdid") stdid: Int?,
            @Query("classid") classid: Int?,
            @Query("section") section: Int?
        ): Call<List<videoapi>>//Call<UserLogin?>?
    }
//http://vidhyalaya.co.in/sspanel/API/holiday/getschoolsession?school_id=32
    interface GETSCHOOLCESSION {
        @GET("getschoolsession?")
        fun getSchoolCession1(
            @Query("school_id") school_id: Int?
        ): Call<List<GetSchoolCessionModel>>//Call<UserLogin?>?
    }
}