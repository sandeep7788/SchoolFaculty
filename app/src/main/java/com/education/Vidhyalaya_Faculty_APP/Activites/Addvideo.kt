package com.education.Vidhyalaya_Faculty_APP.Activites

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.*
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
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
import com.education.Vidhyalaya_Faculty_APP.R
import com.education.Vidhyalaya_Faculty_APP.API.Data
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.*
import com.education.Vidhyalaya_Faculty_APP.API.FileUtils
import com.education.Vidhyalaya_Faculty_APP.API.Profileupdateapi
import com.education.Vidhyalaya_Faculty_APP.API.Services
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapter
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapterSection
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

class Addvideo: AppCompatActivity() {

    lateinit var imageview:ImageView
    private val PICK_IMAGE: Int = 100
    lateinit var imageUri: Uri
    var status: Boolean = false
    var RECORD_REQUEST_CODE = 101
    lateinit var progressDialog: android.app.AlertDialog
    var Classlist=ArrayList<Classlist>()
    var Section=ArrayList<Section>()
    var Subject=ArrayList<Subject>()
    lateinit var list:ListView
    lateinit var itemAtPos:String
    lateinit var cv:CardView
    lateinit var i_date: ImageView
    var cal = Calendar.getInstance()
    lateinit var t_date:TextView
    var type="0"
    lateinit var i_selectedimage:VideoView
    var classid=0
    lateinit var c_selecttype:CardView
    lateinit var i_closew_c_selecttype:ImageView
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
    var apendboolean=false
    var sectionidst= "0"
    var section_id: Int? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addvideo)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        cv=findViewById(R.id.cv)
        imageview=findViewById(R.id.imageview)
        progressDialog= SpotsDialog.Builder().setContext(this@Addvideo).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Please wait....")
        progressDialog.setCancelable(false)
        list=findViewById(R.id.list)
        c_selecttype=findViewById(R.id.c_selecttype)
        i_closew_c_selecttype=findViewById(R.id.i_closew_c_selecttype)
        r_class=findViewById(R.id.r_class)
        r_student=findViewById(R.id.r_student)
        c_button=findViewById(R.id.c_button)
        i_date = findViewById(R.id.i_date)
        t_date=findViewById(R.id.t_date)
        i_selectedimage=findViewById(R.id.i_selectedvideo)
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

        var MyReceiver: BroadcastReceiver?= null;
        MyReceiver = com.education.vidhyalaya.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        var i_backbutton1:ImageView=findViewById(R.id.i_backbutton1)
        i_backbutton1.setOnClickListener {
            var intent= Intent(this,
                Dashbord::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        b_studentslist.setOnClickListener {
            Log.e("@@data",generateDataToContainerLayout().toString())
            Log.e("@@l",s_listid.toString())
            cv.visibility=View.GONE
            t_s_list.setText("Selected STD :- "+(s_listnumber).toString())
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
                        "0"-> {Toast.makeText(this@Addvideo, "Somthing Wrong", Toast.LENGTH_LONG).show()}
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
                        "c"-> {inserthomeworkStudent1()}
                    }
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
                    this@Addvideo,
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

            t_section.setText("")
            t_section.setHint("_")
            t_subject.setText("")
            t_subject.setHint("_")
            t_s_list.setText("_")
            t_date.setText("")
            t_date.setHint("00:00:00")
            s_listnumber=0
            apendboolean=false


            getclasslist()
            var t:TextView=findViewById(R.id.t_class)
            list.setOnItemClickListener(){
                    adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)

                cv.visibility=View.GONE
                t.setText("Class :- "+Classlist.get(itemIdAtPos.toInt()).class_+"")

                classid=(Classlist.get(itemIdAtPos.toInt()).id+"").trim().toInt()

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

                cv.visibility=View.GONE
                t.setText("Section :- "+itemAtPos+"")
                sectionidst=itemAtPos
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

                    cv.visibility=View.GONE
                    t_subject.setText("Subject :- "+Subject.get(itemIdAtPos.toInt()).subject+"")
                }
            }
            else
            {
                Toast.makeText(this@Addvideo, "Please First Select Class", Toast.LENGTH_LONG).show()
            }
        }
    }

    open fun openGallery(): Unit {

        val gallery = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data!!
//            i_selectedimage.setImageURI(imageUri)
            i_selectedimage.setVideoURI(imageUri)
            Log.e("@@", imageUri.toString())
            Log.e("@@", PICK_IMAGE.toString())

//            val uri = Uri.parse(TEST_URL)
            i_selectedimage.setVideoURI(imageUri)
            i_selectedimage.requestFocus()
            i_selectedimage.start()

            status = true
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
                Toast.makeText(this@Addvideo,"Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility=View.GONE
            }

            override fun onResponse(
                call: Call<List<Section>>,
                response: Response<List<Section>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addvideo,"wait", Toast.LENGTH_SHORT)
                if(response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@Addvideo, "No Data Found Try Later", Toast.LENGTH_LONG).show()

                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Section.addAll(listOf(response.body()!!.get(i)))
                        }

                        val myListAdapter = MyListAdapterSection(this@Addvideo,Section)
                        list.adapter = myListAdapter


                    }
                }
                else
                {
                    Toast.makeText(this@Addvideo,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                    cv.visibility=View.GONE
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
                Toast.makeText(this@Addvideo, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility=View.GONE
            }

            override fun onResponse(
                call: Call<List<Subject>>,
                response: Response<List<Subject>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addvideo, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@Addvideo,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
//                                Subject.addAll(listOf(response.body()!!.get(i).subject))
                            Subject!!.add(Subject(response.body()!!.get(i).subjectid,response.body()!!.get(i).subject))
                        }

                        val myListAdapter = MyListAdapter(this@Addvideo, Subject)
                        list.adapter = myListAdapter

                    }
                } else {
                    Toast.makeText(this@Addvideo, "Don't Have Any Data", Toast.LENGTH_LONG)
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
                Toast.makeText(this@Addvideo,"Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility=View.GONE
            }

            override fun onResponse(
                call: Call<List<Classlist>>,
                response: Response<List<Classlist>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Addvideo,"wait", Toast.LENGTH_SHORT)
                if(response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@Addvideo, "No Data Found Try Later", Toast.LENGTH_LONG).show()

                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Classlist.addAll(listOf(response.body()!!.get(i)))

                        }

                        val myListAdapter = MyListAdapterclass(this@Addvideo,Classlist)
                        list.adapter = myListAdapter

                    }
                }
                else
                {
                    Toast.makeText(this@Addvideo,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                    cv.visibility=View.GONE
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
        this@Addvideo!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
    fun makeRequest() {
        ActivityCompat.requestPermissions(
            this@Addvideo!!,
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
        var value = data.getString(key,"_")
        return value
    }

    fun list():String
    {
        var data="...."
        list.setOnItemClickListener(){adapterView, view, position, id ->
            itemAtPos = (adapterView.getItemAtPosition(position)).toString()
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

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
        Picasso.with(this@Addvideo).load("http://vidhyalaya.co.in/sspanel/"+getdata(Data.image))
            .error(R.drawable.school)
            .into(imageview)
    }

    override fun onStart() {
        super.onStart()

    }

    fun alertdilog1()
    {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
        }

        builder!!.setMessage("Upload Video")
            .setTitle("Video Update")

        builder.apply {
            setPositiveButton("Video to a Student") { dialog, id ->

                l1.visibility=View.VISIBLE
                dialog.dismiss()
                type="s"

            }
            setNegativeButton("Video to a Class") { dialog, id ->
                l1.visibility=View.INVISIBLE
                dialog.dismiss()
                type="c"
                var l_student:LinearLayout
                l_student=findViewById(R.id.l_student)
                l_student.visibility=View.GONE

                var l_main:LinearLayout
                l_main=findViewById(R.id.l_main)
                l_main.weightSum= 2F
            }
        }

        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
        dialog.setCancelable(false)
    }

    fun inserthomeworkStudent()
    {

        if (status == false) {
            Toast.makeText(this@Addvideo, "Please Select Image", Toast.LENGTH_LONG).show()
        } else {
            progressDialog.show()

            val file = File(FileUtils.getPath(this@Addvideo, imageUri))
            val videoBody: RequestBody =
                RequestBody.create(MediaType.parse("video/*"), file)
            val vFile =
                MultipartBody.Part.createFormData("file", file.name, videoBody)

            val school_id1 = createPartFromString(getdata(Data.school_id)+""!!)
            val classid1 = createPartFromString((classid).toString()+"")
            val sectionid1 = createPartFromString(t_section.text.toString()+"")
            val student1 = createPartFromString(s_listid.toString()!!)
            val faculty1 = createPartFromString(getdata(Data.Facultyid)+""!!)
            val title1 = createPartFromString(e_title.text.toString()+"")
            val description1 = createPartFromString(e_description.text.toString()+"")
            val videotype1 = createPartFromString("a_student")

            Log.e("@@inserthomwworkclass", "s_listid " + s_listid.toString()+""
                    +"school_id " + getdata(Data.school_id) )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.getinsertvideo()!!.setdata_homeworkstudent(school_id1,classid1,sectionid1,student1,
                    faculty1,title1,description1,videotype1,vFile)

            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message!!)
                    Toast.makeText(this@Addvideo, t.message!!, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss()
                    }
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {
                    Log.e("@@",response.body().toString()+"")
                    Toast.makeText(this@Addvideo, response.body()!!.status+"", Toast.LENGTH_SHORT).show()
                    status = false
                    progressDialog.dismiss()
                    ok()
                }
            })
        }
    }

    fun inserthomeworkStudent1()
    {

        if (status == false) {
            Toast.makeText(this@Addvideo, "Please Select Image", Toast.LENGTH_LONG).show()
        } else {
            progressDialog.show()

            val file = File(FileUtils.getPath(this@Addvideo, imageUri))
            val videoBody: RequestBody =
                RequestBody.create(MediaType.parse("video/*"), file)
            val vFile =
                MultipartBody.Part.createFormData("file", file.name, videoBody)

            val school_id1 = createPartFromString(getdata(Data.school_id)+""!!)
            val classid1 = createPartFromString((classid).toString()+"")
            val sectionid1 = createPartFromString("0"+"")
            val faculty1 = createPartFromString(getdata(Data.Facultyid)+""!!)
            val title1 = createPartFromString(e_title.text.toString()+"")
            val description1 = createPartFromString(e_description.text.toString()+"")
            val videotype1 = createPartFromString("a_class")

            Log.e("@@inserthomwworkclass", "getdata(Data.school_id) "+getdata(Data.school_id)+""
                    +"classid "+classid.toString()+""+"sectionid 0"+" faculty"+getdata(Data.Facultyid)
                    +"school_id " + getdata(Data.school_id) )

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.getinsertvideo1()!!.setdata_homeworkstudent(school_id1,classid1,sectionid1,
                    faculty1,title1,description1,videotype1,vFile)

            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message!!)
                    Toast.makeText(this@Addvideo, t.message!!, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss()
                    }
                    cv.visibility=View.GONE
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {
                    Log.e("@@",response.body().toString())
                    Toast.makeText(this@Addvideo,"Submited Successfully", Toast.LENGTH_SHORT).show()
                    status = false
                    progressDialog.dismiss()
                    ok()
                }
            })

        }
    }



    fun studentlist()
    {
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<Studentlistapi>> =
            preferences.getstudentlist()!!.getdata(getdata(Data.school_id),classid.toString(),section_id,getdata(Data.schoolSession))
//            preferences.getstudentlist()!!.getdata("32","720","","2020-2021")
//        Log.e("@@studentlist ","s_id "+getdata(Data.school_id)+"c_id "+classid.toString()+"section "+t_section.text.toString()+"s_session "+getdata(Data.schoolSession))
        loginResponseCall.enqueue(object : Callback<List<Studentlistapi>>
        {
            override fun onFailure(call: Call<List<Studentlistapi>>, t: Throwable) {

                progressDialog.dismiss()
                cv.visibility=View.GONE
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
                        Log.e("@@",response.body()!!.get(i).id+""+response.body()!!.get(i).student)

                    }

                    cv.visibility=View.VISIBLE
                    b_studentslist.visibility=View.VISIBLE
                    adapter = ListViewAdapter1(this@Addvideo, R.layout.listitem, friends)
                    list.setAdapter(adapter)

                }
                else
                {
                    Toast.makeText(this@Addvideo,"List is Empty", Toast.LENGTH_SHORT).show()
                    cv.visibility=View.INVISIBLE
                    b_studentslist.visibility=View.INVISIBLE
                    cv.visibility=View.GONE
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

                if(apendboolean==false)
                {
                    apendboolean=true
                    s_listid.append((friend.isId).toString())
                    s_listname.append((friend.name).toString())
                }
                else
                {
                    s_listid.append(","+(friend.isId).toString())
                    s_listname.append(","+(friend.name).toString())
                }

                s_listnumber++
            }
            i++ // rise i
        }
        return s_listid
    }

    fun ok()
    {
        var ok=findViewById<ImageView>(R.id.ok)
        ok.visibility=View.VISIBLE
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            var intent= Intent(this@Addvideo,LoadMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("name","video")
            startActivity(intent)
            // close this activity
        }, 1000)
    }
}