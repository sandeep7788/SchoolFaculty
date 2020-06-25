package com.education.Faculty.Activites

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.education.Faculty.API.Changepasswordapi
import com.education.Faculty.API.Data
import com.education.Faculty.API.Services
import com.dating.schoolfaculty.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChangePassword : AppCompatActivity() {
    lateinit var i_backbutton:ImageView
    lateinit var imageview: CircleImageView
    lateinit var t_name: TextView
    lateinit var e_oldpassword:EditText
    lateinit var e_newpassword:EditText
    lateinit var e_conformpassword:EditText
    lateinit var button:Button
    lateinit var pb: android.app.AlertDialog
    lateinit var  i_old_password:ImageView
    lateinit var  i_newpassword:ImageView
    lateinit var  i_conformpassword:ImageView
    var mHandler: Handler? = null

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        i_backbutton=findViewById(R.id.i_backbutton)
        imageview=findViewById(R.id.imageview)
        t_name=findViewById(R.id.t_name)
        e_oldpassword=findViewById(R.id.e_oldpassword)
        e_newpassword=findViewById(R.id.e_newpassword)
        e_conformpassword=findViewById(R.id.e_conformpassword)
        button=findViewById(R.id.button)
        i_old_password=findViewById(R.id.i_old_password)
        i_newpassword=findViewById(R.id.i_newpassword)
        i_conformpassword=findViewById(R.id.i_conformpassword)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        pb= SpotsDialog.Builder().setContext(this@ChangePassword).build()
        pb.setTitle("Connecting to Server")
        pb!!.setMessage("Please Wait")
        pb.setCancelable(false)

        i_backbutton.setOnClickListener { finish() }
        Picasso.with(this@ChangePassword).load("http://skoolstarr.com/sspanel/"+getdata(Data.image))
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(imageview)

        t_name.setText(getdata(Data.name))

        button.setOnClickListener {
            if(e_oldpassword.text.isEmpty())
            {
                e_oldpassword.setError("Enter Old Password")
            }
            else if(e_newpassword.text.isEmpty())
            {
                e_newpassword.setError("Ender New Password")
            }
            else if(e_conformpassword.text.isEmpty())
            {
                e_conformpassword.setError("Conform Password")
            }
            else if((e_newpassword.toString()).equals(e_conformpassword.toString()))
            {
                Toast.makeText(this@ChangePassword,"New and Old Password Not Same",Toast.LENGTH_LONG).show()
            }
            else {
                change_password()
            }
        }


        i_old_password.setOnClickListener { openkeybord(e_oldpassword) }
        i_newpassword.setOnClickListener { openkeybord(e_newpassword) }
        i_conformpassword.setOnClickListener { openkeybord(e_conformpassword) }




    }
    override fun dispatchKeyEvent(KEvent: KeyEvent): Boolean {
        val keyaction: Int = KEvent.getAction()
        if (keyaction == KeyEvent.ACTION_DOWN) {
            val keycode: Int = KEvent.getKeyCode()
            val keyunicode: Int = KEvent.getUnicodeChar(KEvent.getMetaState())
            val character = keyunicode.toChar()
            println("DEBUG MESSAGE KEY=$character KEYCODE=$keycode")
        }
        return super.dispatchKeyEvent(KEvent)
    }

    fun change_password()
    {
        pb.show()
        var preferences = Services()
        Log.e("@@",getdata(Data.school_id)+" "+getdata(Data.Userid)+" "+getdata(Data.id)+" "+e_oldpassword.toString()+" "+e_newpassword.toString())
        val loginResponseCall: Call<Changepasswordapi> = preferences.getchange_password()!!.getchancepasswordservices(getdata(Data.school_id)!!.trim(),getdata(Data.Userid)!!.trim(),e_oldpassword.text.toString(),e_newpassword.text.toString())
        loginResponseCall.enqueue(object : Callback<Changepasswordapi>
        {
            override fun onFailure(call: Call<Changepasswordapi>, t: Throwable) {
                pb.dismiss()
                Toast.makeText(this@ChangePassword,"Failure Try Again or Check Internet Connection",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Changepasswordapi>,
                response: Response<Changepasswordapi>
            ) {
                pb.dismiss()
                Toast.makeText(this@ChangePassword," "+response!!.body()!!.status.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }

    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "d_null")
        return value
    }override fun onDestroy() {
        if (pb != null && pb.isShowing()) {
            pb.dismiss()
        }
        this!!.onVisibleBehindCanceled()
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

    /*fun showTheKeyboardWhenQWERTY(
        context: Context,
        editText: EditText?
    ) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN, 0)
    }*/
}