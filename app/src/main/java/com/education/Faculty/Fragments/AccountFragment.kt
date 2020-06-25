package com.education.Faculty.Fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.education.Faculty.API.Accountapi
import com.education.Faculty.API.Faculty.MomentApi
import com.education.Faculty.API.Profileupdateapi
import com.education.Faculty.API.Data
import com.education.Faculty.API.FileUtils
import com.dating.schoolfaculty.R
import com.education.Faculty.API.Services
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception
import kotlin.collections.ArrayList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AccountFragment : Fragment() {
    lateinit var progressDialog: ProgressDialog
    lateinit var circleImageView: CircleImageView
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var address: EditText
    lateinit var number: EditText
    lateinit var p_edit: ImageView
    var userdata = ArrayList<MomentApi>()
    private val PICK_IMAGE: Int = 100
    lateinit var imageUri: Uri
    var status: Boolean = false
    var RECORD_REQUEST_CODE = 101
    lateinit var i_state:ImageView

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(
                position + 1
            )
        }

        override fun getCount(): Int {
            return 5
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater!!.inflate(R.layout.activity_profile__update, container, false)
        circleImageView = view.findViewById(R.id.imageview)
        name = view.findViewById(R.id.e_name)
        email = view.findViewById(R.id.e_email)
        address = view.findViewById(R.id.e_address)
        number = view.findViewById(R.id.e_number)
        p_edit = view.findViewById(R.id.p_edit)
        i_state=view.findViewById(R.id.i_state)


        i_state.setOnClickListener { openkeybord(address) }

        load_user()
        makeRequest()
        setRetainInstance(true);

        var btn_done = view.findViewById<Button>(R.id.btn_done).setOnClickListener {
            function()
        }
        var button=view.findViewById<ImageView>(R.id.i_sync)
            .setOnClickListener { load_user() }
        userdata = ArrayList()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        p_edit.setOnClickListener { openGallery() }

        return view
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.activity_profile__update, container, false)

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment =
                    PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    fun load_user() {

        var preferences = Services()
        val loginResponseCall: Call<List<Accountapi>> = preferences.getAccountinformation()!!
            .getAccountinfo(getdata(Data.school_id), getdata(
                Data.Userid))
        Log.e("@@1", "section" + getdata(Data.school_id) + " user_id " + getdata(
            Data.Userid))

        loginResponseCall.enqueue(object : Callback<List<Accountapi>> {
            override fun onFailure(call: Call<List<Accountapi>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
            }

            override fun onResponse(
                call: Call<List<Accountapi>>,
                response: Response<List<Accountapi>>
            ) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }

                if(response.isSuccessful) {
                    name.setText(getdata(Data.name))
                    email.setText(response.body()!!.get(0).email)
                    address.setText(response.body()!!.get(0).address)
                    number.setText(response.body()!!.get(0).phonenumber)
                    Picasso.with(context)
                        .load(
                            "http://skoolstarr.com/sspanel/" + response.body()!!.get(0).profileimage
                        )
                        .error(R.drawable.ic_account_circle_black_24dp)
                        .resize(100, 100)
                        .into(circleImageView)
                    setdata(Data.image, response.body()!!.get(0).profileimage)
                } else {
                    Toast.makeText(context, "not get Valid data", Toast.LENGTH_SHORT).show()
                    Log.e("@@", "else part")
                }
            }
        })
    }

    fun getdata(key: String): String? {
        var value:String="v_null"
        try {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
            val data: SharedPreferences = sharedPreferences
             value= data.getString(key, "d_null").toString()
        }
        catch (e:Exception)
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
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data!!
            circleImageView.setImageURI(imageUri)
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
            Toast.makeText(context, "Please Select Image", Toast.LENGTH_LONG).show()
        } else {
            progressDialog.show()
            val file = File(FileUtils.getPath(context, imageUri))
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val parts =
                MultipartBody.Part.createFormData("file", file.name, requestFile)

            val school_id = createPartFromString(getdata(Data.school_id)!!)
            val userid = createPartFromString(getdata(Data.Userid)!!)
            val addresse = createPartFromString(address.text.toString())
            Log.e("@@3", "user_id \n" + getdata(Data.Userid)+" "+"school_id " + getdata(Data.school_id) )

            Log.e("@@a", "functione")

            var preferences = Services()
            val loginResponseCall: Call<Profileupdateapi> =
                preferences.getaccountupdate()!!.account(school_id, userid, addresse, parts)
            loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
                override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                    Log.e("@@", "e " + t.message)
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                    }
                }

                override fun onResponse(
                    call: Call<Profileupdateapi>,
                    response: Response<Profileupdateapi>
                ) {
                    load_user()
                    Toast.makeText(context, response.body()!!.status+" ", Toast.LENGTH_SHORT).show()
                    status = false
                }
            })

        }
    }

    fun setdata(key: String, value: String) {
        try {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
            editor.commit()
        }
        catch (e:Exception)
        {
            System.out.println("@@e"+e.message)
        }

    }



    fun makeRequest() {
        ActivityCompat.requestPermissions(
            activity!!,
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
                } else {
                    Log.i("@@permission", "Permission has been granted by user")
                }
            }
        }
    }

    override fun onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
        activity!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
    fun openkeybord(edittext:EditText)
    {
        Log.e("@@", "On Keyboard Button click event!")
        edittext.requestFocus();
        edittext.editableText
        edittext.setFocusableInTouchMode(true);
        val imm = edittext.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN, 0)
        imm?.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT)
    }
}