package com.education.vidhyalaya.helper

import android.app.Activity
import android.content.*
import android.net.ConnectivityManager
import android.util.Log
import cn.pedant.SweetAlert.SweetAlertDialog

public open class MyReceiver() : BroadcastReceiver() {
    var pausingDialog: SweetAlertDialog?=null
    override fun onReceive(mContext: Context, intent: Intent) {

        var status: String = NetworkUtil.getConnectivityStatusString(
            mContext
        )!!
        if(status.equals("No internet is available"))
        {
            blockActivity(false,mContext)
        }else{
            Log.d("@@", "onReceive: 3")
            blockActivity(true,mContext)
/*
            Snacky.builder()
                .setActivity(context as Activity?)
                .setActionText("Hide")
                .setActionTextColor(Color.parseColor("#ffffff"))
                .setActionTextColor(Color.parseColor("#5D6D7E"))
                .setText(status)
                .setTextColor(Color.parseColor("#ffffff"))
                .setBackgroundColor(Color.parseColor("#041e37"))
                .setDuration(Snacky.LENGTH_INDEFINITE)
                .build()
                .show()
*/
        }
    }

    fun blockActivity(connected: Boolean,mContext: Context) {

        if (pausingDialog == null) {
            pausingDialog =SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
            pausingDialog!!.titleText = "Application waiting for internet connection..."
            pausingDialog!!.setCancelable(false)
            pausingDialog!!.setConfirmClickListener{
                pausingDialog!!.dismiss()
                var MyReceiver: BroadcastReceiver?= null;
                MyReceiver = MyReceiver();
                mContext.registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
                val intent = Intent()
                intent.component = ComponentName(
                    "com.android.settings",
                    "com.android.settings.Settings\$DataUsageSummaryActivity"
                )
                mContext.startActivity(intent)
            }
        }
        if (!connected) {

            if (!(mContext as Activity).isFinishing) {
                if (!pausingDialog!!.isShowing) {
                    pausingDialog!!.show()
                }
            }
        } else {
            if (!(mContext as Activity).isFinishing) {
                if (pausingDialog!!.isShowing) {
                    pausingDialog!!.dismiss()
                }
            }
        }
    }
}