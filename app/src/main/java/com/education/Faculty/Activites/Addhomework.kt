package com.education.Faculty.Activites

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.dating.schoolfaculty.R
import com.education.Faculty.API.Data
import com.education.Faculty.API.Faculty.*
import com.education.Faculty.API.FileUtils
import com.education.Faculty.API.Profileupdateapi
import com.education.Faculty.API.Services
import com.education.Faculty.Adapter.MyListAdapter
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Addhomework: AppCompatActivity() {

    lateinit var imageview:ImageView
    private val PICK_IMAGE: Int = 100
    lateinit var imageUri: Uri
    var status: Boolean = false
    var RECORD_REQUEST_CODE = 101
    lateinit var progressDialog: android.app.AlertDialog
    var Classlist=ArrayList<Classlist>()
    var Section=ArrayList<String>()
    var Subject=ArrayList<String>()
    lateinit var list:ListView
    lateinit var itemAtPos:String
    lateinit var cv:CardView
    lateinit var i_date: ImageView
    var cal = Calendar.getInstance()
    lateinit var t_date:TextView
    var type="0"
    lateinit var i_selectedimage:ImageView
    var classid=0
    lateinit var c_selecttype:CardView
    lateinit var i_closew_c_selecttype:ImageView
    lateinit var rg:RadioGroup
    lateinit var r_class:RadioButton
    lateinit var r_student:RadioButton
    lateinit var c_button:Button
    lateinit var e_title:EditText
    lateinit var e_description:EditText
    lateinit var t:TextView
    lateinit var t_subject:TextView
    lateinit var t_section:TextView
    lateinit var cv1:CardView
    lateinit var friends: ArrayList<Friend>
    private var adapter: ListViewAdapter1? = null
    var i = 0
    lateinit var b_studentslist:Button
    lateinit var l1:LinearLayout
    lateinit var s_listid:StringBuilder
    lateinit var s_listname:StringBuilder
    var s_listnumber:Int = 0
    lateinit var t_s_list:TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhomework)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        cv=findViewById(R.id.cv)
        imageview=findViewById(R.id.imageview)
        progressDialog= SpotsDialog.Builder().setContext(this@Addhomework).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        list=findViewById(R.id.list)
        c_selecttype=findViewById(R.id.c_selecttype)
        i_closew_c_selecttype=findViewById(R.id.i_closew_c_selecttype)
        rg=findViewById(R.id.rg)
        r_class=findViewById(R.id.r_class)
        r_student=findViewById(R.id.r_student)
        c_button=findViewById(R.id.c_button)
        i_date = findViewById(R.id.i_date)
        t_date=findViewById(R.id.t_date)
        i_selectedimage=findViewById(R.id.i_selectedimage)
        e_title=findViewById(R.id.e_title)
        e_description=findViewById(R.id.e_description)
        t=findViewById(R.id.t_subject)
        t_subject=findViewById(R.id.t_subject)
        t_section=findViewById(R.id.t_section)
        cv1=findViewById(R.id.cv1)
        b_studentslist=findViewById(R.id.b_studentslist)
        l1=findViewById(R.id.l1)
        t_s_list=findViewById(R.id.t_s_list)
        Classlist= ArrayList()
        Section=ArrayList()
        Subject=ArrayList()
        friends=ArrayList()


        alertdilog1()
        makeRequest()
        setinfo()


        b_studentslist.setOnClickListener {
            Log.e("@@data",generateDataToContainerLayout().toString())
            Log.e("@@l",s_listid.toString())
            cv.visibility=View.GONE
            t_s_list.setText("selected ST :- "+(s_listnumber).toString())
        }


        var b_studentlist:Button=findViewById(R.id.b_studentlist)
        b_studentlist.setOnClickListener {
            if(classid==0)
            {

                Toast.makeText(this, "Please Select Any Class", Toast.LENGTH_LONG).show()

            }
            else
            {
                Section.clear()
                Classlist.clear()
                Subject.clear()
                friends.clear()
                studentlist()
            }
        }

        var button=findViewById<Button>(R.id.button)
            .setOnClickListener {
                if(classid==0)
                {

                    Toast.makeText(this, "Please Select Any Class", Toast.LENGTH_LONG).show()

                }

                else if(t_section.text.toString().equals("")&&t_section.text.toString().isEmpty())
                {
                    Toast.makeText(applicationContext,"Please Select Your Section",Toast.LENGTH_SHORT).show()
                }
                else if(t_subject.text.toString().equals("")&&t_subject.text.toString().isEmpty())
                {
                    Toast.makeText(applicationContext,"Please Select Your Subject",Toast.LENGTH_SHORT).show()
                }

                else if(t_date.text.isEmpty())
                {
                    t_date.setError("Select Date")
                }
                else if(e_title.text.isEmpty())
                {
                    e_title.setError("Title Not be Empty")
                }
                else if((e_description.text.toString().isEmpty())&&((e_description.text.toString().trim()).equals("")))
                {
                    e_description.setError("Enter Long Description")
                }
                else {
                    when(type)
                    {
                        "0"-> {Toast.makeText(this@Addhomework, "Somthing Wrong", Toast.LENGTH_LONG).show()}
                        "s"-> {
                            if(s_listnumber<=0)
                            {
                                Toast.makeText(applicationContext,"Please Select Student",Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                inserthomeworkStudent()
                            }
                        }
                        "c"-> {inserthomeworkClass()}
                    }
                }
            }


        rg.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change :"+
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                if(radio.text.toString().equals("Student wise"))
                {
                    cv1.visibility=View.GONE
                }
                else
                {
                    cv1.visibility=View.VISIBLE
                }
            })

        c_button.setOnClickListener{
            // Get the checked radio button id from radio group
            var id: Int = rg.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                val radio:RadioButton = findViewById(id)
                Toast.makeText(applicationContext,"On button click :" +
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                c_selecttype.visibility=View.GONE
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"On button click :" +
                        " nothing selected",
                    Toast.LENGTH_SHORT).show()
            }
        }

    var i_image:ImageView=findViewById(R.id.i_image)
        i_image.setOnClickListener { openGallery() }

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

        i_date!!.setOnClickListener(object : View.OnClickListener {
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



        var b_class:Button=findViewById(R.id.b_class)
        b_class.setOnClickListener {
            cv.visibility=View.VISIBLE
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()
            getclasslist()
            var t:TextView=findViewById(R.id.t_class)
            list.setOnItemClickListener(){
                    adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this, "Click on item at ${itemAtPos} its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                cv.visibility=View.GONE
                t.setText(Classlist.get(itemIdAtPos.toInt()).class_+"")
                Log.e("@@","itemAtPos "+itemAtPos)
                Log.e("@@","itemIdAtPos "+itemIdAtPos)
                Log.e("@@","itemIdAtPos class "+Classlist.get(itemIdAtPos.toInt()).class_)
                Log.e("@@","itemIdAtPos id "+Classlist.get(itemIdAtPos.toInt()).id)

                classid=(Classlist.get(itemIdAtPos.toInt()).id+" ").trim().toInt()

                }
             }

        var b_section:Button=findViewById(R.id.b_section)
        b_section.setOnClickListener {
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()
            cv.visibility=View.VISIBLE
            sectione()
            var t:TextView=findViewById(R.id.t_section)
            list.setOnItemClickListener(){adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                cv.visibility=View.GONE
                t.setText(itemAtPos+"")
            }
        }

        var b_subjet:Button=findViewById(R.id.b_subjet)
        b_subjet.setOnClickListener {
            Section.clear()
            Classlist.clear()
            Subject.clear()
            friends.clear()
            if(classid!=0)
            {
                cv.visibility=View.VISIBLE
            subjecte()
            list.setOnItemClickListener(){adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                cv.visibility=View.GONE
                t_subject.setText(itemAtPos+"")
            }
            }
            else
            {
                Toast.makeText(this@Addhomework, "Please First Select Class", Toast.LENGTH_LONG).show()
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

    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(rg.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio.text}",
            Toast.LENGTH_SHORT).show()
    }

    private fun updateDateInView(t: TextView) {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        t!!.text = sdf.format(cal.getTime())
    }

    fun sectione()
    {
        Section.clear()
        Classlist.clear()
        Subject.clear()
        friends.clear()
        Log.e("@@class","call")
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<Section>> =
            preferences.getsectionlist()!!.getdata(getdata(Data.school_id))

        loginResponseCall.enqueue(object : Callback<List<Section>>
        {
            override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                Log.e("@@e","error")
                Toast.makeText(this@Addhomework,"Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
            }

            override fun onResponse(
                call: Call<List<Section>>,
                response: Response<List<Section>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addhomework,"wait", Toast.LENGTH_SHORT)
                if(response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@Addhomework, "No Data Found Try Later", Toast.LENGTH_LONG).show()

                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Section.addAll(listOf(response.body()!!.get(i).section))
                        }

                        val myListAdapter = MyListAdapter(this@Addhomework,Section)
                        list.adapter = myListAdapter

                    }
                }
                else
                {
                    Toast.makeText(this@Addhomework,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }
            }
        })
    }

    fun subjecte()
    {

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

                        } else {
                            Log.e("@@userlist", response.body().toString())
                            Log.e("@@userlist", response.body().toString())
                            for (i in 0 until response.body()!!.size) {
                                mutableListOf<Classlist>()
                                Subject.addAll(listOf(response.body()!!.get(i).subject))
                            }

                            val myListAdapter = MyListAdapter(this@Addhomework, Subject)
                            list.adapter = myListAdapter

                        }
                    } else {
                        Toast.makeText(this@Addhomework, "Don't Have Any Data", Toast.LENGTH_LONG)
                            .show()
                        progressDialog.dismiss()
                    }
                }
            })
    }

    fun getclasslist() {
        Section.clear()
        Classlist.clear()
        Subject.clear()
        friends.clear()
        Log.e("@@class","call")
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<Classlist>> =
            preferences.getclass()!!.getdata(getdata(Data.school_id))

        loginResponseCall.enqueue(object : Callback<List<Classlist>>
        {
            override fun onFailure(call: Call<List<Classlist>>, t: Throwable) {
                Log.e("@@e","error")
                Toast.makeText(this@Addhomework,"Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
            }

            override fun onResponse(
                call: Call<List<Classlist>>,
                response: Response<List<Classlist>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addhomework,"wait", Toast.LENGTH_SHORT)
                if(response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@Addhomework, "No Data Found Try Later", Toast.LENGTH_LONG).show()

                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Classlist.addAll(listOf(response.body()!!.get(i)))

                        }

                        val myListAdapter = MyListAdapterclass(this@Addhomework,Classlist)
                        list.adapter = myListAdapter

                    }
                }
                else
                {
                    Toast.makeText(this@Addhomework,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }
            }
        })
    }

    fun openkeybord(edittext: EditText)
    {
        Log.e("@@", "On Keyboard Button click event!")
        edittext.requestFocus();
        edittext.editableText
        edittext.setFocusableInTouchMode(true);
        val imm = edittext.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN, 0)
        imm?.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT)
    }
    override fun onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
        this@Addhomework!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
    fun makeRequest() {
        ActivityCompat.requestPermissions(
            this@Addhomework!!,
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
                    makeRequest()
                } else {
                    Log.i("@@permission", "Permission has been granted by user")
                }
            }
        }
    }

    fun getdata(key:String): String? {
        val sharedPreferences: SharedPreferences = this!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"defoult_value")
        return value
    }

    fun list():String
    {
        var data="...."
        list.setOnItemClickListener(){adapterView, view, position, id ->
            itemAtPos = (adapterView.getItemAtPosition(position)).toString()
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            cv.visibility=View.GONE
            Log.e("@@2",itemAtPos)
            data=itemAtPos
        }
        return data
    }
    fun setinfo()
    {
        var i_closew:ImageView=findViewById(R.id.i_closew)
        i_closew.setOnClickListener { cv.visibility=View.GONE }

        var t_faculty:TextView=findViewById(R.id.t_faculty)
        t_faculty.setText(getdata(Data.Facultyid))

        var t_session:TextView=findViewById(R.id.t_session)
        t_session.setText(getdata(Data.schoolSession))

        var t_name:TextView=findViewById(R.id.t_name)
        t_name.setText(getdata(Data.name))

        var imageview:CircleImageView=findViewById(R.id.imageview)
        Picasso.with(this@Addhomework).load("http://skoolstarr.com/sspanel/"+getdata(Data.image))
            .error(R.drawable.school)
            .into(imageview)
    }

    override fun onStart() {
        super.onStart()

    }
    fun alertdilog1()
    {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }


        builder!!.setMessage("Homework for")
            .setTitle("HomeWork Panel")

        builder.apply {
            setPositiveButton("For Specifec Students") { dialog, id ->

                l1.visibility=View.VISIBLE
                dialog.dismiss()
                type="s"

            }
            setNegativeButton("For All over class") { dialog, id ->
                l1.visibility=View.INVISIBLE
                dialog.dismiss()
                type="c"
            }
        }
        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
        dialog.setCancelable(false)
    }


    fun inserthomeworkStudent()
    {

        if (status == false) {
            Toast.makeText(this@Addhomework, "Please Select Image", Toast.LENGTH_LONG).show()
        } else {
            progressDialog.show()
            val file = File(FileUtils.getPath(this@Addhomework, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val parts =
                MultipartBody.Part.createFormData("file", file.name, requestFile)


            val school_id = createPartFromString(getdata(Data.school_id)+" "!!)
            val classid = createPartFromString((classid).toString()+" ")
            val sectionid = createPartFromString("89"+" ")
            val faculty = createPartFromString(getdata(Data.Facultyid)+" "!!)
            val subject = createPartFromString(t_subject.text.toString()+" ")
            val title = createPartFromString(e_title.text.toString()+" ")
            val description = createPartFromString(e_description.text.toString()+" ")
            val submission_date = createPartFromString(t_date.text.toString()+" ")
            val homeworktype = createPartFromString("a_student")
            val s_listide = createPartFromString(s_listid.toString())
            Log.e("@@inserthomwworkclass", "user_id \n" + getdata(Data.Userid)+" "+"school_id " + getdata(Data.school_id) )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.getinserthomeworkstudent()!!.setdata_homeworkstudent(school_id,classid,sectionid,
                    s_listide,faculty,subject,title,description,submission_date,homeworktype,parts)

            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message)
                    Toast.makeText(this@Addhomework, t.message, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss()
                    }
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {

                    Toast.makeText(this@Addhomework, response.body()!!.status+" ", Toast.LENGTH_SHORT).show()
                    status = false
                    progressDialog.dismiss()
                }
            })

        }
    }

    fun inserthomeworkClass()
    {

        if (status == false) {
            Toast.makeText(this@Addhomework, "Please Select Image", Toast.LENGTH_LONG).show()

        }
        else {
            progressDialog.show()
            val file = File(FileUtils.getPath(this@Addhomework, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val parts =
                MultipartBody.Part.createFormData("file", file.name, requestFile)

            Log.e("inserthomeworkclass","school_id"+getdata(Data.school_id)+" "+
                    "classid"+(classid).toString()+" "+
                    "sectionid"+"00"+" "+
                    "faculty"+getdata(Data.Facultyid)+" "+
                    "submission_date"+t_date.text.toString()+" ")

            val school_id = createPartFromString(getdata(Data.school_id)+" "!!)
            val classid = createPartFromString((classid).toString()+" ")
            val sectionid = createPartFromString("00"+" ")//89
            val faculty = createPartFromString(getdata(Data.Facultyid)+" "!!)
            val subject = createPartFromString(t_subject.text.toString()+" ")
            val title = createPartFromString(e_title.text.toString()+" ")
            val description = createPartFromString(e_description.text.toString()+" ")
            val submission_date = createPartFromString(t_date.text.toString()+" ")
            val homeworktype = createPartFromString("a_class")
            Log.e("@@inserthomwworkclass", "user_id \n" + getdata(Data.Userid)+" "+"school_id " + getdata(Data.school_id) )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.sethomework()!!.setdata_homework(school_id,classid,sectionid,
                    faculty,subject,title,description,submission_date,homeworktype,parts)
            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message)
                    Toast.makeText(this@Addhomework, t.message, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss()
                    }
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {

                    Toast.makeText(this@Addhomework, response.body()!!.status+" ", Toast.LENGTH_SHORT).show()
                    status = false
                    progressDialog.dismiss()
                }
            })

        }

    }

    fun studentlist()
    {
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<Studentlistapi>> =
            preferences.getstudentlist()!!.getdata(getdata(Data.school_id),classid.toString(),"",getdata(Data.schoolSession))
//            preferences.getstudentlist()!!.getdata("32","720","","2020-2021")
        Log.e("@@studentlist ","s_id "+getdata(Data.school_id)+"c_id "+classid.toString()+"section "+t_section.text.toString()+"s_session "+getdata(Data.schoolSession))
        loginResponseCall.enqueue(object : Callback<List<Studentlistapi>>
        {
            override fun onFailure(call: Call<List<Studentlistapi>>, t: Throwable) {
                Log.e("@@studentlist",t.message)
                progressDialog.dismiss()
            }

            override fun onResponse(
                call: Call<List<Studentlistapi>>,
                response: Response<List<Studentlistapi>>
            ) {
                progressDialog.dismiss()
                Log.e("@@size",(response.body()!!.size).toString())
                Log.e("@@",(response.body()).toString())

                if(response.body()!!.size>0) {

                    for (i in 0 until response.body()!!.size) {
                        Log.e("@@userlist", response.body()!!.get(i).id)
                        mutableListOf<Studentlistapi>()
                        friends!!.add(Friend(response.body()!!.get(i).id,response.body()!!.get(i).student))
                        Log.e("@@",response.body()!!.get(i).id+" "+response.body()!!.get(i).student)

                    }
                    cv.visibility=View.VISIBLE
                    b_studentslist.visibility=View.VISIBLE
                    adapter = ListViewAdapter1(this@Addhomework, R.layout.listitem, friends)
                    list.setAdapter(adapter)
                }
                else
                {
                    Toast.makeText(this@Addhomework,"List is Empty", Toast.LENGTH_SHORT).show()
                    cv.visibility=View.INVISIBLE
                    b_studentslist.visibility=View.INVISIBLE
                }
            }
        })
    }
    @SuppressLint("InflateParams")
    private fun generateDataToContainerLayout():StringBuilder {
        var i = 0
        s_listnumber=0
        s_listid=StringBuilder()
        s_listname=StringBuilder()
        if (friends.size == i) { //do nothing
        }
        while (friends.size > i) {
            val friend = friends[i]
            Log.e("@@", friend.name)
            if (friend.isSelected) {
                Log.e("@@ListActivity", "here" + friend.name)
                s_listid.append("'"+(friend.isId).toString()+"'"+",")
                s_listname.append("'"+(friend.name).toString()+"'"+",")
                s_listnumber++
            }
            i++ // rise i
        }
        return s_listid
    }
}