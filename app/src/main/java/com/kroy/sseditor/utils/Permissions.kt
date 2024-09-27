package com.kroy.sseditor.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList

class Permissions {



     fun checkAndRequestPermissions(context : Context , activity: Activity): Boolean {


        val permissionCamera = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        val permissionWriteStorage =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionReadStorage =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionLocation =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
         val permissioncoarseLocation =
             ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        val recordAudio =
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
         val readSMS=
             ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
         val receiveSMS=
             ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS)

        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (recordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
        }

         if (readSMS != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(Manifest.permission.READ_SMS)
         }

         if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS)
         }

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                listPermissionsNeeded.toTypedArray(),
               101
            )
            return false
        }

        return true
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun handlePermissionsResult(grantResults: IntArray) {
        var allPermissionsGranted = true
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false
                break
            }
        }


    }
}