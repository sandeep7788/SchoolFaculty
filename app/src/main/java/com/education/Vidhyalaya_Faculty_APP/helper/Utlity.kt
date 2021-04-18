package com.education.Vidhyalaya_Faculty_APP.helper

import android.app.Activity
import cn.pedant.SweetAlert.SweetAlertDialog

open class Utlity {

    companion object {
        public fun showDialog(activity: Activity, title: String, type: Int) {
            var pausingDialog: SweetAlertDialog? = null
            if (pausingDialog == null) {
                pausingDialog = SweetAlertDialog(activity, type)
                pausingDialog!!.titleText = title + " "
                pausingDialog!!.setCancelable(false)
                pausingDialog!!.setConfirmClickListener {
                    pausingDialog!!.dismiss()
                    activity.finish()
                }
                pausingDialog.show()
            }

        }

        public fun showErrorDialgo(activity: Activity) {
            var pausingDialog: SweetAlertDialog? = null
            if (pausingDialog == null) {
                pausingDialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                pausingDialog!!.titleText = "Your request not submitted"
                pausingDialog!!.setCancelable(false)
                pausingDialog!!.setConfirmClickListener {
                    pausingDialog!!.dismiss()
//                    activity.finish()
                }
                pausingDialog.show()
            }

        }
    }
}