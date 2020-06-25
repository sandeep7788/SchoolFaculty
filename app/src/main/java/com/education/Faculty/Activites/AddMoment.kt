package com.education.Faculty.Activites

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dating.schoolfaculty.R
import com.education.Faculty.API.Data
import com.education.Faculty.API.Faculty.InsertMomentapi
import com.education.Faculty.API.FileUtils
import com.education.Faculty.API.Services
import dmax.dialog.SpotsDialog
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddMoment: AppCompatActivity() {

    lateinit var imageView9:ImageView
    lateinit var i_addimage:ImageView
    lateinit var imageView4:ImageView
    lateinit var i_title:ImageView
    lateinit var i_description:ImageView
    lateinit var e_title:EditText
    lateinit var e_description:EditText
    private val PICK_IMAGE: Int = 100
    lateinit var imageUri: Uri
    var status: Boolean = false
    var RECORD_REQUEST_CODE = 101
    lateinit var progressDialog: android.app.AlertDialog
    lateinit var btn_done:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmoment)

        imageView9=findViewById(R.id.imageView9)
        i_addimage=findViewById(R.id.i_addimage)
        imageView4=findViewById(R.id.imageView4)
        i_title=findViewById(R.id.i_title)
        i_description=findViewById(R.id.i_description)
        e_title=findViewById(R.id.e_title)
        e_description=findViewById(R.id.e_description)
        btn_done=findViewById(R.id.btn_done)
        progressDialog= SpotsDialog.Builder().setContext(this@AddMoment).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)

        makeRequest()
        i_addimage.setOnClickListener { openGallery() }
        btn_done.setOnClickListener {
            if(e_title.text.isEmpty())
            {
                e_title.setError("Title not be Empty")
            }
            else if(e_description.text.isEmpty())
            {
                e_description.setError("Description Not be Empty")
            }
            else {
                function()
            }
        }

        i_title.setOnClickListener { openkeybord(e_title) }
        i_description.setOnClickListener { openkeybord(e_description) }
    }

    fun getdata(key: String): String? {
        var value:String="v_null"
        try {
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("v1", Context.MODE_PRIVATE)
            val data: SharedPreferences = sharedPreferences
            value= data.getString(key, "d_null").toString()
        }
        catch (e: Exception)
        {
            System.out.println("@@e"+e.message.toString())
        }


        return value
    }
    open fun openGallery(): Unit {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data!!
            imageView9.setImageURI(imageUri)
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
    fun function() {

        if (status == false) {
            Toast.makeText(this@AddMoment, "Please Select Image", Toast.LENGTH_LONG).show()
        } else {
            progressDialog.show()
            val file = File(FileUtils.getPath(this@AddMoment, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val parts =
                MultipartBody.Part.createFormData("file", file.name, requestFile)

            val school_id = createPartFromString(getdata(Data.school_id)!!)
            val title = createPartFromString(e_title.text.toString()!!)
            val description = createPartFromString(e_description.text.toString())
            val date = createPartFromString(("00:00:00").toString())
            val faculty = createPartFromString(getdata(Data.Facultyid).toString())
/*            val school_id = createPartFromString("32")
            val title = createPartFromString("title")
            val description = createPartFromString("description")
            val date = createPartFromString("00:00:00")
            val faculty = createPartFromString("faculty")*/

            Log.e(
                "@@3",
                "school_id " + getdata(Data.school_id) + "user_id \n" + getdata(
                    Data.Userid)
            )
            Log.e("@@a", "functione")

            var preferences = Services()
            val loginResponseCall: Call<InsertMomentapi> =
                preferences.setMoment()!!
                    .insertmomrnt(
                        school_id,
                    title,
                    description,
                    date,faculty,
                    parts)

            loginResponseCall.enqueue(object : Callback<InsertMomentapi>
            {
                override fun onFailure(call: Call<InsertMomentapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message)
                    Toast.makeText(this@AddMoment, t.message, Toast.LENGTH_SHORT).show()
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss()
                    }
                }

                override fun onResponse(
                    call: Call<InsertMomentapi>,
                    response: Response<InsertMomentapi>
                ) {
                    status = false
                    if(response.isSuccessful)
                    {
                        Toast.makeText(this@AddMoment,"Inserted"+response.body()!!.status, Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(this@AddMoment,"Inserted but not get valid response", Toast.LENGTH_SHORT).show()
                    }

                }
            })

        }
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
        this@AddMoment!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
    fun makeRequest() {
        ActivityCompat.requestPermissions(
            this@AddMoment!!,
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
}