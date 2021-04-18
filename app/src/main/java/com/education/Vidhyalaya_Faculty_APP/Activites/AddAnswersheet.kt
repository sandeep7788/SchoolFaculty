package com.education.Vidhyalaya_Faculty_APP.Activites

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.education.Vidhyalaya_Faculty_APP.R
import com.education.Vidhyalaya_Faculty_APP.API.Data
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.*
import com.education.Vidhyalaya_Faculty_APP.API.FileUtils
import com.education.Vidhyalaya_Faculty_APP.API.Profileupdateapi
import com.education.Vidhyalaya_Faculty_APP.API.Services
import com.education.Vidhyalaya_Faculty_APP.Adapter.FeeAdapter
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapter
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapter1
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapterSection
import com.education.Vidhyalaya_Faculty_APP.helper.Utlity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class AddAnswersheet : AppCompatActivity() {

    lateinit var imageview: ImageView
    private val PICK_IMAGE: Int = 100
    lateinit var imageUri: Uri
    var status: Boolean = false
    var RECORD_REQUEST_CODE = 101
    lateinit var progressDialog: android.app.AlertDialog
    var Classlist = ArrayList<Classlist>()
    var Section = ArrayList<Section>()
    var Subject = ArrayList<Subject>()
    lateinit var list: ListView
    lateinit var itemAtPos: String
    lateinit var cv: CardView
    var type = "0"
    lateinit var i_selectedimage: ImageView
    var classid = 0
    lateinit var c_selecttype: CardView
    lateinit var i_closew_c_selecttype: ImageView
    lateinit var r_class: RadioButton
    lateinit var r_student: RadioButton
    lateinit var c_button: Button
    lateinit var e_title: EditText
    lateinit var t: TextView
    lateinit var t_subject: TextView
    lateinit var t_section: TextView
    lateinit var cv1: CardView
    lateinit var friends: ArrayList<Studentlistapi>
    private var adapter: ListViewAdapter1? = null
    var i = 0
    lateinit var b_studentslist: Button
    lateinit var l1: LinearLayout
    lateinit var s_listid: StringBuilder
    var s_listnumber: Int = 0
    var studentid = "0"
    var subject_id = "0"
    lateinit var t_s_list: TextView
    var sectionidst = "0"
    var section_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setContentView(R.layout.activity_add_answersheet)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        cv = findViewById(R.id.cv)
        imageview = findViewById(R.id.imageview)
        progressDialog = SpotsDialog.Builder().setContext(this@AddAnswersheet).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Please wait....")
        progressDialog.setCancelable(false)
        list = findViewById(R.id.list)
        c_selecttype = findViewById(R.id.c_selecttype)
        i_closew_c_selecttype = findViewById(R.id.i_closew_c_selecttype)
        r_class = findViewById(R.id.r_class)
        r_student = findViewById(R.id.r_student)
        c_button = findViewById(R.id.c_button)
        i_selectedimage = findViewById(R.id.i_selectedimage)
        e_title = findViewById(R.id.e_title)
        t = findViewById(R.id.t_subject)
        t_subject = findViewById(R.id.t_subject)
        t_section = findViewById(R.id.t_section)
        cv1 = findViewById(R.id.cv1)
        b_studentslist = findViewById(R.id.b_studentslist)
        l1 = findViewById(R.id.l1)
        t_s_list = findViewById(R.id.t_s_list)
        Classlist = ArrayList()
        Section = ArrayList()
        Subject = ArrayList()
        friends = ArrayList()

        var MyReceiver: BroadcastReceiver? = null
        MyReceiver = com.education.vidhyalaya.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        makeRequest()
        setinfo()

        var i_backbutton1: ImageView = findViewById(R.id.i_backbutton1)
        i_backbutton1.setOnClickListener {
            finish()
        }

        b_studentslist.setOnClickListener {
            Log.e("@@l", s_listid.toString())
            cv.visibility = View.GONE
            t_s_list.text = "Selected STD :- " + (s_listnumber).toString()
            cv.visibility = View.VISIBLE
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()

            getclasslist()
            var t: TextView = findViewById(R.id.t_class)
            list.setOnItemClickListener { adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)

                cv.visibility = View.GONE
                t.text = Classlist.get(itemIdAtPos.toInt()).class_ + ""
                t_s_list.text = friends.get(itemIdAtPos.toInt()).student + ""
                studentid = (friends.get(itemIdAtPos.toInt()).id + "").trim().toString()
            }
        }


        var button = findViewById<Button>(R.id.button)
            .setOnClickListener {
                if (classid == 0) {

                    Toast.makeText(this, "Please Select Any Class", Toast.LENGTH_LONG).show()

                } else if (t_s_list.text.toString().equals("_") && t_section.text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(applicationContext, "Please Select Students", Toast.LENGTH_SHORT)
                        .show()
                } else if (t_subject.text.toString().equals("") && t_subject.text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Please Select Your Subject",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (e_title.text.isEmpty()) {
                    e_title.error = "Marks value Not be Empty"
                } else {
                    inserthomeworkStudent()
                }
            }

        var i_image: ImageView = findViewById(R.id.i_image)
        i_image.setOnClickListener {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                //denied
                Log.e("denied", "Manifest.permission.READ_EXTERNAL_STORAGE")
                makeRequest()
            } else {
                openGallery()
            }
        }


        var b_class: Button = findViewById(R.id.b_class)
        b_class.setOnClickListener {

            cv.visibility = View.VISIBLE
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()
            t_section.text = ""
            t_section.hint = "_"
            t_subject.text = ""
            t_subject.hint = "_"
            t_s_list.text = "_"
            s_listnumber = 0

            getclasslist()

            var t: TextView = findViewById(R.id.t_class)
            list.setOnItemClickListener { adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)

                cv.visibility = View.GONE
                t.text = Classlist.get(itemIdAtPos.toInt()).class_ + ""

                var t_section: TextView = findViewById(R.id.t_section)
                t_section.text = ""
                section_id = 0

                classid = (Classlist.get(itemIdAtPos.toInt()).id + "").trim().toInt()

            }
        }

        var b_section: Button = findViewById(R.id.b_section)
        b_section.setOnClickListener {
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()
            cv.visibility = View.VISIBLE
            sectione()
            var t: TextView = findViewById(R.id.t_section)
            list.setOnItemClickListener { adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                section_id = Section.get(position).id.toInt()
                cv.visibility = View.GONE
                t.text = "Section :- " + Section.get(position).section + ""
                sectionidst = itemAtPos
            }
        }

        var b_subjet: Button = findViewById(R.id.b_subjet)
        b_subjet.setOnClickListener {
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()
            if (classid != 0) {
                cv.visibility = View.VISIBLE
                subjecte()
                list.setOnItemClickListener { adapterView, view, position, id ->
                    itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                    val itemIdAtPos = adapterView.getItemIdAtPosition(position)

                    cv.visibility = View.GONE
//                    t_subject.setText(itemAtPos+"")
                    t_subject.text = Subject.get(itemIdAtPos.toInt()).subject + ""
                    subject_id = (Subject.get(itemIdAtPos.toInt()).subjectid + "").toString()
                }
            } else {
                Toast.makeText(this@AddAnswersheet, "Please First Select Class", Toast.LENGTH_LONG)
                    .show()
            }
        }
        var b_studentlist: Button = findViewById(R.id.b_studentlist)
        b_studentlist.setOnClickListener {
            if (classid == 0) {

                Toast.makeText(this, "Please Select Any Class", Toast.LENGTH_LONG).show()

            } else {
                Section.clear()
                Classlist.clear()
                Subject.clear()
                friends.clear()
                studentlist()
                list.setOnItemClickListener { adapterView, view, position, id ->
                    itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                    val itemIdAtPos = adapterView.getItemIdAtPosition(position)

                    cv.visibility = View.GONE
//                    t_subject.setText(itemAtPos+"")
                    t_s_list.text = friends.get(itemIdAtPos.toInt()).student + ""
                    studentid = (friends.get(itemIdAtPos.toInt()).id + "").toString()
                }
            }
        }
    }

    open fun openGallery(): Unit {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data!!
            i_selectedimage.setImageURI(imageUri)
            Log.e("@@", imageUri.toString())
            Log.e("@@", PICK_IMAGE.toString())
            status = true
        }
    }

    @NonNull
    private fun createPartFromString(value: String): RequestBody? {
        return RequestBody.create(
            MultipartBody.FORM, value
        )
    }


    fun sectione() {

        friends.clear()
        val myListAdapter1 = MyListAdapter1(this@AddAnswersheet, friends)
        list.adapter = myListAdapter1
        myListAdapter1.notifyDataSetChanged()

        Section.clear()
        Classlist.clear()
        Subject.clear()
        friends.clear()
        Log.e("@@class", "call")
        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Section>> =
            preferences.getsectionlist()!!.getdata(getdata(Data.school_id))

        loginResponseCall.enqueue(object : Callback<List<Section>> {
            override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                Log.e("@@e", "error")
                Toast.makeText(this@AddAnswersheet, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Section>>,
                response: Response<List<Section>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@AddAnswersheet, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@AddAnswersheet,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Section.addAll(listOf(response.body()!!.get(i)))
                        }

                        val myListAdapter = MyListAdapterSection(this@AddAnswersheet, Section)
                        list.adapter = myListAdapter


                    }
                } else {
                    Toast.makeText(this@AddAnswersheet, "Don't Have Any Data", Toast.LENGTH_LONG)
                        .show()
                    progressDialog.dismiss()
                    cv.visibility = View.GONE
                }
            }
        })
    }

    fun subjecte() {

        Section.clear()
        Classlist.clear()
        Subject.clear()
        friends.clear()
        Log.e("@@class", "call")
        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Subject>> =
            preferences.getsubjectlist()!!.getdata(getdata(Data.school_id), classid.toString())

        loginResponseCall.enqueue(object : Callback<List<Subject>> {
            override fun onFailure(call: Call<List<Subject>>, t: Throwable) {
                Log.e("@@e", "error")
                Toast.makeText(this@AddAnswersheet, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Subject>>,
                response: Response<List<Subject>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@AddAnswersheet, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@AddAnswersheet,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()
                        cv.visibility = View.GONE
                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Subject>()
//                            Subject.addAll(listOf(response.body()!!.get(i).subject))
                            Subject.add(
                                Subject(
                                    response.body()!!.get(i).subjectid,
                                    response.body()!!.get(i).subject
                                )
                            )
                        }

                        val myListAdapter = MyListAdapter(this@AddAnswersheet, Subject)
                        list.adapter = myListAdapter

                    }
                } else {
                    Toast.makeText(this@AddAnswersheet, "Don't Have Any Data", Toast.LENGTH_LONG)
                        .show()
                    progressDialog.dismiss()
                    cv.visibility = View.GONE
                    b_studentslist.visibility = View.GONE
                }
            }
        })
    }

    fun getclasslist() {
        Section.clear()
        Classlist.clear()
        Subject.clear()
        friends.clear()
        Log.e("@@class", "call")
        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Classlist>> =
            preferences.getclass()!!.getdata(getdata(Data.school_id))

        loginResponseCall.enqueue(object : Callback<List<Classlist>> {
            override fun onFailure(call: Call<List<Classlist>>, t: Throwable) {
                Log.e("@@e", "error")
                Toast.makeText(this@AddAnswersheet, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Classlist>>,
                response: Response<List<Classlist>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@AddAnswersheet, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@AddAnswersheet,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()
                        cv.visibility = View.GONE
                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Classlist.addAll(listOf(response.body()!!.get(i)))

                        }

                        val myListAdapter = MyListAdapterclass(this@AddAnswersheet, Classlist)
                        list.adapter = myListAdapter

                    }
                } else {
                    Toast.makeText(this@AddAnswersheet, "Don't Have Any Data", Toast.LENGTH_LONG)
                        .show()
                    progressDialog.dismiss()
                    cv.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        this@AddAnswersheet.onVisibleBehindCanceled()
        super.onDestroy()
    }

    fun makeRequest() {
        ActivityCompat.requestPermissions(
            this@AddAnswersheet,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("@@permission", "Permission has been denied by user")
                    dialog()
                } else {
                    Log.i("@@permission", "Permission has been granted by user")
                }
            }
        }
    }

    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "_")
        return value
    }

    fun list(): String {
        var data = "...."
        list.setOnItemClickListener { adapterView, view, position, id ->
            itemAtPos = (adapterView.getItemAtPosition(position)).toString()
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            cv.visibility = View.GONE
            Log.e("@@2", itemAtPos)
            data = itemAtPos
        }
        return data
    }

    fun setinfo() {
        var i_closew: ImageView = findViewById(R.id.i_closew)
        i_closew.setOnClickListener { cv.visibility = View.GONE }

        var t_faculty: TextView = findViewById(R.id.t_faculty)
        t_faculty.text = getdata(Data.Facultyid)

        var t_session: TextView = findViewById(R.id.t_session)
        t_session.text = getdata(Data.schoolSession)

        var t_name: TextView = findViewById(R.id.t_name)
        t_name.text = getdata(Data.name)

        var imageview: CircleImageView = findViewById(R.id.imageview)
        Picasso.with(this@AddAnswersheet)
            .load("http://vidhyalaya.co.in/sspanel/" + getdata(Data.image))
            .error(R.drawable.school)
            .into(imageview)
    }


    fun inserthomeworkStudent() {

        if (status == false) {
            Toast.makeText(this@AddAnswersheet, "Please Select Image", Toast.LENGTH_LONG).show()
        } else {
            progressDialog.show()
            val file = File(FileUtils.getPath(this@AddAnswersheet, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val parts =
                MultipartBody.Part.createFormData("file", file.name, requestFile)


            val school_id1 = createPartFromString(getdata(Data.school_id).toString())
            val staff_id1 = createPartFromString(getdata(Data.Facultyid)!!)
            val subject_id1 = createPartFromString(subject_id.toString())
//            val student_id1 = createPartFromString(s_listid.toString()+"")
            val student_id1 = createPartFromString(studentid.toString())

            val remark1 = createPartFromString(e_title.text.toString())

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.getinsertanswersheet()!!.setdata_homeworkstudent(
                    school_id1, staff_id1, student_id1,
                    subject_id1, remark1, parts
                )

            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message!!)
                    Toast.makeText(this@AddAnswersheet, t.message!!, Toast.LENGTH_SHORT).show()
                    Log.e("@@", t.toString() + " ")
                    if (progressDialog != null && progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    Utlity.showErrorDialgo(this@AddAnswersheet)
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {

                    Log.e("@@", response.body().toString() + " ")
                    Toast.makeText(
                        this@AddAnswersheet,
                        response.body()!!.status + " ",
                        Toast.LENGTH_SHORT
                    ).show()
                    status = false
                    progressDialog.dismiss()
                    if (response.body()!!.status.equals("Success")) {
                        Toast.makeText(
                            this@AddAnswersheet,
                            "Submited Successfully" + response.body()?.status.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        ok()
                    } else {
                        Utlity.showErrorDialgo(this@AddAnswersheet)
                        Toast.makeText(
                            this@AddAnswersheet,
                            "Submited but somthing error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })

        }
    }


    fun studentlist() {
        friends.clear()
        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Studentlistapi>> =
            preferences.getstudentlist()!!.getdata(getdata(Data.school_id),classid.toString(),section_id,getdata(Data.schoolSession))

        loginResponseCall.enqueue(object : Callback<List<Studentlistapi>> {
            override fun onFailure(call: Call<List<Studentlistapi>>, t: Throwable) {

                progressDialog.dismiss()
                friends.clear()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Studentlistapi>>,
                response: Response<List<Studentlistapi>>
            ) {
                progressDialog.dismiss()
//                Log.e("@@size",(response.body()!!.size).toString())
                Log.e("@@", (response.body()).toString())

                if (response.isSuccessful) {

                    if (response.body()!!.size > 0) {
                        cv.visibility = View.VISIBLE
                        for (i in 0 until response.body()!!.size) {
                            Log.e("@@userlist", response.body()!!.get(i).id)
                            mutableListOf<Studentlistapi>()
                            friends.add(
                                Studentlistapi(
                                    response.body()!!.get(i).id,
                                    response.body()!!.get(i).student
                                )
                            )

                        }

//
//                    cv.visibility=View.VISIBLE
//                    b_studentslist.visibility=View.VISIBLE
//                    adapter = ListViewAdapter1(this@AddAnswersheet, R.layout.listitem, friends)
//                    list.setAdapter(adapter)
//                    
                        val myListAdapter1 = MyListAdapter1(this@AddAnswersheet, friends)
                        list.adapter = myListAdapter1
                    } else {
                        Toast.makeText(
                            this@AddAnswersheet,
                            "There are no children available in the section of this class",
                            Toast.LENGTH_SHORT
                        ).show()
                        cv.visibility = View.INVISIBLE
                        b_studentslist.visibility = View.INVISIBLE
                        cv.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(
                        this@AddAnswersheet,
                        "There are no children available in the section of this class",
                        Toast.LENGTH_SHORT
                    ).show()
                    cv.visibility = View.INVISIBLE
                    b_studentslist.visibility = View.INVISIBLE
                    cv.visibility = View.GONE
                }
            }
        })
    }

    fun ok() {
        Utlity.showDialog(
            this,
            "Your request submitted Successfully!",
            SweetAlertDialog.SUCCESS_TYPE
        )
    }

    fun dialog() {
        val builder =
            AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
        builder.setTitle("Need Permission")
        builder.setMessage("This app needs storage permission.")
        builder.setPositiveButton("Grant", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.cancel()
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        })
        builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0!!.cancel()
            }

        })
        builder.show()
    }

}