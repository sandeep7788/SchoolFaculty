package com.education.Vidhyalaya_Faculty_APP.Activites

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import com.education.Vidhyalaya_Faculty_APP.API.Data
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.*
import com.education.Vidhyalaya_Faculty_APP.API.FileUtils
import com.education.Vidhyalaya_Faculty_APP.API.Profileupdateapi
import com.education.Vidhyalaya_Faculty_APP.API.Services
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapter
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapterSection
import com.education.Vidhyalaya_Faculty_APP.R
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Addhomework : AppCompatActivity() {

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
    lateinit var i_date: ImageView
    var cal = Calendar.getInstance()
    lateinit var t_date: TextView
    var type = "0"
    lateinit var i_selectedimage: ImageView
    var classid = 0
    lateinit var c_selecttype: CardView
    lateinit var i_closew_c_selecttype: ImageView
    lateinit var r_class: RadioButton
    lateinit var r_student: RadioButton
    lateinit var c_button: Button
    lateinit var e_title: EditText
    lateinit var e_description: EditText
    lateinit var t: TextView
    lateinit var t_subject: TextView
    lateinit var t_section: TextView
    lateinit var cv1: CardView
    lateinit var friends: ArrayList<Friend>
    private var adapter: ListViewAdapter1? = null
    var i = 0
    lateinit var b_studentslist: Button
    lateinit var l1: LinearLayout
    lateinit var s_listid: StringBuilder
    lateinit var s_listname: StringBuilder
    var s_listnumber: Int = 0
    lateinit var t_s_list: TextView
    var subjectidnew = "0"
    var sectionidst = "0"
    var apendboolean = false
    var section_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhomework)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        cv = findViewById(R.id.cv)
        imageview = findViewById(R.id.imageview)
        progressDialog = SpotsDialog.Builder().setContext(this@Addhomework).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Please wait....")
        progressDialog.setCancelable(false)
        list = findViewById(R.id.list)
        c_selecttype = findViewById(R.id.c_selecttype)
        i_closew_c_selecttype = findViewById(R.id.i_closew_c_selecttype)
        r_class = findViewById(R.id.r_class)
        r_student = findViewById(R.id.r_student)
        c_button = findViewById(R.id.c_button)
        i_date = findViewById(R.id.i_date)
        t_date = findViewById(R.id.t_date)
        i_selectedimage = findViewById(R.id.i_selectedimage)
        e_title = findViewById(R.id.e_title)
        e_description = findViewById(R.id.e_description)
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

        alertdilog1()
        makeRequest()
        setinfo()
        var i_backbutton1: ImageView = findViewById(R.id.i_backbutton1)
        i_backbutton1.setOnClickListener {
            finish()
        }

        b_studentslist.setOnClickListener {
            Log.e("@@data", generateDataToContainerLayout().toString())
            Log.e("@@l", s_listid.toString())
            cv.visibility = View.GONE
            t_s_list.text = "Selected ST :- " + (s_listnumber).toString()
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
            }
        }

        var button = findViewById<Button>(R.id.button)
            .setOnClickListener {
                if (classid == 0) {

                    Toast.makeText(this, "Please Select Any Class", Toast.LENGTH_LONG).show()

                }
/*

                else if(t_section.text.toString().equals("")&&t_section.text.toString().isEmpty())
                {
                    Toast.makeText(applicationContext,"Please Select Your Section",Toast.LENGTH_SHORT).show()
                }
*/
                else if (t_subject.text.toString().equals("_") && t_subject.text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(
                        this@Addhomework,
                        "Please Select Your Subject",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (t_date.text.isEmpty()) {
                    t_date.error = "Select Date"
                } else if (e_title.text.isEmpty()) {
                    e_title.error = "Title Not be Empty"
                } else if ((e_description.text.toString()
                        .isEmpty()) && ((e_description.text.toString().trim()).equals(""))
                ) {
                    e_description.error = "Enter Long Description"
                } else {
                    when (type) {
                        "0" -> {
                            Toast.makeText(this@Addhomework, "Somthing Wrong", Toast.LENGTH_LONG)
                                .show()
                        }
                        "s" -> {
                            if (s_listnumber <= 0) {
                                Toast.makeText(
                                    applicationContext,
                                    "Please Select Student",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                inserthomeworkStudent()
                            }
                        }
                        "c" -> {
                            inserthomeworkClass()
                        }
                    }
                }
            }



        t_date.setOnClickListener {
            i_date.performClick()
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

        val dateSetListener1 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(t_date)
            }
        }

        i_date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@Addhomework,
                    dateSetListener1,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })


        var b_class: Button = findViewById(R.id.b_class)
        b_class.setOnClickListener {
            cv.visibility = View.VISIBLE
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()
            apendboolean = false

            t_section.text = ""
            t_section.hint = "_"
            t_subject.text = ""
            t_subject.hint = "_"
            t_s_list.text = "_"
            t_date.text = ""
            t_date.hint = "00:00:00"
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
                classid = (Classlist.get(itemIdAtPos.toInt()).id + " ").trim().toInt()

            }
        }

        val b_section: Button = findViewById(R.id.b_section)
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
//                t_subject.setText(itemAtPos+"")
                    t_subject.text = Subject.get(itemIdAtPos.toInt()).subject + ""
                    Log.e("@@>>", Subject.get(itemIdAtPos.toInt()).subject + " ")
                    Log.e("@@>>id", Subject.get(itemIdAtPos.toInt()).subjectid + " ")
                    subjectidnew = Subject.get(itemIdAtPos.toInt()).subjectid + " "
                }
            } else {
                Toast.makeText(this@Addhomework, "Please First Select Class", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    open fun openGallery(): Unit {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder =
            AlertDialog.Builder(this@Addhomework)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val f = File(
                    Environment.getExternalStorageDirectory(),
                    "temp.jpg"
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
                startActivityForResult(intent, 1)
            } else if (options[item] == "Choose from Gallery") {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, 2)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2 || requestCode == PICK_IMAGE) {
                val selectedImage = data!!.data
                val filePath =
                    arrayOf(MediaStore.Images.Media.DATA)
                val c: Cursor? =
                    contentResolver.query(selectedImage!!, filePath, null, null, null)
                c!!.moveToFirst()
                val columnIndex: Int = c.getColumnIndex(filePath[0])
                val picturePath: String = c.getString(columnIndex)
                c.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                i_selectedimage.setImageBitmap(thumbnail)
                imageUri = data?.data!!
                status=true

            }
        }
    }

    @NonNull
    private fun createPartFromString(value: String): RequestBody? {
        return RequestBody.create(
            MultipartBody.FORM, value
        )
    }


    private fun updateDateInView(t: TextView) {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        t.text = sdf.format(cal.time)
    }

    fun sectione() {
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
                Toast.makeText(this@Addhomework, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Section>>,
                response: Response<List<Section>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addhomework, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {

                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@Addhomework,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()
                        cv.visibility = View.GONE

                    } else {

                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Section.addAll(listOf(response.body()!!.get(i)))
                        }

                        val myListAdapter = MyListAdapterSection(this@Addhomework, Section)
                        list.adapter = myListAdapter
                        cv.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(this@Addhomework, "Don't Have Any Data", Toast.LENGTH_LONG)
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
                Toast.makeText(this@Addhomework, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Subject>>,
                response: Response<List<Subject>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addhomework, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@Addhomework,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()
                        cv.visibility = View.GONE
                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
//                                Subject.addAll(listOf(response.body()!!.get(i).subject))
                            Subject.add(
                                Subject(
                                    response.body()!!.get(i).subjectid,
                                    response.body()!!.get(i).subject
                                )
                            )
                        }

                        val myListAdapter = MyListAdapter(this@Addhomework, Subject)
                        list.adapter = myListAdapter
//                            cv.visibility=View.GONE
                    }
                } else {
                    Toast.makeText(this@Addhomework, "Don't Have Any Data", Toast.LENGTH_LONG)
                        .show()
                    progressDialog.dismiss()
                    cv.visibility = View.GONE
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
                Toast.makeText(this@Addhomework, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Classlist>>,
                response: Response<List<Classlist>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addhomework, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@Addhomework,
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

                        val myListAdapter = MyListAdapterclass(this@Addhomework, Classlist)
                        list.adapter = myListAdapter
//                        cv.visibility=View.GONE
                    }
                } else {
                    Toast.makeText(this@Addhomework, "Don't Have Any Data", Toast.LENGTH_LONG)
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
        this@Addhomework.onVisibleBehindCanceled()
        super.onDestroy()
    }

    fun makeRequest() {
        ActivityCompat.requestPermissions(
            this@Addhomework,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
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
//                    makeRequest()
                    dialog()
                } else {
                    Log.i("@@permission", "Permission has been granted by user")
                }
            }
        }
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
        Picasso.with(this@Addhomework)
            .load("http://vidhyalaya.co.in/sspanel/" + getdata(Data.image))
            .error(R.drawable.school)
            .into(imageview)
    }

    fun alertdilog1() {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
        }

        builder!!.setMessage("Homework Type")
            .setTitle("HomeWork")

        builder.apply {
            setPositiveButton("Homework to a Student") { dialog, id ->

                l1.visibility = View.VISIBLE
                dialog.dismiss()
                type = "s"

            }
            setNegativeButton("Homework to a Class") { dialog, id ->
                l1.visibility = View.GONE
                dialog.dismiss()
                type = "c"
            }
        }
        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
        dialog.setCancelable(false)
    }


    fun inserthomeworkStudent() {

        if (status == false) {
            Toast.makeText(this@Addhomework, "Please wait....", Toast.LENGTH_LONG).show()
            progressDialog.show()
/*
            val file = File(FileUtils.getPath(this@Addhomework, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)*/
            val parts =
                MultipartBody.Part.createFormData("file", "_")

            val school_id1 = createPartFromString(getdata(Data.school_id) + "")
            val classid1 = createPartFromString((classid).toString() + "")
            val sectionid1 = createPartFromString(sectionidst + "")
            val faculty1 = createPartFromString(getdata(Data.Facultyid) + "")
            val subject1 = createPartFromString(subjectidnew.toString())
            val title1 = createPartFromString(e_title.text.toString() + "")
            val description1 = createPartFromString(e_description.text.toString() + "")
            val submission_date1 = createPartFromString(t_date.text.toString() + "")
            val homeworktype1 = createPartFromString("a_student")
            val s_listide1 = createPartFromString(s_listid.toString())
            Log.e(
                "@@inserthomwworkclass",
                "user_id \n" + getdata(Data.Userid) + "" + "school_id " + getdata(Data.school_id)
            )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.getinserthomeworkstudent()!!.setdata_homeworkstudent(
                    school_id1,
                    classid1,
                    sectionid1,
                    s_listide1,
                    faculty1,
                    subject1,
                    title1,
                    description1,
                    submission_date1,
                    homeworktype1,
                    parts
                )

            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@in", "e " + t.message!!)
                    Toast.makeText(this@Addhomework, t.message!!, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    Utlity.showErrorDialgo(this@Addhomework)
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {
                    Log.e("@@in", response.body().toString())
                    Log.e("@@in", response.body()?.status.toString())
                    Toast.makeText(
                        this@Addhomework,
                        response.body()!!.status + " ",
                        Toast.LENGTH_SHORT
                    ).show()
                    ok()
                    status = false
                    progressDialog.dismiss()
                }
            })

        } else {
            progressDialog.show()

            val file = File(FileUtils.getPath(this@Addhomework, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val parts =
                MultipartBody.Part.createFormData("file", file.name, requestFile)


            val school_id = createPartFromString(getdata(Data.school_id) + "")
            val classid = createPartFromString((classid).toString() + "")
            val sectionid = createPartFromString(sectionidst + "")
            val faculty = createPartFromString(getdata(Data.Facultyid) + "")
            val subject = createPartFromString(subjectidnew.toString())
            val title = createPartFromString(e_title.text.toString() + "")
            val description = createPartFromString(e_description.text.toString() + "")
            val submission_date = createPartFromString(t_date.text.toString() + "")
            val homeworktype = createPartFromString("a_student")
            val s_listide = createPartFromString(s_listid.toString())
            Log.e(
                "@@inserthomwworkclass",
                "user_id \n" + getdata(Data.Userid) + "" + "school_id " + getdata(Data.school_id)
            )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.getinserthomeworkstudent()!!.setdata_homeworkstudent(
                    school_id,
                    classid,
                    sectionid,
                    s_listide,
                    faculty,
                    subject,
                    title,
                    description,
                    submission_date,
                    homeworktype,
                    parts
                )

            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message!!)
                    Toast.makeText(this@Addhomework, t.message!!, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    Utlity.showErrorDialgo(this@Addhomework)
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {
                    Log.e("@@", response.body().toString())
                    Toast.makeText(
                        this@Addhomework,
                        response.body()!!.status + " ",
                        Toast.LENGTH_SHORT
                    ).show()
                    ok()
                    status = false
                    progressDialog.dismiss()
                }
            })

        }
    }

    fun inserthomeworkClass() {

        if (status == false) {
            Toast.makeText(this@Addhomework, "Please wait....", Toast.LENGTH_LONG).show()
            progressDialog.show()

            val parts =
                MultipartBody.Part.createFormData("file", "_")

            val school_id = createPartFromString(getdata(Data.school_id) + "")
            val classid = createPartFromString((classid).toString() + "")
            val sectionid = createPartFromString(sectionidst + "")
            val faculty = createPartFromString(getdata(Data.Facultyid) + "")
            val subject = createPartFromString(subjectidnew.toString())
            val title = createPartFromString(e_title.text.toString() + "")
            val description = createPartFromString(e_description.text.toString() + "")
            val submission_date = createPartFromString(t_date.text.toString() + "")
            val homeworktype = createPartFromString("a_class")
            Log.e(
                "@@inserthomwworkclass",
                "user_id \n" + getdata(Data.Userid) + "" + "school_id " + getdata(Data.school_id)
            )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.sethomework()!!.setdata_homework(
                    school_id, classid, sectionid,
                    faculty, subject, title, description, submission_date, homeworktype, parts
                )
            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message!!)
                    Toast.makeText(this@Addhomework, t.message!!, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    Utlity.showErrorDialgo(this@Addhomework)
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {
                    Log.e("@@", response.body().toString() + " ")
                    Toast.makeText(
                        this@Addhomework,
                        response.body()!!.status + " ",
                        Toast.LENGTH_SHORT
                    ).show()
                    status = false
                    progressDialog.dismiss()
                    ok()
                }
            })
        } else {
            progressDialog.show()
            val file = File(FileUtils.getPath(this@Addhomework, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val parts =
                MultipartBody.Part.createFormData("file", file.name, requestFile)

            val school_id = createPartFromString(getdata(Data.school_id) + "")
            val classid = createPartFromString((classid).toString() + "")
            val sectionid = createPartFromString(sectionidst + "")//89
            val faculty = createPartFromString(getdata(Data.Facultyid) + "")
            val subject = createPartFromString(subjectidnew.toString())
            val title = createPartFromString(e_title.text.toString() + "")
            val description = createPartFromString(e_description.text.toString() + "")
            val submission_date = createPartFromString(t_date.text.toString() + "")
            val homeworktype = createPartFromString("a_class")
            Log.e(
                "@@inserthomwworkclass",
                "user_id \n" + getdata(Data.Userid) + "" + "school_id " + getdata(Data.school_id)
            )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.sethomework()!!.setdata_homework(
                    school_id, classid, sectionid,
                    faculty, subject, title, description, submission_date, homeworktype, parts
                )
            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message!!)
                    Toast.makeText(this@Addhomework, t.message!!, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    Utlity.showErrorDialgo(this@Addhomework)
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {

                    Toast.makeText(
                        this@Addhomework,
                        response.body()!!.status + " ",
                        Toast.LENGTH_SHORT
                    ).show()
                    status = false
                    progressDialog.dismiss()
                    ok()
                }
            })

        }

    }

    fun studentlist() {
        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Studentlistapi>> =
            preferences.getstudentlist()!!.getdata(
                getdata(Data.school_id),
                classid.toString(),
                section_id,
                getdata(Data.schoolSession)
            )
        Log.e(
            "@@studentlist ",
            "s_id " + getdata(Data.school_id) + "c_id " + classid.toString() + "section " + section_id.toString() + "s_session " + getdata(
                Data.schoolSession
            )
        )
        loginResponseCall.enqueue(object : Callback<List<Studentlistapi>> {
            override fun onFailure(call: Call<List<Studentlistapi>>, t: Throwable) {
                Log.e("@@studentlist", t.message!!)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Studentlistapi>>,
                response: Response<List<Studentlistapi>>
            ) {

                progressDialog.dismiss()
                if (response.isSuccessful) {


                    if (response.body()!!.size > 0) {

                        for (i in 0 until response.body()!!.size) {
                            Log.e("@@userlist", response.body()!!.get(i).id)
                            mutableListOf<Studentlistapi>()
                            friends.add(
                                Friend(
                                    response.body()!!.get(i).id,
                                    response.body()!!.get(i).student
                                )
                            )

                        }

                        cv.visibility = View.VISIBLE
                        b_studentslist.visibility = View.VISIBLE
                        adapter = ListViewAdapter1(this@Addhomework, R.layout.listitem, friends)
                        list.adapter = adapter
                    } else {
                        Toast.makeText(
                            this@Addhomework,
                            "There are no children available in the section of this class",
                            Toast.LENGTH_SHORT
                        ).show()
                        cv.visibility = View.INVISIBLE
                        b_studentslist.visibility = View.INVISIBLE
                        cv.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@Addhomework, "List is Empty", Toast.LENGTH_SHORT).show()
                    cv.visibility = View.INVISIBLE
                    b_studentslist.visibility = View.INVISIBLE
                    cv.visibility = View.GONE
                }
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun generateDataToContainerLayout(): StringBuilder {
        var i = 0
        s_listnumber = 0
        s_listid = StringBuilder()
        s_listname = StringBuilder()
        if (friends.size == i) { //do nothing
        }
        while (friends.size > i) {
            val friend = friends[i]
            Log.e("@@", friend.name)
            if (friend.isSelected) {
                Log.e("@@ListActivity", "here" + friend.name)
//                s_listid.append("'"+(friend.isId).toString()+"'"+",")
//                s_listname.append("'"+(friend.name).toString()+"'"+",")

                if (apendboolean == false) {
                    apendboolean = true
                    s_listid.append((friend.isId).toString())
                    s_listname.append((friend.name).toString())
                } else {
                    s_listid.append("," + (friend.isId).toString())
                    s_listname.append("," + (friend.name).toString())
                }

                s_listnumber++
            }
            i++
        }
        return s_listid
    }

    fun ok() {
        Utlity.showDialog(
            this,
            "Your request submitted Successfully!",
            SweetAlertDialog.SUCCESS_TYPE
        )
    }
}