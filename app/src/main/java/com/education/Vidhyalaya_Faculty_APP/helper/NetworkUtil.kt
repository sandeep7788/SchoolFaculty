    package com.education.vidhyalaya.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager


internal object NetworkUtil {
    @SuppressLint("MissingPermission")
    fun getConnectivityStatusString(context: Context): String? {
        var status: String? = null
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled"
                return status
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled"
                return status
            }
        } else {
            status = "No internet is available"
            return status
        }
        return status
    }
}